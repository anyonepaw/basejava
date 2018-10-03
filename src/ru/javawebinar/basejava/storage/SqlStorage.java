package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;

import java.util.*;


public class SqlStorage implements Storage {
	private SqlHelper sqlHelper;

	public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
		this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
	}

	@Override
	public void clear() {
		sqlHelper.callSqlStrategy("DELETE FROM resume");
	}

	@Override
	public void update(Resume resume) {
		sqlHelper.transactionalExecute(conn -> {
			try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
				setStrings(ps, resume.getFullName(), resume.getUuid());
				if (ps.executeUpdate() == 0) {
					throw new NotExistStorageException(resume.getUuid());
				}
			}
			try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
				ps.setString(1, resume.getUuid());
				ps.execute();
			}
			gettingContacts(resume, conn);
			return null;
		});
	}

	@Override
	public void save(Resume resume) {
		sqlHelper.transactionalExecute(conn -> {
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
				setStrings(ps, resume.getUuid(), resume.getFullName());
				ps.execute();
			}
			gettingContacts(resume, conn);
			return null;
		});
	}

	@Override
	public Resume get(String uuid) {
		return sqlHelper.callSqlStrategy("SELECT * FROM resume r" +
						"                      LEFT JOIN contact c" +
						"                      ON r.uuid = c.resume_uuid" +
						"                      WHERE r.uuid = ?",
				ps -> {
					ps.setString(1, uuid);
					ResultSet rs = ps.executeQuery();
					if (!rs.next()) {
						throw new NotExistStorageException(uuid);
					}
					Resume resume = new Resume(uuid, rs.getString("full_name"));
					do {
						addingContact(resume, rs);
					} while (rs.next());
					return resume;
				});
	}

	@Override
	public void delete(String uuid) {
		sqlHelper.callSqlStrategy("DELETE FROM resume WHERE uuid=?", ps -> {
			setStrings(ps, uuid);
			if (ps.executeUpdate() == 0) {
				throw new NotExistStorageException(uuid);
			}
			return null;
		});
	}

	@Override
	public List<Resume> getAllSorted() {
		return sqlHelper.callSqlStrategy("SELECT * FROM resume r" +
				"                              LEFT JOIN contact c" +
				"                              ON r.uuid = c.resume_uuid" +
				"                              ORDER BY full_name, uuid", ps -> {
			ResultSet rs = ps.executeQuery();
			Map<String, Resume> sortedResumesMap = new LinkedHashMap<>();
			while (rs.next()) {
				String uuid = rs.getString("uuid");
				sortedResumesMap.putIfAbsent(uuid, new Resume(uuid, rs.getString("full_name")));
				Resume resume = sortedResumesMap.get(uuid);
				addingContact(resume, rs);
			}
			return new ArrayList<>(sortedResumesMap.values());
		});
	}

	@Override
	public int size() {
		return sqlHelper.callSqlStrategy("SELECT * FROM resume", ps -> {
			ResultSet rs = ps.executeQuery();
			rs.last();
			return rs.getRow();
			//return rs.next()? rs.getInt(1) : 0;
		});
	}


	private void setStrings(PreparedStatement ps, String... arg) throws SQLException {
		int index = arg.length;
		ps.setString(index, arg[index - 1]);
		if (arg.length == 1) {
			return;
		} else {
			String[] arg1 = Arrays.copyOf(arg, arg.length - 1);
			setStrings(ps, arg1);
		}
	}

	private void addingContact(Resume resume, ResultSet rs) throws SQLException {
		String value = rs.getString("value");
		if (value != null) {
			ContactType type = ContactType.valueOf(rs.getString("type"));
			resume.addContact(type, value);
		}
	}

	private void gettingContacts(Resume resume, Connection conn) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
			for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
				setStrings(ps, resume.getUuid(), entry.getKey().name(), entry.getValue());
				ps.addBatch();
			}
			ps.executeBatch();
		}
	}

}

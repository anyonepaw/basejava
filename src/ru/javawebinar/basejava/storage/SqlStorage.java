package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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
				setStrings(resume.getFullName(), resume.getUuid(), ps);
				if (ps.executeUpdate() == 0) {
					throw new NotExistStorageException(resume.getUuid());
				}
			}
			try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET type = ?, value = ?  WHERE resume_uuid = ?")) {
				gettingContacts(resume, ps);
			}
			return null;
		});
	}

	@Override
	public void save(Resume resume) {
		sqlHelper.transactionalExecute(conn -> {
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
				setStrings(resume.getUuid(), resume.getFullName(), ps);
				ps.execute();
			}
			if (!resume.getContacts().isEmpty()) {
				try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
					gettingContacts(resume, ps);
				}
			}
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
			ps.setString(1, uuid);
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
			Map<String, Resume> hashMap = new LinkedHashMap<>();
			while (rs.next()) {
				String uuid = rs.getString("uuid");
				Resume resume = hashMap.get(uuid);
				if (resume == null) {
					resume = new Resume(uuid, rs.getString("full_name"));
				}
				addingContact(resume, rs);
				hashMap.put(uuid, resume);
			}
			return new ArrayList<>(hashMap.values());
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


	private void setStrings(String arg1, String arg2, PreparedStatement ps) throws SQLException {
		ps.setString(1, arg1);
		ps.setString(2, arg2);
	}

	private void addingContact(Resume r, ResultSet rs) throws SQLException {
		String value = rs.getString("value");
		if (value != null) {
			ContactType type = ContactType.valueOf(rs.getString("type"));
			r.addContact(type, value);
		}
	}

	private void gettingContacts(Resume resume, PreparedStatement ps) throws SQLException {
		for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
			ps.setString(1, resume.getUuid());
			ps.setString(2, entry.getKey().name());
			ps.setString(3, entry.getValue());
			ps.addBatch();
		}
		ps.executeBatch();
	}

}

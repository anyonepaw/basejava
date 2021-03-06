package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.*;

import java.util.*;


public class SqlStorage implements Storage {
	private SqlHelper sqlHelper;

	public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
				ps.setString(1, resume.getFullName());
				ps.setString(2, resume.getUuid());
				if (ps.executeUpdate() == 0) {
					throw new NotExistStorageException(resume.getUuid());
				}
			}

			deleteContacts(conn, resume);
			getAllContacts(resume, conn);

			deleteSections(conn, resume);
			getAllSections(resume, conn);

			return null;
		});
	}

	private void deleteSections(Connection conn, Resume resume) throws SQLException {
		deleteAttributes(conn, resume, "DELETE FROM section WHERE resume_uuid = ?");
	}

	private void deleteContacts(Connection conn, Resume resume) throws SQLException {
		deleteAttributes(conn, resume, "DELETE FROM contact WHERE resume_uuid = ?");
	}

	private void deleteAttributes(Connection conn, Resume resume, String sql) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, resume.getUuid());
			ps.execute();
		}
	}

	@Override
	public void save(Resume resume) {
		sqlHelper.transactionalExecute(conn -> {
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
				ps.setString(1, resume.getUuid());
				ps.setString(2, resume.getFullName());
				ps.execute();
			}
			getAllContacts(resume, conn);
			getAllSections(resume, conn);
			return null;
		});
	}

	@Override
	public Resume get(String uuid) {
		return sqlHelper.transactionalExecute(conn -> {
			Resume resume;
			try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid=?")) {
				ps.setString(1, uuid);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					throw new NotExistStorageException(uuid);
				}
				resume = new Resume(uuid, rs.getString("full_name"));
			}

			try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid=?")) {
				ps.setString(1, uuid);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					addContact(resume, rs);
				}
			}

			try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid=?")) {
				ps.setString(1, uuid);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					addSection(resume, rs);
				}
			}

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

		return sqlHelper.transactionalExecute(conn -> {

			Map<String, Resume> sortedResumesMap = new LinkedHashMap<>();

			try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String uuid = rs.getString("uuid");
					String full_name = rs.getString("full_name");
					sortedResumesMap.putIfAbsent(uuid, new Resume(uuid, full_name));
				}
			}

			try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String resume_uuid = rs.getString("resume_uuid");
					Resume resume = sortedResumesMap.get(resume_uuid);
					addContact(resume, rs);
				}
			}

			try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String resume_uuid = rs.getString("resume_uuid");
					Resume resume = sortedResumesMap.get(resume_uuid);
					addSection(resume, rs);
				}
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

	private void addContact(Resume resume, ResultSet rs) throws SQLException {
		String value = rs.getString("value");
		if (value != null) {
			resume.setContact(ContactType.valueOf(rs.getString("type")), value);
		}
	}

	private void addSection(Resume resume, ResultSet rs) throws SQLException {
		String value = rs.getString("value");
		if (value != null) {
			SectionType type = SectionType.valueOf(rs.getString("type"));
			resume.setSection(type, JsonParser.read(value, Text.class));
		}
	}


	private void getAllContacts(Resume resume, Connection conn) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
			for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
				ps.setString(1, resume.getUuid());
				ps.setString(2, entry.getKey().name());
				ps.setString(3, entry.getValue());
				ps.addBatch();
			}
			ps.executeBatch();
		}
	}

	private void getAllSections(Resume resume, Connection conn) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
			for (Map.Entry<SectionType, Text> entry : resume.getSections().entrySet()) {
				ps.setString(1, resume.getUuid());
				ps.setString(2, entry.getKey().name());
				Text section = entry.getValue();
				ps.setString(3, JsonParser.write(section, Text.class));
				ps.addBatch();
			}
			ps.executeBatch();
		}
	}

}

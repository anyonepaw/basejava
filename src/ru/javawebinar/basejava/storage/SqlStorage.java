package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;


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
		sqlHelper.callSqlStrategy("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
			ps.setString(1, resume.getFullName());
			ps.setString(2, resume.getUuid());
			if (ps.executeUpdate() == 0) {
				throw new NotExistStorageException(resume.getUuid());
			}
			return null;
		});
	}

	@Override
	public void save(Resume resume) throws ExistStorageException{
		sqlHelper.callSqlStrategy("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
			ps.setString(1, resume.getUuid());
			ps.setString(2, resume.getFullName());
			ps.execute();
			return null;
		});
	}

	@Override
	public Resume get(String uuid) {
		return sqlHelper.callSqlStrategy("SELECT * FROM resume WHERE uuid=?", ps -> {
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotExistStorageException(uuid);
			}
			return new Resume(uuid, rs.getString("full_name"));
		});
	}

	@Override
	public void delete(String uuid) {
		sqlHelper.callSqlStrategy("DELETE FROM resume WHERE uuid=?", ps -> {
			ps.setString(1, uuid);
			ps.execute();
			return null;
		});
	}

	@Override
	public List<Resume> getAllSorted() {
		return sqlHelper.callSqlStrategy("SELECT * FROM resume ORDER BY full_name ASC, uuid ASC", ps -> {
			List<Resume> resumesList = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				resumesList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
			}
			return resumesList;
		});
	}

	@Override
	public int size() {
		return sqlHelper.callSqlStrategy("SELECT * FROM resume", ps -> {
			ResultSet rs = ps.executeQuery();
			rs.last();
			return rs.getRow();
		});
	}
}

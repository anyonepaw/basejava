package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {

	private final ConnectionFactory connectionFactory;

	public SqlHelper(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void callSqlStrategy(String sql) {
		callSqlStrategy(sql, PreparedStatement::execute);
	}

	public <T> T callSqlStrategy(String sql, SqlStrategy<T> sqlStrategy) {
		try (Connection connection = connectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				     ResultSet.CONCUR_UPDATABLE)) {
			return sqlStrategy.perform(ps);
		} catch (SQLException e) {
			//костыль
			throw new ExistStorageException(e.getMessage());
		}
	}

	public interface SqlStrategy<T> {
		T perform(PreparedStatement ps) throws SQLException;
	}
}
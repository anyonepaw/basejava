package ru.javawebinar.basejava.sql;


import ru.javawebinar.basejava.exception.StorageException;


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
			throw new StorageException(e.getSQLState());
		}
	}

	public interface SqlStrategy<T> {
		T perform(PreparedStatement ps) throws SQLException;
	}


}
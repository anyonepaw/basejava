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
			return sqlStrategy.execute(ps);
		} catch (SQLException e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	public <T> T transactionalExecute(SqlTransaction<T> executor){
		try(Connection conn = connectionFactory.getConnection()){
			try{
				conn.setAutoCommit(false);
				T res = executor.execute(conn);
				conn.commit();
				return res;
			} catch (SQLException e){
				conn.rollback();
				throw ExceptionUtil.convertException(e);
			}
		} catch (SQLException e) {
			throw new StorageException(e);
		}
	}


	public interface SqlStrategy<T> {
		T execute(PreparedStatement st) throws SQLException;
	}


}
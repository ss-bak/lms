package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDao<T> {

	protected Connection connection;

	public BaseDao(Connection connection) {
		this.connection = connection;
	}

	protected void save(String query, Object[] values) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(query);
		if (values != null) {
			int i = 1;
			for (Object object : values) {
				ps.setObject(i, object);
				i++;
			}
		}
		ps.executeUpdate();
	}

	protected Integer saveReturnPk(String sql, Object[] values) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		if (values != null) {
			int i = 1;
			for (Object o : values) {
				pstmt.setObject(i, o);
				i++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	protected List<T> readFirstLevel(String query, Object[] values) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement(query);
		if (values != null) {
			int i = 1;
			for (Object object : values) {
				pstmt.setObject(i, object);
				i++;
			}
		}
		return extractDataFirstLevel(pstmt.executeQuery());
	}

	protected abstract List<T> extractDataFirstLevel(ResultSet rs) throws SQLException;

}

package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.lms.model.Publisher;

public class PublisherDao extends BaseDao<Publisher> {

	public PublisherDao(Connection connection) {
		super(connection);
	}

	public Publisher readOneFirstLevel(Integer id) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement("select * from tbl_publisher where publisherId = ?");
		pstmt.setObject(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setId(rs.getInt("publisherId"));
			publisher.setName(rs.getString("publisherName"));
			publisher.setAddress(rs.getString("publisherAddress"));
			publisher.setAddress(rs.getString("publisherPhone"));
			return publisher;
		}
		return null;
	}

	@Override
	protected List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setId(rs.getInt("publisherId"));
			publisher.setName(rs.getString("publisherName"));
			publisher.setAddress(rs.getString("publisherAddress"));
			publisher.setAddress(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		return publishers;
	}

}
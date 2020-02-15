package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.lms.model.Author;

public class AuthorDao extends BaseDao<Author> {

	public AuthorDao(Connection connection) {
		super(connection);
	}

	@Override
	protected List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		while (rs.next()) {
			Author author = new Author();
			author.setId(rs.getInt("authorId"));
			author.setName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}

}
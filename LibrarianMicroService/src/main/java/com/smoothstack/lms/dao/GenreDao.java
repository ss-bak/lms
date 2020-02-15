package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.lms.model.Genre;

public class GenreDao extends BaseDao<Genre> {

	public GenreDao(Connection connection) {
		super(connection);
	}

	@Override
	protected List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<Genre>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setId(rs.getInt("genre_id"));
			genre.setName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}

}
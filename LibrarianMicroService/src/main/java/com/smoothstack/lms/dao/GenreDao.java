package com.smoothstack.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smoothstack.lms.model.Genre;

@Component
public class GenreDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private BookDao bookDao;

	public List<Genre> read() throws SQLException {
		return jdbcTemplate.query("select * from tbl_genre", (rs, rowNum) -> extractData(rs));
	}

	public List<Genre> readFirstLevel(String sql, Object[] values) throws SQLException {
		return jdbcTemplate.query(sql, values, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	private Genre extractData(ResultSet rs) throws SQLException {
		Genre genre = new Genre();
		genre.setId(rs.getInt("genre_id"));
		genre.setName(rs.getString("genre_name"));
		genre.setBooks(bookDao.readFirstLevel(
				"select * from tbl_book inner join tbl_book_genres on tbl_book.bookId = tbl_book_genres.bookId where tbl_book_genres.genre_id = ?",
				new Object[] { rs.getInt("genre_id") }));
		return genre;
	}

	private Genre extractDataFirstLevel(ResultSet rs) throws SQLException {
		Genre genre = new Genre();
		genre.setId(rs.getInt("genre_id"));
		genre.setName(rs.getString("genre_name"));
		return genre;
	}
}
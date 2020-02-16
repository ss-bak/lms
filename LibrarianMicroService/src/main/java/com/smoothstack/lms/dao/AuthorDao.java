package com.smoothstack.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smoothstack.lms.model.Author;

@Component
public class AuthorDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	private BookDao bookDao;

	public List<Author> read() throws SQLException {
		return jdbcTemplate.query("select * from tbl_author", (rs, rowNum) -> extractData(rs));
	}

	public List<Author> readFirstLevel(String sql, Object[] values) throws SQLException {
		return jdbcTemplate.query(sql, values, (rs, rowNum) -> extractDataFirstLevel(rs));
	}

	private Author extractData(ResultSet rs) throws SQLException {
		Author author = new Author();
		author.setId(rs.getInt("authorId"));
		author.setName(rs.getString("authorName"));
		author.setBooks(bookDao.readFirstLevel(
				"select * from tbl_book inner join tbl_book_authors on tbl_book.bookId = tbl_book_authors.bookId where tbl_book_authors.authorId = ?",
				new Object[] { rs.getInt("authorId") }));
		return author;
	}

	private Author extractDataFirstLevel(ResultSet rs) throws SQLException {
		Author author = new Author();
		author.setId(rs.getInt("authorId"));
		author.setName(rs.getString("authorName"));
		return author;
	}

}
package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.Publisher;

public class BookDao extends BaseDao<Book> {

	public BookDao(Connection connection) {
		super(connection);
	}

	public List<Book> read() throws SQLException {
		return extractData(connection.prepareStatement("select * from tbl_book").executeQuery());
	}

	public Book readOneFirstLevel(Integer id) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement("select * from tbl_book where bookId = ?");
		pstmt.setObject(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			Book book = new Book();
			Publisher publisher = new Publisher();
			book.setId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			publisher.setId(rs.getInt("pubId"));
			book.setPublisher(publisher);
			return book;
		}
		return null;
	}

	protected List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		PublisherDao publisherDao = new PublisherDao(connection);
		AuthorDao authorDao = new AuthorDao(connection);
		GenreDao genreDao = new GenreDao(connection);
		LibraryBranchDao libraryBranchDao = new LibraryBranchDao(connection);
		BorrowerDao borrowerDao = new BorrowerDao(connection);
		while (rs.next()) {
			Book book = new Book();
			book.setId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(publisherDao.readOneFirstLevel(rs.getInt("pubId")));
			book.setAuthors(authorDao.readFirstLevel(
					"select * from tbl_author inner join tbl_book_authors on tbl_author.authorId = tbl_book_authors.authorId where tbl_book_authors.bookId = ?",
					new Object[] { rs.getInt("bookId") }));
			book.setGenres(genreDao.readFirstLevel(
					"select * from tbl_genre inner join tbl_book_genres on tbl_genre.genre_id = tbl_book_genres.genre_id where tbl_book_genres.bookId = ?",
					new Object[] { rs.getInt("bookId") }));
			book.setLibraryBranches(libraryBranchDao.readFirstLevel(
					"select * from tbl_library_branch inner join tbl_book_loans on tbl_library_branch.branchId = tbl_book_loans.branchId where tbl_book_loans.bookId = ?",
					new Object[] { rs.getInt("bookId") }));
			book.setBorrowers(borrowerDao.readFirstLevel(
					"select * from tbl_borrower inner join tbl_book_loans on tbl_borrower.cardNo = tbl_book_loans.cardNo where tbl_book_loans.bookId = ?",
					new Object[] { rs.getInt("bookId") }));
			books.add(book);
		}
		return books;
	}

	@Override
	protected List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			Publisher publisher = new Publisher();
			publisher.setId(rs.getInt("pubId"));
			book.setPublisher(publisher);
			books.add(book);
		}
		return books;
	}

}
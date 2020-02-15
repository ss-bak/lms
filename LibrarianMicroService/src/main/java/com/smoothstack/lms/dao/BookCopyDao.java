package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.BookCopy;
import com.smoothstack.lms.model.LibraryBranch;

public class BookCopyDao extends BaseDao<BookCopy> {

	public BookCopyDao(Connection connection) {
		super(connection);
	}

	public void create(BookCopy bookCopy) throws SQLException {
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values(?, ?, ?)",
				new Object[] { bookCopy.getBook().getId(), bookCopy.getLibraryBranch().getId(), bookCopy.getAmount() });
	}

	public void update(BookCopy bookCopy) throws SQLException {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { bookCopy.getAmount(), bookCopy.getBook().getId(), bookCopy.getLibraryBranch().getId() });
	}

	public BookCopy readOneFirstLevel(Integer bookId, Integer libraryBranchId) throws SQLException {
		PreparedStatement pstmt = connection
				.prepareStatement("select * from tbl_book_copies where bookId = ? and branchId = ?");
		pstmt.setObject(1, bookId);
		pstmt.setObject(2, libraryBranchId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			BookCopy bookCopy = new BookCopy();
			Book book = new Book();
			book.setId(rs.getInt("bookId"));
			bookCopy.setBook(book);
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setId(rs.getInt("branchId"));
			bookCopy.setLibraryBranch(libraryBranch);
			bookCopy.setAmount(rs.getInt("noOfCopies"));
			return bookCopy;
		}
		return null;
	}

	@Override
	protected List<BookCopy> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<BookCopy> bookCopies = new ArrayList<BookCopy>();
		while (rs.next()) {
			BookCopy bookCopy = new BookCopy();
			Book book = new Book();
			book.setId(rs.getInt("bookId"));
			bookCopy.setBook(book);
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setId(rs.getInt("branchId"));
			bookCopy.setLibraryBranch(libraryBranch);
			bookCopy.setAmount(rs.getInt("noOfCopies"));
			bookCopies.add(bookCopy);
		}
		return bookCopies;
	}

}
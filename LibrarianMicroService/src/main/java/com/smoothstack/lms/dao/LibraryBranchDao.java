package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.lms.model.LibraryBranch;

public class LibraryBranchDao extends BaseDao<LibraryBranch> {

	public LibraryBranchDao(Connection connection) {
		super(connection);
	}

	public void update(LibraryBranch libraryBranch) throws SQLException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { libraryBranch.getName(), libraryBranch.getAddress(), libraryBranch.getId() });
	}

	public List<LibraryBranch> read() throws SQLException {
		return extractData(connection.prepareStatement("select * from tbl_library_branch").executeQuery());
	}

	public LibraryBranch readOne(int id) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement("select * from tbl_library_branch where branchId = ?");
		pstmt.setObject(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			BookDao bookDao = new BookDao(connection);
			BorrowerDao borrowerDao = new BorrowerDao(connection);
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setId(rs.getInt("branchId"));
			libraryBranch.setName(rs.getString("branchName"));
			libraryBranch.setAddress(rs.getString("branchAddress"));
			libraryBranch.setBooks(bookDao.readFirstLevel(
					"select * from tbl_book inner join tbl_book_copies on tbl_book.bookId = tbl_book_copies.bookId where tbl_book_copies.branchId = ?",
					new Object[] { rs.getInt("branchId") }));
			libraryBranch.setBorrowers(borrowerDao.readFirstLevel(
					"select * from tbl_borrower inner join tbl_book_loans on tbl_borrower.cardNo = tbl_book_loans.cardNo where tbl_book_loans.branchId = ?",
					new Object[] { rs.getInt("branchId") }));
			return libraryBranch;
		}
		return null;
	}

	public LibraryBranch readOneFirstLevel(int id) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement("select * from tbl_library_branch where branchId = ?");
		pstmt.setObject(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setId(rs.getInt("branchId"));
			libraryBranch.setName(rs.getString("branchName"));
			libraryBranch.setAddress(rs.getString("branchAddress"));
			return libraryBranch;
		}
		return null;
	}

	protected List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();
		BookDao bookDao = new BookDao(connection);
		BorrowerDao borrowerDao = new BorrowerDao(connection);
		while (rs.next()) {
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setId(rs.getInt("branchId"));
			libraryBranch.setName(rs.getString("branchName"));
			libraryBranch.setAddress(rs.getString("branchAddress"));
			libraryBranch.setBooks(bookDao.readFirstLevel(
					"select * from tbl_book inner join tbl_book_copies on tbl_book.bookId = tbl_book_copies.bookId where tbl_book_copies.branchId = ?",
					new Object[] { rs.getInt("branchId") }));
			libraryBranch.setBorrowers(borrowerDao.readFirstLevel(
					"select * from tbl_borrower inner join tbl_book_loans on tbl_borrower.cardNo = tbl_book_loans.cardNo where tbl_book_loans.branchId = ?",
					new Object[] { rs.getInt("branchId") }));
			libraryBranches.add(libraryBranch);
		}
		return libraryBranches;
	}

	@Override
	protected List<LibraryBranch> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();
		while (rs.next()) {
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setId(rs.getInt("branchId"));
			libraryBranch.setName(rs.getString("branchName"));
			libraryBranch.setAddress(rs.getString("branchAddress"));
			libraryBranches.add(libraryBranch);
		}
		return libraryBranches;
	}

}
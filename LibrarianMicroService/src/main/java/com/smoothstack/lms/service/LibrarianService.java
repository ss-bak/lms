package com.smoothstack.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.lms.dao.BookDao;
import com.smoothstack.lms.dao.LibraryBranchDao;
import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.LibraryBranch;
import com.smoothstack.lms.util.ConnectUtil;

@Service
public class LibrarianService {

	@Autowired
	private ConnectUtil connectUtil;

	public List<LibraryBranch> getLibraryBranches() throws SQLException {
		Connection connection = connectUtil.getConnection();
		try {
			return new LibraryBranchDao(connection).read();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.close();
		}
	}

	public LibraryBranch getLibraryBranchById(int id) throws SQLException {
		Connection connection = connectUtil.getConnection();
		try {
			return new LibraryBranchDao(connection).readOne(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.close();
		}
	}

	public void updateLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		Connection connection = connectUtil.getConnection();
		try {
			new LibraryBranchDao(connection).update(libraryBranch);
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public List<Book> getBooks() throws SQLException {
		Connection connection = connectUtil.getConnection();
		try {
			return new BookDao(connection).read();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.close();
		}
	}

}
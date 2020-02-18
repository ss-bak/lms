package com.smoothstack.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.lms.dao.BookCopyDao;
import com.smoothstack.lms.dao.BookDao;
import com.smoothstack.lms.dao.LibraryBranchDao;
import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.BookCopy;
import com.smoothstack.lms.model.LibraryBranch;

@Service
public class LibrarianService {

	@Autowired
	private LibraryBranchDao libraryBranchDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private BookCopyDao bookCopyDao;

	public List<LibraryBranch> getLibraryBranches() throws SQLException {
		return libraryBranchDao.read();
	}

	public LibraryBranch getLibraryBranchById(int id) throws SQLException {
		return libraryBranchDao.readOne(id);
	}

	public int updateLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		return libraryBranchDao.update(libraryBranch);
	}

	public List<Book> getBooks() throws SQLException {
		return bookDao.read();
	}

	public BookCopy getBookCopyById(int bookId, int libraryBranchId) throws SQLException {
		return bookCopyDao.readOne(bookId, libraryBranchId);
	}

	public void addBookCopy(BookCopy bookCopy) throws SQLException {
		bookCopyDao.create(bookCopy);
	}

	public int updateBookCopy(BookCopy bookCopy) throws SQLException {
		return bookCopyDao.update(bookCopy);
	}

}
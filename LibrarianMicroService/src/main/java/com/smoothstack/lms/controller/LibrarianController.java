package com.smoothstack.lms.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.BookCopy;
import com.smoothstack.lms.model.LibraryBranch;
import com.smoothstack.lms.service.LibrarianService;

@RestController
public class LibrarianController {

	@Autowired
	private LibrarianService librarianService;

	@RequestMapping(path = "/lms/librarybranches", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<LibraryBranch>> getLibraryBranches() {
		List<LibraryBranch> libraryBranches = null;
		try {
			libraryBranches = librarianService.getLibraryBranches();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<LibraryBranch>>(libraryBranches, HttpStatus.OK);
	}

	@RequestMapping(path = "/lms/librarybranches/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LibraryBranch> getLibraryBranchById(@PathVariable String id) {
		Integer parsedId = null;
		try {
			parsedId = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (parsedId <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LibraryBranch libraryBranch = null;
		try {
			libraryBranch = librarianService.getLibraryBranchById(parsedId);
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<LibraryBranch>(libraryBranch, HttpStatus.OK);
	}

	@RequestMapping(path = "/lms/librarybranches", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		if (libraryBranch == null || libraryBranch.getId() == null || libraryBranch.getName() == null
				|| libraryBranch.getAddress() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (libraryBranch.getId() <= 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (libraryBranch.getName().trim().isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (libraryBranch.getAddress().trim().isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		libraryBranch.setName(libraryBranch.getName().trim());
		libraryBranch.setAddress(libraryBranch.getAddress().trim());
		try {
			librarianService.updateLibraryBranch(libraryBranch);
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(path = "/lms/books", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> books = null;
		try {
			books = librarianService.getBooks();
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

	@RequestMapping(path = "/lms/bookcopies/{bookId}/{libraryBranchId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookCopy> getBookCopy(@PathVariable String bookId, @PathVariable String libraryBranchId) {
		Integer parsedBookId = null, parsedLibraryBranchId = null;
		try {
			parsedBookId = Integer.parseInt(bookId);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			parsedLibraryBranchId = Integer.parseInt(libraryBranchId);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (parsedBookId <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (parsedLibraryBranchId <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BookCopy bookCopy = null;
		try {
			bookCopy = librarianService.getBookCopyById(parsedBookId, parsedLibraryBranchId);
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BookCopy>(bookCopy, HttpStatus.OK);
	}

	@RequestMapping(path = "/lms/bookcopies", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateBookCopy(@RequestBody BookCopy bookCopy) {
		if (bookCopy == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (bookCopy.getBook() == null || bookCopy.getBook().getId() == null || bookCopy.getBook().getId() <= 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (bookCopy.getLibraryBranch() == null || bookCopy.getLibraryBranch().getId() == null
				|| bookCopy.getLibraryBranch().getId() < 1)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (bookCopy.getAmount() == null || bookCopy.getAmount() < 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		try {
			librarianService.updateBookCopy(bookCopy);
		} catch (SQLException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

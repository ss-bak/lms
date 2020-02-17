package com.smoothstack.lms.controller;

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
		try {
			List<LibraryBranch> libraryBranches = librarianService.getLibraryBranches();
			return new ResponseEntity<List<LibraryBranch>>(libraryBranches, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/lms/librarybranches/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<LibraryBranch> getLibraryBranchById(@PathVariable int id) {
		try {
			if (id <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			LibraryBranch libraryBranch = librarianService.getLibraryBranchById(id);
			return new ResponseEntity<LibraryBranch>(libraryBranch, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/lms/librarybranches", method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		try {
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
			librarianService.updateLibraryBranch(libraryBranch);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/lms/books", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		try {
			List<Book> books = librarianService.getBooks();
			return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/lms/bookcopies/{bookId}/{libraryBranchId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BookCopy> getBookCopy(@PathVariable int bookId, @PathVariable int libraryBranchId) {
		try {
			if (bookId <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if (libraryBranchId <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			BookCopy bookCopy = librarianService.getBookCopyById(bookId, libraryBranchId);
			return new ResponseEntity<BookCopy>(bookCopy, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/lms/bookcopies", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBookCopy(@RequestBody BookCopy bookCopy) {
		try {
			if (bookCopy == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			if (bookCopy.getBook() == null || bookCopy.getBook().getId() == null || bookCopy.getBook().getId() <= 0)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			if (bookCopy.getLibraryBranch() == null || bookCopy.getLibraryBranch().getId() == null
					|| bookCopy.getLibraryBranch().getId() <= 0)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			if (bookCopy.getAmount() == null || bookCopy.getAmount() < 0)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			librarianService.updateBookCopy(bookCopy);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

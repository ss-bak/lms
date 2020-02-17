package com.smoothstack.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.smoothstack.lms.model.Book;
import com.smoothstack.lms.model.BookCopy;
import com.smoothstack.lms.model.LibraryBranch;
import com.smoothstack.lms.service.LibrarianService;

@RestController
public class LibrarianController {

	@Autowired
	private LibrarianService librarianService;

	@RequestMapping(path = "/librarian/librarybranches", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<LibraryBranch>> getLibraryBranches() {
		try {
			List<LibraryBranch> libraryBranches = librarianService.getLibraryBranches();
			if (libraryBranches.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(libraryBranches);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(path = "/librarian/librarybranches/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<LibraryBranch> getLibraryBranchById(@PathVariable int id) {
		try {
			LibraryBranch libraryBranch = librarianService.getLibraryBranchById(id);
			return ResponseEntity.ok(libraryBranch);
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(path = "/librarian/librarybranches", method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		try {
			if (libraryBranch == null || libraryBranch.getId() == null || libraryBranch.getName() == null
					|| libraryBranch.getAddress() == null)
				return ResponseEntity.badRequest().build();
			if (libraryBranch.getName().trim().isEmpty())
				return ResponseEntity.badRequest().build();
			if (libraryBranch.getAddress().trim().isEmpty())
				return ResponseEntity.badRequest().build();
			int rowsAffected = librarianService.updateLibraryBranch(libraryBranch);
			if (rowsAffected == 0) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(path = "/librarian/books", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Book>> getBooks() {
		try {
			List<Book> books = librarianService.getBooks();
			if (books.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(books);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(path = "/librarian/bookcopies/{bookId}/{libraryBranchId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BookCopy> getBookCopy(@PathVariable int bookId, @PathVariable int libraryBranchId) {
		try {
			BookCopy bookCopy = librarianService.getBookCopyById(bookId, libraryBranchId);
			return ResponseEntity.ok(bookCopy);
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(path = "/librarian/bookcopies", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> addBookCopy(@RequestBody BookCopy bookCopy, UriComponentsBuilder b) {
		try {
			if (bookCopy == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getBook() == null || bookCopy.getBook().getId() == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getLibraryBranch() == null || bookCopy.getLibraryBranch().getId() == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getAmount() == null || bookCopy.getAmount() < 0)
				return ResponseEntity.badRequest().build();
			librarianService.addBookCopy(bookCopy);
			UriComponents uriComponents = b.path("/librarian/bookcopies/{bookId}/{libraryBranchId}")
					.buildAndExpand(bookCopy.getBook().getId(), bookCopy.getLibraryBranch().getId());
			return ResponseEntity.created(uriComponents.toUri()).build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(path = "/librarian/bookcopies", method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> updateBookCopy(@RequestBody BookCopy bookCopy) {
		try {
			if (bookCopy == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getBook() == null || bookCopy.getBook().getId() == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getLibraryBranch() == null || bookCopy.getLibraryBranch().getId() == null)
				return ResponseEntity.badRequest().build();
			if (bookCopy.getAmount() == null || bookCopy.getAmount() < 0)
				return ResponseEntity.badRequest().build();
			int rowsAffected = librarianService.updateBookCopy(bookCopy);
			if (rowsAffected == 0) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}

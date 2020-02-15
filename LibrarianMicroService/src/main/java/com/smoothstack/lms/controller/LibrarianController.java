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
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/lms/librarybranches/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LibraryBranch> getLibraryBranchById(@PathVariable int id) {
		try {
			LibraryBranch libraryBranch = librarianService.getLibraryBranchById(id);
			return new ResponseEntity<LibraryBranch>(libraryBranch, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/lms/librarybranches", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		try {
			librarianService.updateLibraryBranch(libraryBranch);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

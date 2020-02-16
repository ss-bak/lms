package com.smoothstack.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.lms.dao.LibraryBranchDao;
import com.smoothstack.lms.model.LibraryBranch;

@Service
public class LibrarianService {

	@Autowired
	private LibraryBranchDao libraryBranchDao;

	public List<LibraryBranch> getLibraryBranches() throws SQLException {
		return libraryBranchDao.read();
	}

	public LibraryBranch getLibraryBranchById(int id) throws SQLException {
		return libraryBranchDao.readOne(id);
	}

	public void updateLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		libraryBranchDao.update(libraryBranch);
	}

}
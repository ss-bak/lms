package com.smoothstack.lms.adminmicroservice.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smoothstack.lms.adminmicroservice.dao.AuthorDAO;
import com.smoothstack.lms.adminmicroservice.dao.BookDAO;
import com.smoothstack.lms.adminmicroservice.dao.BorrowerDAO;
import com.smoothstack.lms.adminmicroservice.dao.BranchDAO;
import com.smoothstack.lms.adminmicroservice.dao.CopiesDAO;
import com.smoothstack.lms.adminmicroservice.dao.GenreDAO;
import com.smoothstack.lms.adminmicroservice.dao.PublisherDAO;
import com.smoothstack.lms.adminmicroservice.model.Author;
import com.smoothstack.lms.adminmicroservice.model.Book;
import com.smoothstack.lms.adminmicroservice.model.Borrower;
import com.smoothstack.lms.adminmicroservice.model.Branch;
import com.smoothstack.lms.adminmicroservice.model.Copies;
import com.smoothstack.lms.adminmicroservice.model.Genre;
import com.smoothstack.lms.adminmicroservice.model.Publisher;

@Service
public class AdminService {

	public void saveAuthor(Author auth) throws ClassNotFoundException, SQLException {
		try {
			AuthorDAO adao = new AuthorDAO();
			auth.setAuthorId(adao.addAuthor(auth));
			for (Book bk : auth.getBooks())
				adao.insertBookAuthors(auth, bk);
			adao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save author");
			throw e;
		}
	}

	public void updateAuthor(Author auth) throws ClassNotFoundException, SQLException {
		try {
			AuthorDAO adao = new AuthorDAO();
			adao.updateAuthor(auth);
			adao.deleteBookAuthors(auth);
			for (Book bk : auth.getBooks())
				adao.insertBookAuthors(auth, bk);
			adao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update author");
			throw e;
		}
	}

	public void deleteAuthor(Author auth) throws ClassNotFoundException, SQLException {
		try {
			AuthorDAO adao = new AuthorDAO();
			BookDAO bdao = new BookDAO();
			adao.deleteAuthor(auth);
			adao.deleteBookAuthors(auth);
			for (Book book : auth.getBooks())
				bdao.deleteBook(book);
			bdao.commit();
			adao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete author");
			throw e;
		}
	}

	public List<Author> readAuthor() throws ClassNotFoundException, SQLException {
		try {
			AuthorDAO adao = new AuthorDAO();
			return adao.readAuthors();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read author");
			throw e;
		}
	}
	
	public Author readAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {
		try {
			AuthorDAO adao = new AuthorDAO();
			return adao.readAuthorById(authorId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read author");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveBook(Book book) throws ClassNotFoundException, SQLException {
		try {
			BookDAO bdao = new BookDAO();
			book.setBookId(bdao.addBook(book));
			for (Author aut : book.getAuthors())
				bdao.insertBookAuthors(aut, book);
			for (Genre gen : book.getGenres())
				bdao.insertBookGenres(gen, book);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save book");
			throw e;
		}
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		try {
			BookDAO bdao = new BookDAO();
			bdao.updateBook(book);
			bdao.deleteBookAuthors(book);
			bdao.deleteBookGenres(book);
			for (Author auth : book.getAuthors())
				bdao.insertBookAuthors(auth, book);
			for (Genre gen : book.getGenres())
				bdao.insertBookGenres(gen, book);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update book");
			throw e;
		}
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		try {
			BookDAO bdao = new BookDAO();
			CopiesDAO cdao = new CopiesDAO();
			bdao.deleteBook(book);
			bdao.deleteBookAuthors(book);
			bdao.deleteBookGenres(book);
			cdao.deleteCopiesByBookId(book.getBookId());
			bdao.commit();
			cdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete book");
			throw e;
		}
	}

	public List<Book> readBook() throws ClassNotFoundException, SQLException {
		try {
			BookDAO bdao = new BookDAO();
			return bdao.readBooks();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read book");
			throw e;
		}
	}

	public Book readBookById(Integer bookId) throws ClassNotFoundException, SQLException {
		try {
			BookDAO bdao = new BookDAO();
			return bdao.readBookById(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read book");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveGenre(Genre gen) throws ClassNotFoundException, SQLException {
		try {
			GenreDAO gdao = new GenreDAO();
			gen.setGenreId(gdao.addGenre(gen));
			for (Book bk : gen.getBooks())
				gdao.insertBookGenres(gen, bk);
			gdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create genre");
			throw e;
		}
	}

	public void updateGenre(Genre gen) throws ClassNotFoundException, SQLException {
		try {
			GenreDAO gdao = new GenreDAO();
			gdao.updateGenre(gen);
			gdao.deleteBookGenres(gen);
			for (Book book : gen.getBooks())
				gdao.insertBookGenres(gen, book);
			gdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update genre");
			throw e;
		}
	}

	public void deleteGenre(Genre gen) throws ClassNotFoundException, SQLException {
		try {
			GenreDAO gdao = new GenreDAO();
			gdao.deleteGenre(gen);
			gdao.deleteBookGenres(gen);
			gdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete genre");
			throw e;
		}
	}

	public List<Genre> readGenre() throws ClassNotFoundException, SQLException {
		try {
			GenreDAO gdao = new GenreDAO();
			return gdao.readGenre();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read genre");
			throw e;
		}
	}
	
	public Genre readGenreById(Integer genreId) throws ClassNotFoundException, SQLException {
		try {
			GenreDAO gdao = new GenreDAO();
			return gdao.readGenreById(genreId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read genre");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			bor.setCardNo(bdao.addBorrower(bor));
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save borrower");
			throw e;
		}
	}

	public void updateBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			bdao.updateBorrower(bor);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update borrower");
			throw e;
		}
	}

	public void deleteBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			bdao.deleteBorrower(bor);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete borrower");
			throw e;
		}
	}

	public List<Borrower> readBorrower() throws ClassNotFoundException, SQLException {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			return bdao.readBorrower();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read borrower");
			throw e;
		}
	}
	
	public Borrower readBorrowerById(Integer cardNo) throws ClassNotFoundException, SQLException {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			return bdao.readBorrowerById(cardNo);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read borrower");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveBranch(Branch brch) throws ClassNotFoundException, SQLException {
		try {
			BranchDAO bdao = new BranchDAO();
			brch.setBranchId(bdao.addBranch(brch));
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create branch");
			throw e;
		}
	}

	public void updateBranch(Branch brch) throws ClassNotFoundException, SQLException {
		try {
			BranchDAO bdao = new BranchDAO();
			bdao.updateBranch(brch);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update branch");
			throw e;
		}
	}

	public void deleteBranch(Branch brch) throws ClassNotFoundException, SQLException {
		try {
			BranchDAO bdao = new BranchDAO();
			bdao.deleteBranch(brch);
			bdao.deleteBranchCopies(brch);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete branch");
			throw e;
		}
	}

	public List<Branch> readBranch() throws ClassNotFoundException, SQLException {
		try {
			BranchDAO bdao = new BranchDAO();
			return bdao.readBranch();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read branch");
			throw e;
		}
	}
	
	public Branch readBranchById(Integer branchId) throws ClassNotFoundException, SQLException {
		try {
			BranchDAO bdao = new BranchDAO();
			return bdao.readBranchById(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read branch");
			e.printStackTrace();
			throw e;
		}
	}

	public void saveCopies(Copies copy) throws ClassNotFoundException, SQLException {
		try {
			CopiesDAO cdao = new CopiesDAO();
			copy.setBranchId(cdao.addCopies(copy));
			cdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Could not create copies");
			throw e;
		}
	}

	public void updateCopies(Copies copy) throws ClassNotFoundException, SQLException {
		try {
			CopiesDAO cdao = new CopiesDAO();
			cdao.updateCopies(copy);
			cdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update copies");
			throw e;
		}
	}

	public void deleteCopies(Copies copy) throws ClassNotFoundException, SQLException {
		try {
			CopiesDAO cdao = new CopiesDAO();
			cdao.deleteCopies(copy);
			cdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete copies");
			throw e;
		}
	}

	public List<Copies> readCopies() throws ClassNotFoundException, SQLException {
		try {
			CopiesDAO cdao = new CopiesDAO();
			return cdao.readCopies();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
			throw e;
		}
	}

	public List<Copies> readCopiesById(Integer branchId) throws ClassNotFoundException, SQLException {
		try {
			CopiesDAO cdao = new CopiesDAO();
			return cdao.readCopyById(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
			throw e;
		}
	}

	public Copies readCopyById(Integer branchId, Integer bookId) throws ClassNotFoundException, SQLException {
		try {
			CopiesDAO cdao = new CopiesDAO();
			return cdao.readCopyById(branchId, bookId).get(0);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
			throw e;
		}
	}

	public void savePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		try {
			PublisherDAO pdao = new PublisherDAO();
			pub.setPublisherId(pdao.addPublisher(pub));
			pdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not creat publisher");
			throw e;
		}
	}

	public void updatePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		try {
			PublisherDAO pdao = new PublisherDAO();
			pdao.updatePublisher(pub);
			pdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update publisher");
			throw e;
		}
	}

	public void deletePublisher(Publisher pub) throws ClassNotFoundException, SQLException {
		try {
			PublisherDAO pdao = new PublisherDAO();
			pdao.deletePublisher(pub);
			pdao.deletePublisherBooks(pub);
			pdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete publisher");
			throw e;
		}
	}

	public List<Publisher> readPublisher() throws ClassNotFoundException, SQLException {
		try {
			PublisherDAO pdao = new PublisherDAO();
			return pdao.readPublisher();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read publisher");
			throw e;
		}
	}
	
	public Publisher readPublisherById(Integer publisherId) throws ClassNotFoundException, SQLException {
		try {
			PublisherDAO bdao = new PublisherDAO();
			return bdao.readPublisherById(publisherId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read publisher");
			e.printStackTrace();
			throw e;
		}
	}
	
//	public void saveLoans(Loans ln) {
//	try {
//		LoansDAO ldao = new LoansDAO();
//		ldao.addLoans(ln);
//		ldao.commit();
//	} catch (ClassNotFoundException | SQLException e) {
//		System.out.println("Could not creat loans");
//	}
//}
//
//public void updateLoans(Loans ln) {
//	try {
//		LoansDAO ldao = new LoansDAO();
//		ldao.updateLoans(ln);
//		ldao.commit();
//	} catch (ClassNotFoundException | SQLException e) {
//		System.out.println("Could not update loans");
//	}
//}
//
//public void deleteLoans(Loans ln) {
//	try {
//		LoansDAO ldao = new LoansDAO();
//		ldao.deleteLoans(ln);
//		ldao.commit();
//	} catch (ClassNotFoundException | SQLException e) {
//		System.out.println("Could not delete loan");
//	}
//}
//
//public List<Loans> readLoans() {
//	try {
//		LoansDAO ldao = new LoansDAO();
//		return ldao.readLoans();
//	} catch (ClassNotFoundException | SQLException e) {
//		System.out.println("Could not read loans");
//	}
//	return null;
//}
//
//public List<Loans> readLoans(Integer branchId, Integer cardNo) {
//	try {
//		LoansDAO ldao = new LoansDAO();
//		return ldao.readCurrentLoans(branchId, cardNo);
//	} catch (ClassNotFoundException | SQLException e) {
//		System.out.println("Could not read loans");
//	}
//	return null;
//}
//
//public Loans readLoans(Integer branchId, Integer cardNo, Integer bookId) {
//	try {
//		LoansDAO ldao = new LoansDAO();
//		return ldao.readCurrentLoans(branchId, cardNo, bookId).get(0);
//	} catch (ClassNotFoundException | SQLException e) {
//		System.out.println("Could not read loans");
//	}
//	return null;
//}

}

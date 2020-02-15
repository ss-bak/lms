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

	public void saveAuthor(Author auth) {
		try {
			AuthorDAO adao = new AuthorDAO();
			auth.setAuthorId(adao.addAuthor(auth));
			for (Book bk: auth.getBooks())
				adao.insertBookAuthors(auth, bk);
			adao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save author");
		}
	}
	
	public void updateAuthor(Author auth) {
		try {
			AuthorDAO adao = new AuthorDAO();
			adao.updateAuthor(auth);
			adao.deleteBookAuthors(auth);
			for (Book bk: auth.getBooks())
				adao.insertBookAuthors(auth, bk);
			adao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update author");
		}
	}
	
	public void deleteAuthor(Author auth) {
		try {
			AuthorDAO adao = new AuthorDAO();
			BookDAO bdao = new BookDAO();
			adao.deleteAuthor(auth);
			adao.deleteBookAuthors(auth);
			for (Book book: auth.getBooks())
				bdao.deleteBook(book);
			bdao.commit();
			adao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete author");
		}
	}
	
	public List<Author> readAuthor() {
		try {
			AuthorDAO adao = new AuthorDAO();
			return adao.readAuthors();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read author");
		}
		return null;
	}
	
	
	public void saveBook(Book book) {
		try {
			BookDAO bdao = new BookDAO();
			book.setBookId(bdao.addBook(book));
			for (Author aut: book.getAuthors()) 
				bdao.insertBookAuthors(aut, book);
			for (Genre gen: book.getGenres()) 
				bdao.insertBookGenres(gen, book);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save book");
		}
	}
	
	public void updateBook(Book book) {
		try {
			BookDAO bdao = new BookDAO();
			bdao.updateBook(book);
			bdao.deleteBookAuthors(book);
			bdao.deleteBookGenres(book);
			for (Author auth: book.getAuthors())
				bdao.insertBookAuthors(auth, book);
			for (Genre gen: book.getGenres())
				bdao.insertBookGenres(gen, book);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update book");
		}
	}
	
	public void deleteBook(Book book) {
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
		}
	}
	
	public List<Book> readBook() {
		try {
			BookDAO bdao = new BookDAO();
			return bdao.readBooks();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read book");
		}
		return null;
	}
	
	public Book readBookById(Integer bookId) {
		try {
			BookDAO bdao = new BookDAO();
			return bdao.readBookById(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read book");
		}
		return null;
	}
	
	
	public void saveGenre(Genre gen) {
		try {
			GenreDAO gdao = new GenreDAO();
			gen.setGenreId(gdao.addGenre(gen));
			for (Book bk: gen.getBooks())
				gdao.insertBookGenres(gen, bk);
			gdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create genre");
		}
	}
	
	public void updateGenre(Genre gen) {
		try {
			GenreDAO gdao = new GenreDAO();
			gdao.updateGenre(gen);
			gdao.deleteBookGenres(gen);
			for (Book book: gen.getBooks())
				gdao.insertBookGenres(gen, book);
			gdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update genre");
		}
	}
	
	public void deleteGenre(Genre gen) {
		try {
			GenreDAO gdao = new GenreDAO();
			gdao.deleteGenre(gen);
			gdao.deleteBookGenres(gen);
			gdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete genre");
		}
	}
	
	public List<Genre> readGenre() {
		try {
			GenreDAO gdao = new GenreDAO();
			return gdao.readGenre();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read genre");
		}
		return null;
	}
	
	
	public void saveBorrower(Borrower bor) {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			bdao.addBorrower(bor);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save borrower");
		}
	}
	
	public void updateBorrower(Borrower bor) {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			bdao.updateBorrower(bor);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update borrower");
		}
	}
	
	public void deleteBorrower(Borrower bor) {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			bdao.deleteBorrower(bor);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete borrower");
		}
	}
	
	public List<Borrower> readBorrower() {
		try {
			BorrowerDAO bdao = new BorrowerDAO();
			return bdao.readBorrower();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read borrower");
		}
		return null;
	}
	
	
	public void saveBranch(Branch brch) {
		try {
			BranchDAO bdao = new BranchDAO();
			bdao.addBranch(brch);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create branch");
		}
	}
	
	public void updateBranch(Branch brch) {
		try {
			BranchDAO bdao = new BranchDAO();
			bdao.updateBranch(brch);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update branch");
		}
	}
	
	public void deleteBranch(Branch brch) {
		try {
			BranchDAO bdao = new BranchDAO();
			bdao.deleteBranch(brch);
			bdao.deleteBranchCopies(brch);
			bdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete branch");
		}
	}
	
	public List<Branch> readBranch() {
		try {
			BranchDAO bdao = new BranchDAO();
			return bdao.readBranch();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read branch");
		}
		return null;
	}
	
	
	public void saveCopies(Copies copy) {
		try {
			CopiesDAO cdao = new CopiesDAO();
			cdao.addCopies(copy);
			cdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create copies");
		}
	}
	
	public void updateCopies(Copies copy) {
		try {
			CopiesDAO cdao = new CopiesDAO();
			cdao.updateCopies(copy);
			cdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update copies");
		}
	}
	
	public void deleteCopies(Copies copy) {
		try {
			CopiesDAO cdao = new CopiesDAO();
			cdao.deleteCopies(copy);
			cdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete copies");
		}
	}
	
	public List<Copies> readCopies() {
		try {
			CopiesDAO cdao = new CopiesDAO();
			return cdao.readCopies();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
		}
		return null;
	}
	
	public List<Copies> readCopiesById(Integer branchId) {
		try {
			CopiesDAO cdao = new CopiesDAO();
			return cdao.readCopyById(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
		}
		return null;
	}
	
	public Copies readCopyById(Integer branchId, Integer bookId) {
		try {
			CopiesDAO cdao = new CopiesDAO();
			return cdao.readCopyById(branchId, bookId).get(0);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
		}
		return null;
	}
	
	
//	public void saveLoans(Loans ln) {
//		try {
//			LoansDAO ldao = new LoansDAO();
//			ldao.addLoans(ln);
//			ldao.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			System.out.println("Could not creat loans");
//		}
//	}
//	
//	public void updateLoans(Loans ln) {
//		try {
//			LoansDAO ldao = new LoansDAO();
//			ldao.updateLoans(ln);
//			ldao.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			System.out.println("Could not update loans");
//		}
//	}
//	
//	public void deleteLoans(Loans ln) {
//		try {
//			LoansDAO ldao = new LoansDAO();
//			ldao.deleteLoans(ln);
//			ldao.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			System.out.println("Could not delete loan");
//		}
//	}
//	
//	public List<Loans> readLoans() {
//		try {
//			LoansDAO ldao = new LoansDAO();
//			return ldao.readLoans();
//		} catch (ClassNotFoundException | SQLException e) {
//			System.out.println("Could not read loans");
//		}
//		return null;
//	}
//	
//	public List<Loans> readLoans(Integer branchId, Integer cardNo) {
//		try {
//			LoansDAO ldao = new LoansDAO();
//			return ldao.readCurrentLoans(branchId, cardNo);
//		} catch (ClassNotFoundException | SQLException e) {
//			System.out.println("Could not read loans");
//		}
//		return null;
//	}
//	
//	public Loans readLoans(Integer branchId, Integer cardNo, Integer bookId) {
//		try {
//			LoansDAO ldao = new LoansDAO();
//			return ldao.readCurrentLoans(branchId, cardNo, bookId).get(0);
//		} catch (ClassNotFoundException | SQLException e) {
//			System.out.println("Could not read loans");
//		}
//		return null;
//	}
	
	
	public void savePublisher(Publisher pub) {
		try {
			PublisherDAO pdao = new PublisherDAO();
			pub.setPublisherId(pdao.addPublisher(pub));
			pdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not creat publisher");
		}
	}
	
	public void updatePublisher(Publisher pub) {
		try {
			PublisherDAO pdao = new PublisherDAO();
			pdao.updatePublisher(pub);
			pdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update publisher");
		}
	}
	
	public void deletePublisher(Publisher pub) {
		try {
			PublisherDAO pdao = new PublisherDAO();
			pdao.deletePublisher(pub);
			pdao.deletePublisherBooks(pub);
			pdao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Could not delete publisher");
		}
	}
	
	public List<Publisher> readPublisher() {
		try {
			PublisherDAO pdao = new PublisherDAO();
			return pdao.readPublisher();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read publisher");
		}
		return null;
	}

}

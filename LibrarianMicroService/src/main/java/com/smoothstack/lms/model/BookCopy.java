package com.smoothstack.lms.model;

public class BookCopy {
	private Book book;
	private LibraryBranch libraryBranch;
	private Integer amount;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LibraryBranch getLibraryBranch() {
		return libraryBranch;
	}

	public void setLibraryBranch(LibraryBranch libraryBranch) {
		this.libraryBranch = libraryBranch;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "BookCopy [book=" + book + ", libraryBranch=" + libraryBranch + ", amount=" + amount + "]";
	}
}

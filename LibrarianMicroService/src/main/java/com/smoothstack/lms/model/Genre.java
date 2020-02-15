package com.smoothstack.lms.model;

import java.util.List;

public class Genre {
	private Integer id;
	private String name;
	private List<Book> books;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", name=" + name + ", books=" + books + "]";
	}
}

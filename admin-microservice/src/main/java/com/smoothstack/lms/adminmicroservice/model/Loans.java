package com.smoothstack.lms.adminmicroservice.model;

import java.sql.Date;

public class Loans {
	private Integer bookId;
	private Integer branchId;
	private Integer cardNo;
	private Date dateOut;
	private Date dueDate;
	private Date dateIn;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date date) {
		this.dateOut = date;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date date) {
		this.dueDate = date;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date date) {
		this.dateIn = date;
	}
}

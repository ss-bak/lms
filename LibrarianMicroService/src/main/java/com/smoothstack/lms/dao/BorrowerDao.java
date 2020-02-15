package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.lms.model.Borrower;

public class BorrowerDao extends BaseDao<Borrower> {

	public BorrowerDao(Connection connection) {
		super(connection);
	}

	@Override
	protected List<Borrower> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNumber(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}

}
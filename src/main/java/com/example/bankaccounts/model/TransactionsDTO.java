package com.example.bankaccounts.model;

public class TransactionsDTO {

	private String type;
	private double amount;

	public TransactionsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionsDTO(String type, double amount) {
		super();
		this.type = type;
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}

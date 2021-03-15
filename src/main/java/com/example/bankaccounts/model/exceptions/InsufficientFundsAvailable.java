package com.example.bankaccounts.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InsufficientFundsAvailable extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5316220328805179518L;

	public InsufficientFundsAvailable() {
		super("Sorry, You have insufficient funds available.");
	}
	
	public InsufficientFundsAvailable(String message) {
		super(message);
	}
	
	public InsufficientFundsAvailable(String message, Throwable t) {
		super(message, t);
	}
}

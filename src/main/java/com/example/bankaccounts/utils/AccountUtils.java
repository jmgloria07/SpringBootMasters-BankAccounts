package com.example.bankaccounts.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.bankaccounts.model.Account;
import com.example.bankaccounts.model.CheckingAccount;
import com.example.bankaccounts.model.InterestAccount;
import com.example.bankaccounts.model.RegularAccount;

public class AccountUtils {
	
	public static String generateRandomAcctNum() {		
	    return new Random().ints(12, 48, 57)
	    		.mapToObj(c -> Character.toString((char) c))
	    		.collect(Collectors.joining());
	}
	
	public static void accountInitValues(Account account) {
		account.setAcctNumber(generateRandomAcctNum());
		if (account instanceof RegularAccount) {
			account.setPenalty(10);
			account.setMinimumBalance(500);
			account.setBalance(500);
		} else if (account instanceof CheckingAccount) {
			account.setMinimumBalance(100);
			account.setTransactionCharge(1);
			account.setPenalty(10);
			account.setBalance(100);
		}
	}
	
	public static void computeAccountType(Account account) {
		if (account instanceof RegularAccount) {
			computePenalty(account);
		} else if (account instanceof InterestAccount) {
			computeInterestCharge(account);
		} else if (account instanceof CheckingAccount) {
			computeInterestCharge(account);
			computePenalty(account);
			computeTransactionCharge(account);

		}
	}

	private static void computePenalty(Account account) {
		if (account.getBalance() < account.getMinimumBalance()) {
			account.setBalance(account.getBalance() - account.getPenalty());
		}
	}

	private static void computeTransactionCharge(Account account) {
		account.setBalance(account.getBalance() - account.getTransactionCharge());
	}

	private static void computeInterestCharge(Account account) {
		LocalDate lastDayOfTheMonth = LocalDate
				.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
				.with(TemporalAdjusters.lastDayOfMonth());
		if (lastDayOfTheMonth.getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
			double interest = (account.getBalance() * 0.03);
			account.setBalance(account.getBalance() + interest);
		}
	}

	
}

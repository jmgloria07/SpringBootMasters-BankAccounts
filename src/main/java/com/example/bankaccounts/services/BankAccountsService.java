package com.example.bankaccounts.services;

import java.util.List;

import com.example.bankaccounts.model.Account;
import com.example.bankaccounts.model.TransactionsDTO;

public interface BankAccountsService {
	public Account get(long id);

	public List<Account> get();

	public Account createUpdate(Account account);

	public void deleteById(long id);

	public Account createTransaction(long id, TransactionsDTO transactionsDTO);
}

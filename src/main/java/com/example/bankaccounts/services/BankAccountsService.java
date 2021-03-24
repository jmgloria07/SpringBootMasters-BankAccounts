package com.example.bankaccounts.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bankaccounts.model.Account;
import com.example.bankaccounts.model.Transaction;

public interface BankAccountsService {
	public Account get(long id);

	public List<Account> get();
	
	public Page<Account> pageableGet(Pageable pageable);

	public Account createUpdate(Account account);

	public void deleteById(long id);

	public Page<Transaction> getAllTransactions(long accountId, Pageable pageable);
	
	public Account createTransaction(long id, Transaction transaction);

	public void deleteTransaction(long accountId, long transactionId);
}

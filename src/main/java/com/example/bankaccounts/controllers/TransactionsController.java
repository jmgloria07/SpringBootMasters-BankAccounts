package com.example.bankaccounts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccounts.model.Account;
import com.example.bankaccounts.model.Transaction;
import com.example.bankaccounts.services.BankAccountsService;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
public class TransactionsController {
	
	@Autowired
	BankAccountsService bankAccountsService;
	
	@PostMapping
	public ResponseEntity<Account> createTransactions(@PathVariable("accountId") long accountId, @RequestBody Transaction transaction) {
		Account account = bankAccountsService.createTransaction(accountId, transaction);
		return new ResponseEntity<Account>(account, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Page<Transaction>> getAllTransactions(@PathVariable("accountId") long accountId, Pageable pageable) {
		Page<Transaction> transactionsPage = bankAccountsService.getAllTransactions(accountId, pageable);
		return new ResponseEntity<Page<Transaction>>(transactionsPage, new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{transactionId}")
	public ResponseEntity<Object> deleteTransaction(@PathVariable("accountId") long accountId, 
			@PathVariable("transactionId") long transactionId) {
		bankAccountsService.deleteTransaction(accountId, transactionId);
		return ResponseEntity.ok().build();
	}
	
}

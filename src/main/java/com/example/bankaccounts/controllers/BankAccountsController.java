package com.example.bankaccounts.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccounts.model.Account;
import com.example.bankaccounts.services.BankAccountsService;

@RestController
@RequestMapping("/accounts")
public class BankAccountsController {

	@Autowired
	BankAccountsService bankAccountsService;

	@GetMapping("/{id}")
	public Account getById(@PathVariable("id") long id) {
		return bankAccountsService.get(id);
	}
	
	public List<Account> getAll() {
		return bankAccountsService.get();
	}

	@GetMapping
	public ResponseEntity<Page<Account>> pageableGetAll(Pageable pageable) {
		Page<Account> accountsPage = bankAccountsService.pageableGet(pageable);
		return new ResponseEntity<Page<Account>>(accountsPage, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Account> createUpdate(@RequestBody Account account) {
		account = bankAccountsService.createUpdate(account);
		return new ResponseEntity<Account>(account, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
		account = bankAccountsService.createUpdate(account);
		return new ResponseEntity<Account>(account, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable("id") long id) {
		bankAccountsService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	
	
}

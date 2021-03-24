package com.example.bankaccounts.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bankaccounts.model.Account;
import com.example.bankaccounts.model.Transaction;
import com.example.bankaccounts.model.exceptions.InsufficientFundsAvailable;
import com.example.bankaccounts.model.exceptions.RecordNotFoundException;
import com.example.bankaccounts.repositories.BankAccountsRepository;
import com.example.bankaccounts.repositories.TransactionsRepository;
import com.example.bankaccounts.utils.AccountUtils;

@Service
public class BankAccountsServiceImpl implements BankAccountsService {

	@Autowired
	BankAccountsRepository bankAccountsRepository;
	
	@Autowired
	TransactionsRepository transactionsRepository;

	@Override
	public Account get(long id) {
		return bankAccountsRepository.findById(id).orElseThrow(RecordNotFoundException::new);
	}

	@Override
	public List<Account> get() {
		return bankAccountsRepository.findAll();
	}

	@Override
	public Account createUpdate(Account entity) {
		if (entity.getId() != null) {
			Optional<Account> foundEntity = bankAccountsRepository.findById(entity.getId());
			boolean isPresent = foundEntity.isPresent();
			if (isPresent) {
				Account updatedEntity = foundEntity.get();
				updatedEntity.setName(entity.getName());
				updatedEntity = bankAccountsRepository.save(updatedEntity);
				return updatedEntity;
			} else {
				AccountUtils.accountInitValues(entity);
				entity = bankAccountsRepository.save(entity);
				return entity;
			}
		} else {
			AccountUtils.accountInitValues(entity);
			entity = bankAccountsRepository.save(entity);
			return entity;
		}
	}

	@Override
	public void deleteById(long id) {
		bankAccountsRepository.findById(id).orElseThrow(RecordNotFoundException::new);
		bankAccountsRepository.deleteById(id);
	}

	@Override
	public Account createTransaction(long id, Transaction transaction) {
		Account account = bankAccountsRepository.findById(id)
				.orElseThrow(RecordNotFoundException::new);
		
		switch (transaction.getType().toUpperCase()) {
		case "DEPOSIT":
			account.setBalance(Double.sum(account.getBalance(), transaction.getAmount()));
			break;
		case "WITHDRAW":
			if (transaction.getAmount() > account.getBalance()) {
				throw new InsufficientFundsAvailable();
			}
			double diff = account.getBalance() - transaction.getAmount();
			account.setBalance(diff);
			break;
		}
		AccountUtils.computeAccountType(account);
		
		transaction.setAccount(account);
		transaction = transactionsRepository.save(transaction);	
		return bankAccountsRepository.findById(id).orElseThrow(RecordNotFoundException::new);
	}

	@Override
	public Page<Account> pageableGet(Pageable pageable) {
		return bankAccountsRepository.findAll(pageable);
	}

	@Override
	public Page<Transaction> getAllTransactions(long accountId, Pageable pageable) {
		bankAccountsRepository.findById(accountId).orElseThrow(RecordNotFoundException::new);
		return transactionsRepository.findAllByAccount_id(accountId, pageable);
	}

	@Override
	public void deleteTransaction(long accountId, long transactionId) {
		Account account = bankAccountsRepository.findById(accountId)
				.orElseThrow(RecordNotFoundException::new);
		
		Predicate<Transaction> getTransactionFilter = t -> t.getId() == transactionId;  
		
		//validate if transaction exists
		account.getTransactions().stream().filter(getTransactionFilter).findFirst()
			.orElseThrow(RecordNotFoundException::new);
		//remove transaction from account
		account.getTransactions().removeIf(getTransactionFilter);
		
		//save account
		bankAccountsRepository.save(account);	
	}

}

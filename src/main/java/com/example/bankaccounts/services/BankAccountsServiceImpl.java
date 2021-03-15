package com.example.bankaccounts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankaccounts.model.Account;
import com.example.bankaccounts.model.TransactionsDTO;
import com.example.bankaccounts.model.exceptions.InsufficientFundsAvailable;
import com.example.bankaccounts.model.exceptions.RecordNotFoundException;
import com.example.bankaccounts.repositories.BankAccountsRepository;
import com.example.bankaccounts.utils.AccountUtils;

@Service
public class BankAccountsServiceImpl implements BankAccountsService {

	@Autowired
	BankAccountsRepository repository;

	@Override
	public Account get(long id) {
		return repository.findById(id).orElseThrow(RecordNotFoundException::new);
	}

	@Override
	public List<Account> get() {
		return repository.findAll();
	}

	@Override
	public Account createUpdate(Account entity) {
		if (entity.getId() != null) {
			Optional<Account> foundEntity = repository.findById(entity.getId());
			boolean isPresent = foundEntity.isPresent();
			if (isPresent) {
				Account updatedEntity = foundEntity.get();
				updatedEntity.setName(entity.getName());
				updatedEntity = repository.save(updatedEntity);
				return updatedEntity;
			} else {
				AccountUtils.accountInitValues(entity);
				entity = repository.save(entity);
				return entity;
			}
		} else {
			AccountUtils.accountInitValues(entity);
			entity = repository.save(entity);
			return entity;
		}
	}

	@Override
	public void deleteById(long id) {
		repository.findById(id).orElseThrow(RecordNotFoundException::new);
		repository.deleteById(id);
	}

	@Override
	public Account createTransaction(long id, TransactionsDTO transactionsDTO) {
		Optional<Account> acc = repository.findById(id);
		Account account = acc.get();
		switch (transactionsDTO.getType().toUpperCase()) {
		case "DEPOSIT":
			account.setBalance(Double.sum(account.getBalance(), transactionsDTO.getAmount()));
			break;
		case "WITHDRAW":
			if (transactionsDTO.getAmount() > account.getBalance()) {
				throw new InsufficientFundsAvailable();
			}
			double diff = account.getBalance() - transactionsDTO.getAmount();
			account.setBalance(diff);
			break;
		}
		AccountUtils.computeAccountType(account);
		Account returnAccount = repository.save(account);
		return returnAccount;
	}

}

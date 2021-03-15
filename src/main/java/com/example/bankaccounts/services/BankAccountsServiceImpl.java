package com.example.bankaccounts.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankaccounts.model.Account;
import com.example.bankaccounts.model.CheckingAccount;
import com.example.bankaccounts.model.InterestAccount;
import com.example.bankaccounts.model.RegularAccount;
import com.example.bankaccounts.model.TransactionsDTO;
import com.example.bankaccounts.model.exceptions.RecordNotFoundException;
import com.example.bankaccounts.repositories.BankAccountsRepository;

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
				accountInitValues(entity);
				entity = repository.save(entity);
				return entity;
			}
		} else {
			accountInitValues(entity);
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
				throw new RecordNotFoundException("Sorry, You have insufficient funds available.");
			}
			double diff = account.getBalance() - transactionsDTO.getAmount();
			account.setBalance(diff);
			break;
		}
		computeAccountType(account);
		Account returnAccount = repository.save(account);
		return returnAccount;
	}

	private void computeAccountType(Account account) {
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

	private void computePenalty(Account account) {
		if (account.getBalance() < account.getMinimumBalance()) {
			account.setBalance(account.getBalance() - account.getPenalty());
		}
	}

	private void computeTransactionCharge(Account account) {
		account.setBalance(account.getBalance() - account.getTransactionCharge());
	}

	private void computeInterestCharge(Account account) {
		LocalDate lastDayOfTheMonth = LocalDate
				.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
				.with(TemporalAdjusters.lastDayOfMonth());
		if (lastDayOfTheMonth.getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
			double interest = (account.getBalance() * 0.03);
			account.setBalance(account.getBalance() + interest);
		}
	}

	private void accountInitValues(Account account) {
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

}

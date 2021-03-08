package com.example.bankaccounts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankaccounts.model.Account;
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
		Optional<Account> foundEntity = repository.findById(entity.getId());
		boolean isPresent = foundEntity.isPresent();
		
		if (isPresent) {
			Account updatedEntity = foundEntity.get();
			updatedEntity.setName(entity.getName());
			
			updatedEntity = repository.save(updatedEntity);
			return updatedEntity;
		} else {
			entity = repository.save(entity);
			return entity;
		}
	}

	@Override
	public void deleteById(long id) {
		repository.findById(id).orElseThrow(RecordNotFoundException::new);		
		repository.deleteById(id); 	
	}

}

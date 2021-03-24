package com.example.bankaccounts.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankaccounts.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
	Page<Transaction> findAllByAccount_id(Long accountId, Pageable pageable);
}

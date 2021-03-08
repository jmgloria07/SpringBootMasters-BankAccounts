package com.example.bankaccounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankaccounts.model.Account;

@Repository
public interface BankAccountsRepository extends JpaRepository<Account, Long> {

}

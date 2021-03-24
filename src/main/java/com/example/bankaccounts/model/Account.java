package com.example.bankaccounts.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = RegularAccount.class, name = "regular"),
		@JsonSubTypes.Type(value = CheckingAccount.class, name = "checking"),
		@JsonSubTypes.Type(value = InterestAccount.class, name = "interest") })
public abstract class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String acctNumber;

	private double balance;

	@JsonIgnore
	private double minimumBalance;

	@JsonIgnore
	private double penalty;

	@JsonIgnore
	private double transactionCharge;

	@JsonIgnore
	private double interestCharge;

	@JsonIgnore
	private LocalDate createdDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
	private Set<Transaction> transactions;

	public Account() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public double getPenalty() {
		return penalty;
	}

	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	public double getTransactionCharge() {
		return transactionCharge;
	}

	public void setTransactionCharge(double transactionCharge) {
		this.transactionCharge = transactionCharge;
	}

	public double getInterestCharge() {
		return interestCharge;
	}

	public void setInterestCharge(double interestCharge) {
		this.interestCharge = interestCharge;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
}

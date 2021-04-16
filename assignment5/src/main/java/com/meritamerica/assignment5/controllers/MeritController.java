package com.meritamerica.assignment5.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment5.exceptions.MissingFieldException;
import com.meritamerica.assignment5.exceptions.NotFoundException;
import com.meritamerica.assignment5.models.AccountHolder;
import com.meritamerica.assignment5.models.CDAccount;
import com.meritamerica.assignment5.models.CDOffering;
import com.meritamerica.assignment5.models.CheckingAccount;
import com.meritamerica.assignment5.models.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment5.models.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment5.models.MeritBank;
import com.meritamerica.assignment5.models.NegativeAmountException;
import com.meritamerica.assignment5.models.SavingsAccount;

@RestController
public class MeritController {
	
	Logger logs = LoggerFactory.getLogger(MeritController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "Welcome to your first Spring app";
	}
	
	// ---- CD Offers -----
	// --------------------
	// TODO complete
	@PostMapping(value = "/CDOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering addCDOfferings(@RequestBody CDOffering offer) throws MissingFieldException {
		if(offer.getInterestRate() <= 0 || offer.getInterestRate() >= 1 || offer.getTerm() <= 0) {
			throw new MissingFieldException("ID, Term or Interest Rate not within bounds");
		}
		MeritBank.setCDOfferings(offer);
		return offer;
	}
	// TODO complete
	@GetMapping(value = "/CDOfferings")
	public CDOffering[] getCDOfferings() {
		CDOffering[] cdOfferings = MeritBank.getCDOfferings();
		return cdOfferings;
	}
	
	// ---- Accounts -----
	// -------------------
	// TODO complete
	@PostMapping(value = "/AccountHolder")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
		MeritBank.addAccountHolder(accountHolder);
		return accountHolder;
	}
	// TODO complete
	@GetMapping(value = "/AccountHolder")
	public AccountHolder[] getListOfAccountHolders() {
		return MeritBank.getAccountHolders();
	}
	// TODO complete
	@GetMapping(value = "/AccountHolder/{id}")
	public AccountHolder getAccountHolderById(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder accountHolder = MeritBank.getAccountHolder(id);
		if(accountHolder == null) { 
			logs.warn("No account exists"); 
			throw new NotFoundException("Account Not Found"); 
		}
		return accountHolder;
	}
	
	// ---- Checking -----
	// -------------------
	// TODO complete
	@PostMapping(value = "/AccountHolder/{id}/CheckingAccount")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingToAccountHolder(@PathVariable("id") long id, @RequestBody CheckingAccount checkingAccount) throws NotFoundException, ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException {
		AccountHolder ah = this.getAccountHolderById(id);
		ah.addCheckingAccount(checkingAccount);
		return checkingAccount;
	}
	// TODO complete
	@GetMapping(value = "/AccountHolder/{id}/CheckingAccount")
	public CheckingAccount[] getAccountHolderCheckingAccounts(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder ah = this.getAccountHolderById(id);
		return ah.getCheckingAccounts();
	}
	
	// ----- Savings ------
	// --------------------
	// TODO complete
	@PostMapping(value = "/AccountHolder/{id}/SavingsAccount")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsToAccountHolder(@PathVariable("id") long id, @RequestBody SavingsAccount savingsAccount) throws NotFoundException, ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException {
		AccountHolder ah = this.getAccountHolderById(id);
		ah.addSavingsAccount(savingsAccount);
		return savingsAccount;
	}
	// TODO complete
	@GetMapping(value = "/AccountHolder/{id}/SavingsAccount")
	public SavingsAccount[] getAccountHolderSavingsAccounts(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder ah = this.getAccountHolderById(id);
		return ah.getSavingsAccounts();
	}
	
	// ------ CD Accounts ------
	// -------------------------
	// TODO posts but repeated TXNs, need to fix
	@PostMapping(value = "/AccountHolder/{id}/CDAccount")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDToAccountHolder(@PathVariable("id") long id, @RequestBody CDAccount cdAccount) throws NotFoundException, ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException {
		AccountHolder ah = this.getAccountHolderById(id);
		ah.addCDAccount(cdAccount);
		return cdAccount;
	}
	// TODO work on getting savings accounts
	@GetMapping(value = "/AccountHolder/{id}/CDAccount")
	public CDAccount[] getAccountHolderCDAccounts(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder ah = this.getAccountHolderById(id);
		return ah.getCDAccounts();
	}
	
}

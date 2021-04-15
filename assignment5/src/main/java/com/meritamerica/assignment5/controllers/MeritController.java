package com.meritamerica.assignment5.controllers;

import javax.validation.Valid;

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
import com.meritamerica.assignment5.models.CDOffering;
import com.meritamerica.assignment5.models.MeritBank;

@RestController
public class MeritController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "Welcome to your first Spring app";
	}
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
	// TODO complete
	@PostMapping(value = "/AccountHolder")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
		MeritBank.addAccountHolder(accountHolder);
		return accountHolder;
	}
	// TODO complete
	@GetMapping(value = "/AccountHolder")
	public AccountHolder[] geListOftAccountHolders() {
		return MeritBank.getAccountHolders();
	}
	// TODO need to add way to get by id
	@GetMapping(value = "/AccountHolder/{id}")
	public AccountHolder getAccountHolderById(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder accountHolder = MeritBank.getAccountHolder(id);
		if(accountHolder == null) throw new NotFoundException("Account Not Found");
		return accountHolder;
	}
}

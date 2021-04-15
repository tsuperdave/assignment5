package com.meritamerica.assignment5.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment5.exceptions.MissingFieldException;
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
			throw new MissingFieldException("Missing Term or Interest Rate");
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
	
	
}

package com.meritamerica.assignment5.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment5.models.CDOffering;
import com.meritamerica.assignment5.models.MeritBank;

@RestController
public class MeritController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "This is a test";
	}
	
	@PostMapping("/CDOfferings")
	public CDOffering addCDOfferings(@RequestBody CDOffering[] offer) {
		MeritBank.setCDOfferings(offer);
		return offer;
	}
	
	@GetMapping("/CDOfferings")
	public CDOffering[] getCDOfferings() {
		CDOffering[] cdOfferings = MeritBank.getCDOfferings();
		return cdOfferings;
	}
	
}

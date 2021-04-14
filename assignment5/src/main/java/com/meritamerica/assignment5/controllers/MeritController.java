package com.meritamerica.assignment5.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeritController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "This is a test";
	}
}

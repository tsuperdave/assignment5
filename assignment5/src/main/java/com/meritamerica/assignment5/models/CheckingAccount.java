package com.meritamerica.assignment5.models;

import java.text.*;
import java.util.*;

public class CheckingAccount extends BankAccount {
	
    private static final double INTEREST_RATE = 0.0001;
    
    public CheckingAccount() {
    	super(0, CheckingAccount.INTEREST_RATE);
    }
   
    public CheckingAccount(double balance) {
        super(MeritBank.getNextAccountNumber(), balance, INTEREST_RATE, new Date());
    }
    
    public CheckingAccount(double balance, double interestRate) {
    	super(balance, interestRate);
    }
    
    private CheckingAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) {
        super(accountNumber, balance, interestRate, accountOpenedOn);
    }

    public static CheckingAccount readFromString(String accountData) throws ParseException, NumberFormatException {
        String[] tempArr = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long tempAcctNum = Long.parseLong(tempArr[0]);
        double tempBal = Double.parseDouble(tempArr[1]), tempIntRate = Double.parseDouble(tempArr[2]);
        Date tempOpenDate = dateFormat.parse((tempArr[3]));

        return new CheckingAccount(tempAcctNum, tempBal, tempIntRate, tempOpenDate);
    }
}
package com.meritamerica.assignment5.models;

import java.text.*;
import java.util.*;

public class CheckingAccount extends BankAccount {
	
    private static final double INTEREST_RATE = 0.0001;
   
    CheckingAccount(double balance) {
        super(balance, INTEREST_RATE);
    }
    
    CheckingAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) {
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
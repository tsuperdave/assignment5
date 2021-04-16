package com.meritamerica.assignment5.models;

import java.text.*;
import java.util.*;

// add Override for withdraw/deposit so if term < startDate, return false

public class CDAccount extends BankAccount {

    protected CDOffering cdOffering;
    protected int term;

    public CDAccount() {
    	super (0, 0);
    	this.cdOffering = new CDOffering();
    	this.term = 0;
    }
    
    public CDAccount(CDOffering offering, double balance) {
        super(balance, offering.getInterestRate());
        this.cdOffering = offering;
    }

    private CDAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn, int term) {
        super(accountNumber, balance, interestRate, accountOpenedOn);
        this.term = term;
    }
    
    public int getTerm() {
        return this.term;
    }
    
    public CDOffering getCDOffering() {
    	return this.cdOffering;
    }

    public double futureValue() {
        return MeritBank.recursiveFutureValue(super.getBalance(), cdOffering.getTerm(), cdOffering.getInterestRate());
    }

    public static CDAccount readFromString(String accountData) throws ParseException, NumberFormatException {
        String[] tempArr = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long tempAcctNum = Long.parseLong(tempArr[0]);
        double tempBal = Double.parseDouble(tempArr[1]), tempIntRate = Double.parseDouble(tempArr[2]);
        Date tempOpenDate = dateFormat.parse(tempArr[3]);
        int tempTerm = Integer.parseInt(tempArr[4]);
        return new CDAccount(tempAcctNum, tempBal, tempIntRate, tempOpenDate, tempTerm);
    }

    public String writeToString() {
        String[] newStr = new String[]{String.valueOf(this.getAccountNumber()), String.valueOf(this.balance), String.valueOf(this.getInterestRate()), String.valueOf(openedOn), String.valueOf(this.term)};
        return String.join(",", newStr);
    }

    @Override
    public boolean withdraw(double amount) {
        Date date = new Date();
        int years = openedOn.getYear() - date.getYear();
        if (years > term) {
            if (amount <= balance && amount > 0) {
                balance -= amount;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deposit(double amount) {
        Date date = new Date();
        int years = openedOn.getYear() - date.getYear();
        if(years > term)
        {
            if(amount > 0)
            {
                balance += amount;
                return true;
            }
        }
        return false;
    }


}
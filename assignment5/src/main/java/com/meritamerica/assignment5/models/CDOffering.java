package com.meritamerica.assignment5.models;

import javax.validation.constraints.NotNull;

public class CDOffering
{
	// protected static int nextOfferId = 0;
	// protected int offerId;
    protected int term;
    protected double interestRate;
    
    public CDOffering() {
    	this.term = 0;
    	this.interestRate = 0;
    }
    
    public CDOffering(int term, double interestRate) {
    	// this.offerId = nextOfferId++;
        this.term = term;
        this.interestRate = interestRate;
    }

//    public int getOfferId() {
//    	return offerId;
//    }
    
    public int getTerm() {
        return term;
    }

    public double getInterestRate() {
        return interestRate;
    }
    
//    public void setOfferId(int offerId) {
//    	this.offerId = offerId;
//    }
    
    public void setTerm(int term) {
    	this.term = term;
    }
    
    public void setInterestRate(double interestRate) {
    	this.interestRate = interestRate;
    }

    public static CDOffering readFromString(String cdOfferingDataString) {

        int tempTerm;
        double tempIntRate;
        int comma = cdOfferingDataString.indexOf(",");
        if(comma >= 0) {
            tempTerm = Integer.parseInt(cdOfferingDataString.substring(0, comma));
            tempIntRate = Double.parseDouble(cdOfferingDataString.substring(comma + 1));
        } else {
            System.out.println("CD Offering data format incorrect");
            throw new NumberFormatException();
        }
        return new CDOffering(tempTerm, tempIntRate);
    }

    public String writeToString() {
        return this.term + "," + this.interestRate + "\n";
    }

}
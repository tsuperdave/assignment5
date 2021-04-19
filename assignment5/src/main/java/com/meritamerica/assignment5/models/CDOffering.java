package com.meritamerica.assignment5.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class CDOffering
{
	private static int nextOfferId = 0;
	
	private int id;
	
	@Min(value = 1, message = "term must be greater than 1.")
    private int term;
	
    @Positive(message = "interestRate must be greater than 0.")
    @Max(value = 1, message = "interestRate must be less than 1.")
    private double interestRate;
    
    public CDOffering() {
    	this.id = ++CDOffering.nextOfferId;
    	//this.term = 5;
    	//this.interestRate = 0.025;
    }
    
    public CDOffering(int term, double interestRate) {
    	this.id = ++CDOffering.nextOfferId;
        this.term = term;
        this.interestRate = interestRate;
    }

    public int getId() {
    	return this.id;
    }
    
    public int getTerm() {
        return term;
    }

    public double getInterestRate() {
        return interestRate;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
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
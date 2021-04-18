package com.meritamerica.assignment5.models;

public class CDOffering
{
	private static int nextOfferId = 0;
	private int id;
    private int term;
    private double interestRate;
    
    public CDOffering() {
    	this.id = ++CDOffering.nextOfferId;
    	this.term = 5;
    	this.interestRate = 0.025;
    }
    
    public CDOffering(int term, double interestRate) {
    	this.id = ++CDOffering.nextOfferId;
        this.term = term;
        this.interestRate = interestRate;
    }

    public int getid() {
    	return this.id;
    }
    
    public int getTerm() {
        return term;
    }

    public double getInterestRate() {
        return interestRate;
    }
    
    public void setid(int id) {
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
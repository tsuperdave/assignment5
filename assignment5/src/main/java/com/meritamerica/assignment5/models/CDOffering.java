package com.meritamerica.assignment5.models;

public class CDOffering
{
    protected int term;
    protected double interestRate;
    
    CDOffering() {
    
    }

    CDOffering(int term, double interestRate) {
        this.term = term;
        this.interestRate = interestRate;
    }

    int getTerm() {
        return this.term;
    }

    double getInterestRate() {
        return this.interestRate;
    }

    static CDOffering readFromString(String cdOfferingDataString) {

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

    String writeToString() {
        return this.term + "," + this.interestRate + "\n";
    }

}
package com.meritamerica.assignment5.models;

import java.util.ArrayList;
import java.util.List;

public class FraudQueue {
    private List<Transaction> listOfTransactions = new ArrayList<Transaction>();

    FraudQueue(){}

    public void addTransaction(Transaction transaction) {
        listOfTransactions.add(transaction);
    }

    List<Transaction> getTransaction() {
        return listOfTransactions;
    }

}
package com.meritamerica.assignment5.models;

public class ExceedsAvailableBalanceException extends Exception {
    ExceedsAvailableBalanceException(String error) { super(error); }
}
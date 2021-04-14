package com.meritamerica.assignment5.models;

public class ExceedsFraudSuspicionLimitException extends Exception {
    ExceedsFraudSuspicionLimitException(String error) { super(error);
    }
}
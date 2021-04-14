package com.meritamerica.assignment5.models;

public class ExceedsCombinedBalanceLimitException extends Exception {
    ExceedsCombinedBalanceLimitException(String error)
    {
        super(error);
    }
}
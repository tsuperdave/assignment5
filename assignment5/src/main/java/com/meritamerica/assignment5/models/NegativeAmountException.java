package com.meritamerica.assignment5.models;

public class NegativeAmountException extends Exception
{
    NegativeAmountException(String error)
    {
        super(error);
    }
}
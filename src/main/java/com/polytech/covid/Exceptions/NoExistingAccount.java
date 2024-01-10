package com.polytech.covid.Exceptions;

public class NoExistingAccount extends Exception{
    
    public NoExistingAccount(String message){
        super(message);
    }
}

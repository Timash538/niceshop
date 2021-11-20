package com.niceshop.exceptions;

public class EmailAlreadyExists extends Exception{
    public  EmailAlreadyExists(String errorMessage) {super(errorMessage);}
    public  EmailAlreadyExists(String errorMessage, Throwable err) {super(errorMessage, err);}
}

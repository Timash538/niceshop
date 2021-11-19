package com.niceshop.niceshop.exceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String errorMessage) {
        super(errorMessage);
    }
    public UserAlreadyExists(String errorMessage,Throwable err) {super(errorMessage, err);}
}

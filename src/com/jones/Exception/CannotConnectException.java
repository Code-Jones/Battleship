package com.jones.Exception;

public class CannotConnectException extends Exception {

    public CannotConnectException() {
        super("Client cannot connect");
    }
}

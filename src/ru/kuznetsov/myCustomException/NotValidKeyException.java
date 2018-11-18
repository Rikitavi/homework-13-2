package ru.kuznetsov.myCustomException;

public class NotValidKeyException extends Exception {
    public NotValidKeyException(String message) {
        super(message);
    }
}

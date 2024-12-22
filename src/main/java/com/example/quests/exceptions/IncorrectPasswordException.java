package com.example.quests.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("Некорректный пароль");
    }
}

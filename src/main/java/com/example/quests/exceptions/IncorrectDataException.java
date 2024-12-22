package com.example.quests.exceptions;

public class IncorrectDataException extends RuntimeException{
    public IncorrectDataException(String message){
        super("Некорректные данные: " + message);
    }
}

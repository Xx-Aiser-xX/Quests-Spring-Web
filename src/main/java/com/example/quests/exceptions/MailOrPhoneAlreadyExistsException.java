package com.example.quests.exceptions;

public class MailOrPhoneAlreadyExistsException extends RuntimeException{
    public MailOrPhoneAlreadyExistsException(String message){
        super("Пользователь с " + message + " уже существует");
    }
}

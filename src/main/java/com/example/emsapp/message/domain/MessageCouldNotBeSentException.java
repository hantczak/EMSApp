package com.example.emsapp.message.domain;

public class MessageCouldNotBeSentException extends RuntimeException{

    public MessageCouldNotBeSentException(Throwable cause){
        super("Message could not be sent. ", cause);
    }
}

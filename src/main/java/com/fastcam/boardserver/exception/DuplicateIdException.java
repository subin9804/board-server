package com.fastcam.boardserver.exception;

public class DuplicateIdException extends RuntimeException{

    public DuplicateIdException(String msg) {
        super(msg);
    }
}

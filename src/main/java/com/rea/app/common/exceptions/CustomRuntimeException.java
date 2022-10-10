package com.rea.app.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomRuntimeException extends RuntimeException{

    String message;

    public CustomRuntimeException(String message){
        super(String.format("Internal Server Error, Reason: %s",message));
        this.message = message;
    }

}

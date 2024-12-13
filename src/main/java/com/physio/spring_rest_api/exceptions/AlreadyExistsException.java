package com.physio.spring_rest_api.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyExistsException extends RuntimeException{

    String msg;

    public AlreadyExistsException(String msg){
        super(msg);
        this.msg = msg;
    }

}

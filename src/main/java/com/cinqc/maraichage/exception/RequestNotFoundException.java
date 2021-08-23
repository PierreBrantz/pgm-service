package com.cinqc.maraichage.exception;

public class RequestNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RequestNotFoundException(){
        super();
    }

    public RequestNotFoundException(String msg){
        super(msg);
    }

}

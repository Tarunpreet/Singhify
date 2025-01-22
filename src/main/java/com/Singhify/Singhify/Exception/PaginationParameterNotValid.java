package com.Singhify.Singhify.Exception;

public class PaginationParameterNotValid extends RuntimeException{

    private String fieldName;

    public PaginationParameterNotValid(String fieldName) {
        super(fieldName + " should be greater than 0");
        this.fieldName = fieldName;
    }
}

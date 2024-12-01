package com.Singhify.Singhify.Exception;

public class PaginationParameterNotValid extends RuntimeException{

    private String feildName;

    public PaginationParameterNotValid(String feildName) {
        super(feildName + " should be greater than 0");
        this.feildName = feildName;
    }
}

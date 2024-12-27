package com.Singhify.Singhify.Exception;

public class ParameternotValid extends  RuntimeException{
    private String feildName;

    public ParameternotValid(String feildName) {
        super("Given "+feildName + " is not Valid. Please check and try again.");
        this.feildName = feildName;
    }
}

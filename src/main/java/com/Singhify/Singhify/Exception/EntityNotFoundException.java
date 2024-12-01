package com.Singhify.Singhify.Exception;

public class EntityNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private String fieldValue;


    public EntityNotFoundException(String resourceName)
    {
        super("No "+resourceName+" found");
        this.resourceName=resourceName;
    }


    public EntityNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(resourceName + " not found with " + fieldName + ": " + fieldValue);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = String.valueOf(fieldValue);
    }

}

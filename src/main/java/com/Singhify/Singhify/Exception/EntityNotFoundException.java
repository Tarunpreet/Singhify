package com.Singhify.Singhify.Exception;

public class EntityNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private int fieldValue;
    private String fieldValueStr;

    public EntityNotFoundException(String resourceName)
    {
        super("No "+resourceName+" found");
        this.resourceName=resourceName;
    }

    public EntityNotFoundException(String resourceName,String fieldName,int fieldValue)
    {
       super(resourceName+" not found with "+fieldName+":"+fieldValue);
       this.resourceName=resourceName;
       this.fieldName=fieldName;
       this.fieldValue=fieldValue;
    }
    public EntityNotFoundException(String resourceName,String fieldName,String fieldValue)
    {
        super(resourceName+" not found with "+fieldName+":"+fieldValue);
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValueStr=fieldValue;
    }

}

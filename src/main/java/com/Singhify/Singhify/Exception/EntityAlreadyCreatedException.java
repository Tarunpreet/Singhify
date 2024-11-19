package com.Singhify.Singhify.Exception;

public class EntityAlreadyCreatedException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private int fieldValue;
    private String fieldValueStr;

    public EntityAlreadyCreatedException(String resourceName, String fieldName, int fieldValue) {
        super(resourceName + " already created with " + fieldName + ":" + fieldValue);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public EntityAlreadyCreatedException(String resourceName, String fieldName, String fieldValue) {
        super(resourceName + " already created with " + fieldName + ":" + fieldValue);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValueStr = fieldValue;
    }
}
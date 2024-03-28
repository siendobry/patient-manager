package com.esatto.clinic.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

public class UniqueConstraintViolation implements ConstraintViolation<Object> {
    // Provisional implementation only to handle all ConstraintViolationErrors in one ExceptionHandler
    // Created to wrap JPA level unique constraint exception

    private final String message;

    public UniqueConstraintViolation(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getMessageTemplate() {
        return message;
    }

    @Override
    public Object getRootBean() {
        return null;
    }

    @Override
    public Class getRootBeanClass() {
        return null;
    }

    @Override
    public Object getLeafBean() {
        return null;
    }

    @Override
    public Object[] getExecutableParameters() {
        return new Object[0];
    }

    @Override
    public Object getExecutableReturnValue() {
        return null;
    }

    @Override
    public Path getPropertyPath() {
        return null;
    }

    @Override
    public Object getInvalidValue() {
        return null;
    }

    @Override
    public ConstraintDescriptor<?> getConstraintDescriptor() {
        return null;
    }

    @Override
    public Object unwrap(Class aClass) {
        return null;
    }
}

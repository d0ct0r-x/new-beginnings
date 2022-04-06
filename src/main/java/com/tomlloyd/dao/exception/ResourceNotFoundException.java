package com.tomlloyd.dao.exception;

public class ResourceNotFoundException extends DaoException
{
    public ResourceNotFoundException(String message)
    {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

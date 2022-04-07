package com.tomlloyd.service.exception;

import java.util.List;

public class ValidationException extends ServiceException
{
    private List<String> violations;

    public ValidationException(String message, List<String> violations)
    {
        super(message);
        this.violations = violations;
    }

    public ValidationException(String message, Throwable cause, List<String> violations)
    {
        super(message, cause);
        this.violations = violations;
    }

    public List<String> getViolations()
    {
        return violations;
    }
}

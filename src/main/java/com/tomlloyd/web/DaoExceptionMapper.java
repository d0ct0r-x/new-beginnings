package com.tomlloyd.web;

import com.tomlloyd.dao.exception.ConstraintViolationException;
import com.tomlloyd.dao.exception.DaoException;
import com.tomlloyd.dao.exception.ResourceNotFoundException;
import com.tomlloyd.model.StandardError;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
@ApplicationScoped
@Log
public class DaoExceptionMapper implements ExceptionMapper<DaoException>
{
    private static final Response NOT_FOUND = StandardError.toResponse(Response.Status.NOT_FOUND, "NOT FOUND", "The requested data resource was not found");
    private static final Response CONSTRAINT = StandardError.toResponse(Response.Status.FORBIDDEN, "CONSTRAINT VIOLATION", "Attempted data operation failed");
    private static final Response UNKNOWN = StandardError.toResponse(Response.Status.INTERNAL_SERVER_ERROR, "UNKNOWN", "An unknown data error occurred");

    @Override
    public Response toResponse(DaoException e)
    {
        if (isSubclass(e))
        {
            log.warning(() -> String.format("Caught dao exception: %s: %s", e.getClass().getSimpleName(), e.getMessage()));
        }
        else
        {
            log.log(Level.SEVERE, e, () -> "Caught dao exception");
        }

        return mapResponse(e);
    }

    private boolean isSubclass(DaoException e)
    {
        return e.getClass() != DaoException.class;
    }

    private Response mapResponse(DaoException e)
    {
        if (e instanceof ResourceNotFoundException) return NOT_FOUND;
        if (e instanceof ConstraintViolationException) return CONSTRAINT;

        return UNKNOWN;
    }
}

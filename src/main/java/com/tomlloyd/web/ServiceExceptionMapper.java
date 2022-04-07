package com.tomlloyd.web;

import com.tomlloyd.model.StandardError;
import com.tomlloyd.service.exception.ServiceException;
import com.tomlloyd.service.exception.ValidationException;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
@ApplicationScoped
@Log
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException>
{
    private static final Response INVALID = StandardError.toResponse(Response.Status.BAD_REQUEST, "INVALID", "Invalid request received");
    private static final Response UNKNOWN = StandardError.toResponse(Response.Status.INTERNAL_SERVER_ERROR, "UNKNOWN", "An unknown service error occurred");

    @Override
    public Response toResponse(ServiceException e)
    {
        if (isSubclass(e))
        {
            log.warning(() -> String.format("Caught service exception: %s: %s", e.getClass().getSimpleName(), e.getMessage()));

            if (e instanceof ValidationException)
            {
                log.warning(() -> String.format("Violations [%s]", ((ValidationException) e).getViolations()));
            }
        }
        else
        {
            log.log(Level.SEVERE, e, () -> "Caught service exception");
        }

        return mapResponse(e);
    }

    private boolean isSubclass(ServiceException e)
    {
        return e.getClass() != ServiceException.class;
    }

    private Response mapResponse(ServiceException e)
    {
        if (e instanceof ValidationException) return INVALID;
        return UNKNOWN;
    }
}

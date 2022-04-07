package com.tomlloyd.web;

import com.tomlloyd.model.StandardError;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

/**
 * Catches any unhandled exceptions and returns a standard response
 */
@Log
@Provider
@ApplicationScoped
public class GlobalExceptionMapper implements ExceptionMapper<Exception>
{
    private static final Response UNKNOWN = StandardError.toResponse(Response.Status.INTERNAL_SERVER_ERROR, "UNKNOWN", "An unknown error occurred");

    @Override
    public Response toResponse(Exception e)
    {
        log.log(Level.SEVERE, e, () -> "Caught global exception");
        return e instanceof WebApplicationException
                ? ((WebApplicationException) e).getResponse()
                : UNKNOWN;
    }
}

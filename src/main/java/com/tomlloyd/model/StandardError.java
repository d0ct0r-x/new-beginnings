package com.tomlloyd.model;

import lombok.Builder;
import lombok.Value;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Standard error response for failed requests.
 * Used with caught exceptions to return a consistent response.
 */
@Value
@Builder
public class StandardError
{
    /**
     * The HTTP status code.
     */
    int status;

    /**
     * Short description of the error type.
     */
    String title;

    /**
     * More detailed description of the error.
     */
    String detail;

    /**
     * Convenience method for creating {@link Response}.
     */
    public static Response toResponse(Response.Status status, String title, String detail)
    {
        return Response
                .status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(StandardError.builder()
                        .status(status.getStatusCode())
                        .title(title)
                        .detail(detail)
                        .build())
                .build();
    }
}


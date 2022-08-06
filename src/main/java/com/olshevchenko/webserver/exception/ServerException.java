package com.olshevchenko.webserver.exception;

import com.olshevchenko.webserver.entity.Response;
import com.olshevchenko.webserver.entity.StatusCode;

/**
 * @author Oleksandr Shevchenko
 */
public class ServerException extends RuntimeException{
    private final StatusCode statusCode;

    public ServerException(String message, Throwable cause, StatusCode statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public ServerException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}

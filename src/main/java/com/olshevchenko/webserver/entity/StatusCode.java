package com.olshevchenko.webserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "NOT FOUND"),
    BAD_REQUEST(400, "BAD REQUEST"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR");

    private final int statusCode;
    private final String statusText;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(statusCode).append(" ");
        sb.append(statusText);
        return sb.toString();
    }
}

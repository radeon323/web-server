package com.olshevchenko.webserver.entity;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String methodName;

    HttpMethod(String methodName) {
        this.methodName = methodName;
    }

    public static HttpMethod getHttpMethod(String methodName) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> methodName.equals(httpMethod.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Method not supported: " + methodName));
    }

}

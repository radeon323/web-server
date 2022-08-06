package com.olshevchenko.webserver.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@Setter
public class Request {
    private String uri;
    private HttpMethod httpMethod;
    private Map<String, String[]> headers;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Request{");
        sb.append("uri = '").append(uri).append('\'');
        sb.append(", httpMethod = ").append(httpMethod);
        sb.append(", headers = ").append(showMap(headers));
        sb.append('}');
        return sb.toString();
    }

    private String showMap(Map<String, String[]> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String[]> stringEntry : map.entrySet()) {
            sb.append(stringEntry.getKey()).append(": ");
            sb.append(Arrays.toString(stringEntry.getValue())).append(" ");
        }
        return sb.toString();
    }
}

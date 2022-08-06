package com.olshevchenko.webserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.stream.ImageInputStream;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@Setter
public class Response {
    private StatusCode statusCode;
    private BufferedReader content;
    private ImageInputStream image;
    private Map<String, String[]> headers;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Response{");
        sb.append("statusCode = ").append(statusCode);
        sb.append(", content = ").append(content);
        sb.append(", headers = ").append(showMap(headers));
        sb.append('}');
        return sb.toString();
    }

    private String showMap(Map<String, String[]> map) {
        StringBuffer sb = new StringBuffer();
        if (map != null) {
            for (Map.Entry<String, String[]> stringEntry : map.entrySet()) {
                sb.append(stringEntry.getKey()).append(": ");
                sb.append(Arrays.toString(stringEntry.getValue())).append(" ");
            }
        }
        return sb.toString();
    }
}

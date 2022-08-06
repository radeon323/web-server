package com.olshevchenko.webserver;

import com.olshevchenko.webserver.entity.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.olshevchenko.webserver.entity.HttpMethod.getHttpMethod;

/**
 * @author Oleksandr Shevchenko
 */
public class RequestParser {

    public static Request parseRequest(BufferedReader bufferedReader) {
        try {
            Request request = new Request();

            setUriAndHttpMethod(bufferedReader, request);
            setHeaders(bufferedReader, request);

            return request;
        } catch (IOException e) {
            throw new RuntimeException("An I/O exception occurred", e);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new RuntimeException("Could not parse request", e);
        }
    }

    private static void setUriAndHttpMethod(BufferedReader reader, Request request) throws IOException {
        String[] firstRowOfRequest = reader.readLine().split("\\s");
        String httpMethod = firstRowOfRequest[0];
        String uri = firstRowOfRequest[1];
        if (Objects.equals(uri, "/")) {
            uri = "/index.html";
        }
        request.setHttpMethod(getHttpMethod(httpMethod));
        request.setUri(uri);
    }

    private static void setHeaders(BufferedReader reader, Request request) throws IOException {
        String line;
        Map<String, String[]> headers = new HashMap<>();
        while (!(line = reader.readLine()).isEmpty()) {
            String[] header = line.split(": ");
            String[] options = header[1].split(", ");
            headers.put(header[0], options);
        }
        request.setHeaders(headers);
    }
}

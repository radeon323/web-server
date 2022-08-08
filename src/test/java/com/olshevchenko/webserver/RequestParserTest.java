package com.olshevchenko.webserver;

import com.olshevchenko.webserver.entity.HttpMethod;
import com.olshevchenko.webserver.entity.Request;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleksandr Shevchenko
 */
class RequestParserTest {

    @Test
    void testParseRequest() throws IOException {
        String request = "GET /index.html HTTP/1.1\n" +
                "User-Agent: firefox/5.0 (Linux; Debian 5.0.8; en-US; rv:1.8.1.7) Gecko/20070914 Firefox/2.0.0.7\n" +
                "Host: localhost:3001\n" +
                "Accept-Language: en-us\n" +
                "Connection: Keep-Alive\r\n\r\n";

        Map<String, String[]> headers = new HashMap<>();
        headers.put("User-Agent", new String[]{"firefox/5.0 (Linux; Debian 5.0.8; en-US; rv:1.8.1.7) Gecko/20070914 Firefox/2.0.0.7"});
        headers.put("Host", new String[]{"localhost:3001"});
        headers.put("Accept-Language", new String[]{"en-us"});
        headers.put("Connection", new String[]{"Keep-Alive"});

        Request expectedRequest = new Request();
        expectedRequest.setUri("/index.html");
        expectedRequest.setHttpMethod(HttpMethod.GET);
        expectedRequest.setHeaders(headers);

        Request actualRequest;

        try (BufferedReader bufferedReader = new BufferedReader(
                     new InputStreamReader(new ByteArrayInputStream(request.getBytes())));) {
            actualRequest = RequestParser.parseRequest(bufferedReader);
        }

        assertEquals(expectedRequest.getUri(), actualRequest.getUri());
        assertEquals(expectedRequest.getHttpMethod(), actualRequest.getHttpMethod());
        assertArrayEquals(expectedRequest.getHeaders().keySet().toArray(), actualRequest.getHeaders().keySet().toArray());

    }
}
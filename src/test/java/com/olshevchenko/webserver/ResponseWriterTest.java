package com.olshevchenko.webserver;

import com.olshevchenko.webserver.entity.Response;
import com.olshevchenko.webserver.entity.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleksandr Shevchenko
 */
class ResponseWriterTest {

    @Test
    void testWriteResponse() throws IOException {
        String content = "Hello";
        String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n" + content + "\r\n";
        try (ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(byteArrayOutputStream));
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(content.getBytes())));) {

            Response response = new Response();
            response.setContent(bufferedReader);
            response.setStatusCode(StatusCode.OK);

            ResponseWriter.writeResponse(bufferedWriter, response);
            bufferedWriter.flush();
            String actualResponse = byteArrayOutputStream.toString();
            assertEquals(expectedResponse, actualResponse);
        }
    }


    @Test
    void testWriteErrorResponse() throws IOException {
        String content =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<style> .status {text-align: center;padding: 100px; color: blue;}</style>" +
                        "</head>" +
                        "<body>" +
                        "<h1 class=\"status\">404 NOT FOUND</h1>" +
                        "</body>" +
                        "</html>";
        StatusCode statusCode = StatusCode.NOT_FOUND;
        String expectedResponse = "HTTP/1.1 404 NOT FOUND\r\n\r\n" + content;
        try(ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(byteArrayOutputStream));) {

            ResponseWriter.writeResponse(bufferedWriter, statusCode);
            bufferedWriter.flush();
            String actualResponse = byteArrayOutputStream.toString();
            assertEquals(expectedResponse, actualResponse);
        }
    }
}
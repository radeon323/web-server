package com.olshevchenko.webserver;

import com.olshevchenko.webserver.entity.Response;
import com.olshevchenko.webserver.entity.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ResponseWriterTest {

    @Test
    void testWriteResponse() throws IOException {
        String request = "Hello";
        String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n" + request + "\r\n";
        try(ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(byteArrayOutputStream));
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(request.getBytes())));) {

            Response response = new Response();
            response.setContent(bufferedReader);
            response.setStatusCode(StatusCode.OK);

            ResponseWriter.writeResponse(bufferedWriter, response);
            bufferedWriter.close();
            String actualResponse = byteArrayOutputStream.toString();
            assertEquals(expectedResponse, actualResponse);
        }
    }


    @Test
    void testWriteErrorResponse() throws IOException {

    }
}
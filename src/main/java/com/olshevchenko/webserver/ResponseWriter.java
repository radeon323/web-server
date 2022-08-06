package com.olshevchenko.webserver;

import com.olshevchenko.webserver.entity.Response;
import com.olshevchenko.webserver.entity.StatusCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.rmi.ServerException;

/**
 * @author Oleksandr Shevchenko
 */
public class ResponseWriter implements AutoCloseable {
    private final BufferedWriter bufferedWriter;
    private static final String RESPONSE = "HTTP/1.1 %s\r\n";
    private static final String BLANK =
            "<!DOCTYPE html>" +
                    "<html>" +
                        "<head>" +
                            "<style> .status {text-align: center;padding: 100px; color: blue;}</style>" +
                        "</head>" +
                        "<body>" +
                            "<h1 class=\"status\">%s</h1>" +
                        "</body>" +
                    "</html>";

    public ResponseWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public void writeResponse(Response response) throws IOException {
        try {
            StatusCode statusCode = response.getStatusCode();
            bufferedWriter.write(String.format(RESPONSE, statusCode));
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            writeContent(response);
        } catch (IOException e) {
            throw new ServerException("An I/O exception occurred", e);
        }
    }

    public void writeResponse(StatusCode statusCode) throws IOException {
        try {
            bufferedWriter.write(String.format(RESPONSE, statusCode));
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(String.format(BLANK, statusCode));
        } catch (IOException e) {
            throw new ServerException("An I/O exception occurred", e);
        }
    }

    private void writeContent(Response response) throws IOException {
        BufferedReader bufferedReader = response.getContent();
        char[] buffer = new char[1024];
        while (bufferedReader.read(buffer) != -1) {
            bufferedWriter.write(buffer);
            bufferedWriter.newLine();
        }
        bufferedReader.close();
    }


    @Override
    public void close() throws Exception {
        bufferedWriter.close();
    }
}

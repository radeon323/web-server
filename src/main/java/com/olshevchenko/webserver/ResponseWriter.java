package com.olshevchenko.webserver;

import com.olshevchenko.webserver.entity.Response;
import com.olshevchenko.webserver.entity.StatusCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Oleksandr Shevchenko
 */
public abstract class ResponseWriter {
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

    public static void writeResponse(BufferedWriter bufferedWriter, Response response) throws IOException {
        try {
            StatusCode statusCode = response.getStatusCode();
            bufferedWriter.write(String.format(RESPONSE, statusCode));
            if (response.getHeaders() != null) {
                writeHeaders(bufferedWriter, response.getHeaders());
            }
            bufferedWriter.write("\r\n");
            writeContent(bufferedWriter, response);
        } catch (IOException e) {
            throw new ServerException("An I/O exception occurred", e);
        }
    }

    public static void writeResponse(BufferedWriter bufferedWriter, StatusCode statusCode) throws IOException {
        try {
            bufferedWriter.write(String.format(RESPONSE, statusCode));
            bufferedWriter.write("\r\n");
            bufferedWriter.write(String.format(BLANK, statusCode));
        } catch (IOException e) {
            throw new ServerException("An I/O exception occurred", e);
        }
    }

    private static void writeContent(BufferedWriter bufferedWriter, Response response) throws IOException {
        BufferedReader bufferedReader = response.getContent();
        if (bufferedReader != null) {
            char[] buffer = new char[1024];
            int count;
            while ((count = bufferedReader.read(buffer)) != -1) {
                bufferedWriter.write(buffer, 0, count);
                bufferedWriter.newLine();
            }
            bufferedReader.close();
        }
    }

    private static void writeHeaders(BufferedWriter bufferedWriter, Map<String, String[]> headers) throws IOException {
        for (Map.Entry<String, String[]> header : headers.entrySet()) {
            bufferedWriter.write(String.format("%s: %s%n", header.getKey(), Arrays.toString(header.getValue())));
        }
    }


}

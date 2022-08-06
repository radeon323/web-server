package com.olshevchenko.webserver;

import com.olshevchenko.webserver.entity.Request;
import com.olshevchenko.webserver.entity.Response;
import com.olshevchenko.webserver.entity.StatusCode;
import com.olshevchenko.webserver.exception.ServerException;

import java.io.*;

/**
 * @author Oleksandr Shevchenko
 */
public class RequestHandler {
    private final String webAppPath;
    private final BufferedReader bufferedReader;
    private final ResponseWriter responseWriter;


    RequestHandler(String webAppPath, BufferedReader bufferedReader, ResponseWriter responseWriter) {
        this.webAppPath = webAppPath;
        this.bufferedReader = bufferedReader;
        this.responseWriter = responseWriter;
    }

    void handle() throws IOException {
        try {
            Request request = RequestParser.parseRequest(bufferedReader);
            String uri = request.getUri();

            System.out.println(request);

            Response response = new Response();

            response.setContent(readContent(uri));
            response.setStatusCode(StatusCode.OK);
            responseWriter.writeResponse(response);

        } catch (ServerException e) {
            System.out.println(e.getStatusCode());
            responseWriter.writeResponse(e.getStatusCode());
        }
    }

    private BufferedReader readContent(String uri) {
        try {
            FileReader fileReader = new FileReader(webAppPath + uri);
            return new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            throw new ServerException(StatusCode.NOT_FOUND);
        }
    }



}

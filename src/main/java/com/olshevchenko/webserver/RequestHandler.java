package com.olshevchenko.webserver;

import com.olshevchenko.webserver.entity.Request;
import com.olshevchenko.webserver.entity.Response;
import com.olshevchenko.webserver.entity.StatusCode;
import com.olshevchenko.webserver.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Oleksandr Shevchenko
 */
public class RequestHandler {
    private final String webAppPath;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    Logger logger = LoggerFactory.getLogger(getClass());

    RequestHandler(String webAppPath, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        this.webAppPath = webAppPath;
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
    }

    void handle() throws IOException {
        try {
            Request request = RequestParser.parseRequest(bufferedReader);
            String uri = request.getUri();
            Response response = new Response();

            //TODO remove this f..ing spike
            if (!Objects.equals(uri, "/null")) {
                logger.info("\u001B[37m" + "Request on {}", request.getUri() + ", HTTPmethod " + request.getHttpMethod() + "\u001B[0m");
                logger.info("\u001B[34m" + request + "\u001B[0m");

                Map<String, String[]> headers = new HashMap<>();
                headers.put("Server", new String[]{"SimpleWebServer"});
                headers.put("Content-Language", new String[]{"uk"});

                response.setContent(readContent(uri));
                response.setHeaders(headers);
                response.setStatusCode(StatusCode.OK);
                logger.info("\u001B[33m" + response + "\u001B[0m");
                ResponseWriter.writeResponse(bufferedWriter, response);
            }

        } catch (ServerException e) {
            e.printStackTrace();
            ResponseWriter.writeResponse(bufferedWriter, e.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            ResponseWriter.writeResponse(bufferedWriter, StatusCode.INTERNAL_SERVER_ERROR);
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

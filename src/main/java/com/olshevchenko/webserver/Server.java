package com.olshevchenko.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Oleksandr Shevchenko
 */
public class Server {

    private static int portNumber;
    private static String webAppPath;

    Logger logger = LoggerFactory.getLogger(getClass());

    void start() throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
            logger.info("Server started!");
            while (!serverSocket.isClosed()) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader bufferedReader = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bufferedWriter = new BufferedWriter(
                             new OutputStreamWriter(socket.getOutputStream())) ) {

                    RequestHandler requestHandler = new RequestHandler(webAppPath, bufferedReader, bufferedWriter);
                    requestHandler.handle();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        logger.info("Server stopped");

    }

    static void setPort(int port) {
        portNumber = port;
    }

    static void setWebAppPath(String path) {
        webAppPath = path;
    }
}

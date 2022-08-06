package com.olshevchenko.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Oleksandr Shevchenko
 */
public class Server {

    private static int portNumber;
    private static String webAppPath;

    static void start() throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
            System.out.println("Server started!");
            while (!serverSocket.isClosed()) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader bufferedReader = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()));
                     ResponseWriter responseWriter = new ResponseWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))) {

                    RequestHandler requestHandler = new RequestHandler(webAppPath, bufferedReader, responseWriter);
                    requestHandler.handle();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }


        System.out.println("Finish");

    }

    static void setPort(int port) {
        portNumber = port;
    }

    static void setWebAppPath(String path) {
        webAppPath = path;
    }
}

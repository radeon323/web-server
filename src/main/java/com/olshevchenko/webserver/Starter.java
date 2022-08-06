package com.olshevchenko.webserver;

import java.io.IOException;

/**
 * @author Oleksandr Shevchenko
 */
public class Starter {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.setPort(3001);
        server.setWebAppPath("src/main/resources");
        server.start();
    }
}

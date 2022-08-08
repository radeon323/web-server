package com.olshevchenko.webserver;

/**
 * @author Oleksandr Shevchenko
 */
public class Starter {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        Server.setPort(3001);
        Server.setWebAppPath("src/main/resources");
        server.start();
    }
}

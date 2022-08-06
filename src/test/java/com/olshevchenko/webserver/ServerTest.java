package com.olshevchenko.webserver;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    public void testServer() throws IOException {
        Server server = new Server();
        server.setPort(3001);
        server.setWebAppPath("/src/main/resources");
        server.start();
    }

}
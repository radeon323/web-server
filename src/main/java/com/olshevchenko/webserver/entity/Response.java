package com.olshevchenko.webserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.Map;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@Setter
@ToString
public class Response {
    private StatusCode statusCode;
    private BufferedReader content;
    private ImageInputStream image;
    private Map<String, String[]> headers;
}

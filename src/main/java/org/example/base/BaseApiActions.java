package org.example.base;

import lombok.Getter;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class BaseApiActions {
    @Getter
    protected String baseEndPoint;

    @Getter
    protected String authenticationToken;

    public BaseApiActions(String baseEndPoint) {
        this.baseEndPoint = baseEndPoint;
    }

    public BaseApiActions(String baseEndPoint, String authenticationToken) {
        this(baseEndPoint);
        this.authenticationToken = authenticationToken;

    }

    public HttpURLConnection CreateGetConnection(String api) throws IOException, URISyntaxException {
        URI uri = new URIBuilder(this.baseEndPoint).appendPath(api).build();
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connection.setRequestProperty("Authorization", this.authenticationToken);
        connection.setRequestMethod("GET");
        return connection;
    }

    public HttpURLConnection CreatePostConnection(String api) throws IOException, URISyntaxException {
        URI uri = new URIBuilder(this.baseEndPoint).appendPath(api).build();
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connection.setRequestProperty("Authorization", this.authenticationToken);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        return connection;
    }
}

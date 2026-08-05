package com.example.calc;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Client { // everything is pretty obvious here
    public static void main(String[] args) throws Exception{
        HttpClient client = HttpClient.newHttpClient();

        while (true) {
            Scanner sc = new Scanner(System.in);
            sendClient(sc.nextLine(), client);
            Thread.sleep(100);
            acceptClient(client);
        }
    }

    public static void sendClient(String expression, HttpClient client) throws Exception{
        HttpRequest post = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/calc"))
        .header("Content-Type", "text/plain")
        .POST(HttpRequest.BodyPublishers.ofString(expression))
        .build();

        client.send(post, HttpResponse.BodyHandlers.ofString());
    }

    public static void acceptClient(HttpClient client) throws Exception{
        HttpRequest get = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/calc"))
        .header("Content-Type", "text/plain")
        .header("Accept", "text/plain")
        .GET()
        .build();

        System.out.println(client.send(get, HttpResponse.BodyHandlers.ofString()).body());
    }
}

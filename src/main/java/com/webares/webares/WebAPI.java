package com.webares.webares;

import javafx.application.Platform;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebAPI {

    public WebAPI(Controller controller) {
        this.controller = controller;
    }
    private Controller controller;
    private final HttpClient CLIENT = HttpClient.newHttpClient();

    private final Thread GETTER = new Thread(() -> {

        while (true) {
            for (String variable : DATA_Web.VARIABLES) {
                String url = DATA_Web.BASE_URL + variable;

                try {
                    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
                    HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                    DATA_Web.table.put(variable, response.body().strip());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(variable + " " + DATA_Web.table.get(variable));
            }
            System.out.println("==================================================================================================");
            try {
                Platform.runLater(() -> controller.process());
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                System.err.println(e.getMessage());
//            }
        }
    });

    public void webgetter(boolean status) {
        if (status) {
            GETTER.start();
        } else {
            GETTER.interrupt();
            Platform.runLater(() -> controller.process());
        }
    }
}

package com.webares.webares;

import javafx.application.Platform;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class WebAPI {

    public WebAPI(Controller controller) {
        this.controller = controller;
    }

    private Controller controller;

    public void setController2(Controller2 controller2) {
        this.controller2 = controller2;
    }

    private Controller2 controller2;
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
                controller2.process();
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

    public void postVariable(String variable, String value) {
        String url = DATA_Web.BASE_URL + variable + "&value=" + value;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.noBody()) // pas de corps, les données sont dans l'URL
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            Platform.runLater(() -> {
                controller.logsautomate.appendText("\n" + variable + " = " + value);
                String[] lignes = controller.logsautomate.getText().split("\n");
                if (lignes.length > 4) {
                    String nouveauTexte = String.join("\n", Arrays.copyOfRange(lignes, lignes.length - 4, lignes.length));
                    controller.logsautomate.setText(nouveauTexte);
                }

                controller.logsautomate.positionCaret(controller.logsautomate.getLength());
            });

//            System.out.println("POST: " + variable + " = " + value + " | Response: " + response.body());

        } catch (Exception e) {
            System.err.println("POST error for " + variable + ": " + e.getMessage());
        }
    }

    public void webgetter(boolean status) {
        if (status) {
            GETTER.start();
        } else {
            GETTER.interrupt();
            Platform.runLater(() -> controller.process());
        }
    }
}

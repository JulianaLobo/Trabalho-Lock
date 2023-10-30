package com.example.trabalholockotimista.client;

import com.example.trabalholockotimista.TrabalhoLockOtimistaApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientRequests {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Por favor, forneça a URL da API como argumento.");
            return;
        }

        String apiUrl = args[0];

        for (int i = 1; i <= 100; i++) {
            Thread thread = new Thread(new ApiRequestTask(apiUrl, i));
            thread.start();
        }
    }

    static class ApiRequestTask implements Runnable {
        private String apiUrl;
        private int threadNumber;

        public ApiRequestTask(String apiUrl, int threadNumber) {
            this.apiUrl = apiUrl;
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 100; i++) {
                try {
                    URL url = new URL(apiUrl + "/conta/depositar?numeroConta=" + i + "&valor=" + i * 0.8);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");

                    int responseCode = connection.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    System.out.println("Thread " + threadNumber + " - Request " + i + " - Response Code: " + responseCode + " - Response JSON: " + response.toString());

                    connection.disconnect();
                } catch (Exception e) {
                    System.err.println("Thread " + threadNumber + " - Request " + i + " - Error: " + e.getMessage());
                }
            }
        }
    }
}

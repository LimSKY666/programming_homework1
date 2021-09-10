package ru.kpfu.itis.sokolov.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Homework implements HttpClient {

    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        StringBuilder content = new StringBuilder();
        StringBuilder stringGetUrl = new StringBuilder();
        stringGetUrl.append(url).append("?");
        try {
            for (Map.Entry<String, String> paramsEntry : params.entrySet()) {
                stringGetUrl.append(paramsEntry.getKey()).append("=").append(paramsEntry.getValue()).append("&");
            }
            stringGetUrl.deleteCharAt(stringGetUrl.length() - 1);
            URL getUrl = new URL(stringGetUrl.toString());
            HttpURLConnection getConnection = (HttpURLConnection) getUrl.openConnection();
            getConnection.setRequestMethod("GET");

            for (Map.Entry<String, String> headersEntry : headers.entrySet()) {
                getConnection.setRequestProperty(headersEntry.getKey(), headersEntry.getValue());
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(getConnection.getInputStream()))) {
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
            }
            getConnection.disconnect();

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> params) {
        StringBuilder content = new StringBuilder();
        StringBuilder jsonInputString = new StringBuilder("{");
        try {
            URL postUrl = new URL(url);
            HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setDoOutput(true);
            for (Map.Entry<String, String> headersEntry : headers.entrySet()) {
                postConnection.setRequestProperty(headersEntry.getKey(), headersEntry.getValue());
            }

            for (Map.Entry<String, String> paramsEntry : params.entrySet()) {
                jsonInputString.append("\"").append(paramsEntry.getKey()).append("\":\"").append(paramsEntry.getValue()).append("\",");
            }
            jsonInputString.deleteCharAt(jsonInputString.length() - 1).append("}");

            try (OutputStream outputStream = postConnection.getOutputStream()) {
                byte[] input = jsonInputString.toString().getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(postConnection.getInputStream()))) {
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
            }
            postConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}

package com.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;


public class HttpServer {
    private static final int PORT = 8080;
    private static final String STATIC_DIR = "static";

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started at http://localhost:" + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                    String requestStartLine = in.readLine();
                    System.out.println("Получили стартовую строку запроса: " + requestStartLine);


                    if (isValidGetRequest(requestStartLine)) {
                        String fileName = extractFileNameFromRequestStartLine(requestStartLine);
                        System.out.println("Извлеченное имя файла: " + fileName);

                        String filePath = STATIC_DIR + "/" + fileName;
                        File file = new File(filePath);

                        if (file.exists() && file.isFile()) {
                            sendHttpResponseWithFile(out, file);
                        } else {
                            sendHttp404Response(out);
                        }
                    } else {
                        sendHttp400Response(out);
                    }

                } catch (Exception e) {
                    System.err.println("Ошибка при обработке клиентского запроса: " + e.getMessage());
                }
            }
        }
    }


    private static boolean isValidGetRequest(String requestStartLine) {
        return requestStartLine != null && requestStartLine.startsWith("GET ");
    }

    private static String extractFileNameFromRequestStartLine(String requestStartLine) {
        String[] parts = requestStartLine.split(" ");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Некорректная стартовая строка запроса");
        }

        String path = parts[1];

        if ("/".equals(path)) {
            return "index.html";
        }

        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("Путь должен начинаться с /");
        }

        return path.substring(1);
    }

    private static void sendHttpResponseWithFile(BufferedWriter out, File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()), "UTF-8");
        String contentType = determineContentType(file.getName());

        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Content-Type: " + contentType + "\r\n");
        out.write("Content-Length: " + content.length() + "\r\n");
        out.write("\r\n");
        out.write(content);
        out.flush();

        System.out.println("Отправлен HTTP ответ с файлом: " + file.getName());
    }

    private static void sendHttp404Response(BufferedWriter out) throws IOException {
        String response = "<h1>404 Not Found</h1><p>Запрашиваемый файл не найден на сервере.</p>";

        out.write("HTTP/1.1 404 Not Found\r\n");
        out.write("Content-Type: text/html; charset=UTF-8\r\n");
        out.write("Content-Length: " + response.length() + "\r\n");
        out.write("\r\n");
        out.write(response);
        out.flush();

        System.out.println("Отправлен HTTP ответ 404");
    }

    private static void sendHttp400Response(BufferedWriter out) throws IOException {
        String response = "<h1>400 Bad Request</h1><p>Некорректный запрос.</p>";

        out.write("HTTP/1.1 400 Bad Request\r\n");
        out.write("Content-Type: text/html; charset=UTF-8\r\n");
        out.write("Content-Length: " + response.length() + "\r\n");
        out.write("\r\n");
        out.write(response);
        out.flush();

        System.out.println("Отправлен HTTP ответ 400");
    }

    private static String determineContentType(String fileName) {
        if (fileName.endsWith(".html")) {
            return "text/html; charset=UTF-8";
        } else if (fileName.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        } else if (fileName.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        } else {
            return "text/plain; charset=UTF-8";
        }
    }
}
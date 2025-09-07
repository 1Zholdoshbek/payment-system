package com.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;


public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started at http://localhost:8080");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new
                    OutputStreamWriter(clientSocket.getOutputStream()));


            String firstLine = in.readLine();
            System.out.println("Получили запрос: " + firstLine);

            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {

            }

            String fileName = getFileNameFromRequest(firstLine);
            System.out.println("Ищем файл: " + fileName);


            String filePath = "static/" + fileName;
            File file = new File(filePath);

            if (file.exists() && file.isFile()) {
                sendFile(out, file);
            } else {
                send404(out);
            }

            clientSocket.close();
        }
    }

    private static String getFileNameFromRequest(String requestLine) {
        if (requestLine == null || !requestLine.startsWith("GET ")) {
            return "index.html";
        }

        String[] parts = requestLine.split(" ");
        if (parts.length < 2) {
            return "index.html";
        }

        String path = parts[1];

        if (path.equals("/")) {
            return "index.html";
        }


        if (path.startsWith("/")) {
            return path.substring(1);
        }

        return path;
    }

    private static void sendFile(BufferedWriter out, File file) throws IOException {

        String content = new String(Files.readAllBytes(file.toPath()), "UTF-8");


        String contentType = getContentType(file.getName());


        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Content-Type: " + contentType + "\r\n");
        out.write("Content-Length: " + content.length() + "\r\n");
        out.write("\r\n");


        out.write(content);
        out.flush();

        System.out.println("Отправили файл: " + file.getName());
    }


    private static void send404(BufferedWriter out) throws IOException {
        String response = "<h1>404 - Файл не найден</h1>";

        out.write("HTTP/1.1 404 Not Found\r\n");
        out.write("Content-Type: text/html; charset=UTF-8\r\n");
        out.write("Content-Length: " + response.length() + "\r\n");
        out.write("\r\n");
        out.write(response);
        out.flush();

        System.out.println("Отправили 404");
    }


    private static String getContentType(String fileName) {
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
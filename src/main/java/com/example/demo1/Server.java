package com.example.demo1;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("iniciado el servidor...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Nuevo usuario conectado: " + socket.getInetAddress().getHostName());

            Thread t = new Thread(new ClientHandler(socket));
            t.start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            while (true) {
                String input = in.readLine();
                if (input == null) break;
                System.out.println("Cliente dice: " + input);
                out.println("Servidor dice: " + input);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            try { socket.close(); } catch (Exception e) {}
            System.out.println("Cliente desconectado: " + socket.getInetAddress().getHostName());
        }
    }
}

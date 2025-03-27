package com.example.kidsapplication;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 6666;
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        System.out.println("Chat Server is running...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = dis.readUTF()) != null) {
                    System.out.println("Received: " + message);
                    broadcastMessage(message, this);
                }
            } catch (IOException e) {
                System.out.println("Client disconnected.");
            } finally {
                closeConnection();
            }
        }

        private void broadcastMessage(String message, ClientHandler sender) throws IOException {
            synchronized (clients) {
                for (ClientHandler client : clients) {
                    if (client != sender) { // Don't send the message back to the sender
                        client.dos.writeUTF(message);
                        client.dos.flush();
                    }
                }
            }
        }

        private void closeConnection() {
            try {
                clients.remove(this);
                if (dis != null) dis.close();
                if (dos != null) dos.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}


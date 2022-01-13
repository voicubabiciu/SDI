package Proiect.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
        try {
            TCPServer server = new TCPServer(8080);
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package Proiect.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {
    final int port;
    TCPClientManager clientManager;
    ServerSocket socket;

    public TCPServer(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        socket = new ServerSocket(this.port);
        clientManager = new TCPClientManager(socket);
        clientManager.start();
    }

    public void stopServer() throws IOException {
        socket.close();
    }

}

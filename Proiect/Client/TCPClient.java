package Proiect.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    final String address;
    final int port;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    TCPReader reader;
    TCPWriter writer;

    public TCPClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void startClient() throws UnknownHostException, IOException {
        socket = new Socket(address, port);

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void stopClient() throws UnknownHostException, IOException {
        stopListeningFromDataStream();
        stopWriter();
        socket.close();
        dataInputStream.close();
        dataOutputStream.close();
        socket = null;
        dataInputStream = null;
        dataOutputStream = null;
    }

    public void startWriter() throws UnknownHostException, IOException {
        writer = new TCPWriter(dataOutputStream);
        writer.start();
    }

    public void sendMessage(String payload) throws IOException {
        dataOutputStream.writeUTF(payload);
    }

    public void listensToDataStream(ITCPListeners listener) {
        if (reader == null) {
            reader = new TCPReader(dataInputStream, listener);
            reader.setDaemon(true);
            reader.start();
        }
    }

    public void stopListeningFromDataStream() {
        if (reader != null) {
            reader.stopReader();
            reader = null;
        }

    }

    public void stopWriter() {
        if (writer != null) {
            writer.stopWriter();
            writer = null;
        }
    }
}

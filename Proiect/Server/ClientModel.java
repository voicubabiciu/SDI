package Proiect.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientModel {

    String id;
    String username;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket clientSocket;
    TCPReader reader;
    TCPWriter writer;

    ClientModel(Socket clientSocket, DataInputStream dataInputStream,
            DataOutputStream dataOutputStream) {
        this.clientSocket = clientSocket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    public void startWriter() throws UnknownHostException, IOException {
        writer = new TCPWriter(dataOutputStream);
        writer.start();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void sendMessage(String payload) throws IOException {
        dataOutputStream.writeUTF(payload);
    }

    public void listensToDataStream(ITCPListeners listener) {
        if (reader == null) {
            reader = new TCPReader(this, dataInputStream, listener);
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

    public void write(String payload) throws IOException {
        dataOutputStream.writeUTF(payload);
    }
}

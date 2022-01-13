package lab01;

import java.net.*;
import java.io.*;

class TCPEchoReader extends Thread {
    public TCPEchoReader(DataInputStream input) {
        dataInputStream = input;
        active = true;
    }

    public void run() {
        while (active) {
            try {
                String message = dataInputStream.readUTF();
                System.out.println("Received from server: " + message);
            } catch (IOException e) {
                System.out.println(e);
                active = false;
            }
        }
    }

    public boolean active;
    public DataInputStream dataInputStream;
}

public class TCPEchoClient {
    public static void main(String[] args) {
        String address = "localhost";
        int port = 8910;
        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        BufferedReader keyboardReader = null;
        // Connect to the server...
        try {
            socket = new Socket(address, port);
            // Obtain the streams...
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            keyboardReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Problems initialising: " + e);
            System.exit(1);
        }
        try {
            // Start the listening thread...
            TCPEchoReader reader = new TCPEchoReader(dataInputStream);
            reader.setDaemon(true);
            reader.start();
            String input;
            while (true) {
                // read data in from the keyboard
                input = keyboardReader.readLine();
                // send data to server
                dataOutputStream.writeUTF(input);
            }
        } catch (IOException e) {
        }
        try {
            socket.close();
        } catch (IOException e) {
        }
    }
}
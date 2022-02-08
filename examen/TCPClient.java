package examen;

import java.net.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class TCPClient {
    public static void main(String[] args) throws InterruptedException {
        String address = "localhost";
        int port = 56879;
        Socket socket = null;
        PrintWriter dataOutputStream = null;
        BufferedReader keyboardReader = null;
        // Connect to the server...
        try {
            socket = new Socket(address, port);
            // Obtain the streams...
            dataOutputStream = new PrintWriter(socket.getOutputStream(), true);
            keyboardReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Problems initialising: " + e);
            System.exit(1);
        }
        try {
            // Start the listening thread...
            TCPEchoReader reader = new TCPEchoReader(socket.getInputStream());
            reader.setDaemon(true);
            reader.start();
            String input;
            while (true) {
                System.out.print("Type a command: ");

                // read data in from the keyboard
                input = keyboardReader.readLine();
                System.out.println("Before sending " + input);
                // send data to server
                dataOutputStream.println(input);
                dataOutputStream.write(input.toCharArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TCPEchoReader extends Thread {
    public boolean active;
    public InputStream dataInputStream;

    public TCPEchoReader(InputStream input) {
        dataInputStream = input;
        active = true;
    }

    public void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(dataInputStream));
        while (active) {
            try {
                System.out.println("Waiting for response: ");

                String message = input.readLine();
                System.out.println("Received from server: " + message);
            } catch (IOException e) {
                System.out.println(e);
                active = false;
            }
        }
    }

}

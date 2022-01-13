package lab01;

import java.io.*;
import java.net.*;

public class TCPEchoServer {
    public static void main(String args[]) {
        int port = 8910;
        ServerSocket serverSocket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            // open a server socket
            serverSocket = new ServerSocket(port);
            System.out.println("Server created on port " + port);
            System.out.println("Awaiting client connection...");
            // await for a client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from " + clientSocket.getInetAddress());
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Problems initializing server: " + e);
            System.exit(1);
        }
        // communicate with the client
        try {
            dataOutputStream.writeUTF("Welcome to the TCP Echo Server!");
            String input;
            while (true) {
                // read data in from client
                input = dataInputStream.readUTF();
                if (input.contains(" ")) {
                    String[] numbers = input.split(" ");
                    String response = "";
                    for (String number : numbers) {
                        try {
                            if (!number.equals("1") && !number.equals("0"))
                                throw new Exception();
                            Boolean isOccupied = Integer.parseInt(number) == 1;
                            if (isOccupied) {
                                response += " OCCUPIED";
                                System.out.println("OCCUPIED");
                            } else {
                                response += " FREE";
                                System.out.println("FREE");
                            }
                        } catch (Exception ex) {
                            response += " NAN";
                            System.out.println("Entry should be a number");
                        }

                    }
                    dataOutputStream.writeUTF(response.substring(1, response.length()));
                } else {
                    try {
                        if (!input.equals("1") && !input.equals("0"))
                            throw new Exception();

                        Boolean isOccupied = Integer.parseInt(input) == 1;
                        if (isOccupied) {
                            System.out.println("OCCUPIED");
                            dataOutputStream.writeUTF("OCCUPIED");

                        } else {
                            System.out.println("FREE");
                            dataOutputStream.writeUTF("FREE");

                        }
                    } catch (Exception ex) {
                        System.out.println("Entry should be a number");
                        dataOutputStream.writeUTF("Entry should be a number");

                    }
                }
                // write data back to client
            }
        } catch (IOException e) {
            System.out.println("Client disconnected from server");
        }
        try {
            serverSocket.close();
        } catch (Exception e) {
        }
    }
}
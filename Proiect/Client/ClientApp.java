package Proiect.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import Proiect.Client.Auth.Session;
import Proiect.Client.Commands.CommandsStatuses;

public class ClientApp {
    public static void main(String[] args) {
        try {
            final TCPClient client = new TCPClient("localhost", 8080);
            client.startClient();
            client.startWriter();
            client.listensToDataStream(new ITCPListeners() {
                @Override
                public void onAuthReceived(CommandsStatuses status, String payload) {
                    switch (status) {
                        case ERROR:
                            System.out.println(payload);
                            break;
                        case SUCCESS:
                            Session.getInstance().setToken(payload);
                            System.out.println("Hello again");
                            break;
                        default:
                            System.out.println("Unknown error");
                            break;
                    }

                }

                @Override
                public void onRegisterReceived(CommandsStatuses status, String payload) {
                    switch (status) {
                        case ERROR:
                            System.err.println(payload);
                            break;
                        case SUCCESS:
                            Session.getInstance().setToken(payload);
                            System.out.println("Hello");
                            break;
                        default:
                            System.err.println("Unknown error");
                            break;
                    }
                }

                @Override
                public void onUsersReceived(CommandsStatuses status, String payload) {
                    switch (status) {
                        case ERROR:
                            System.err.println(payload);
                            break;
                        case SUCCESS:
                            System.out.println(payload);
                            break;
                        default:
                            System.err.println("Unknown error");
                            break;
                    }

                }

                @Override
                public void onMessagePayloadReceived(String payload) {
                    System.out.println(payload);

                }
            });
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

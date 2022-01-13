package Proiect.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.lang.model.type.NullType;

import org.omg.CORBA.Any;

import Proiect.Server.Commands.AuthCommand;
import Proiect.Server.Commands.Command;
import Proiect.Server.Commands.CommandIdentificator;
import Proiect.Server.Commands.CommandsFactory;
import Proiect.Server.Commands.SupportedCommands;

class TCPReader extends Thread {

    final ITCPListeners listener;
    public boolean active;
    public DataInputStream dataInputStream;
    public ClientModel client;

    public TCPReader(ClientModel client, DataInputStream input, ITCPListeners listener) {
        this.client = client;
        this.listener = listener;
        dataInputStream = input;
        active = true;
    }

    public void run() {
        while (active) {
            try {
                final String payload = dataInputStream.readUTF();
                if (payload != null) {
                    final SupportedCommands commandName = CommandIdentificator.identify(payload);
                    final Command command = CommandsFactory.getCommand(payload, commandName);
                    final String[] parts = payload.split(" ");
                    switch (commandName) {
                        case AUTH:
                            client.setUsername(parts[2]);
                            listener.onAuthReceived(client, command);
                            break;
                        case REGISTER:
                            client.setUsername(parts[2]);
                            listener.onRegisterReceived(client, command);
                            break;
                        case USERS:
                            String[] sessionParts = payload.split(":");

                            if (client.getId() != null && sessionParts.length == 2 &&
                                    sessionParts[1] != null &&
                                    client.getId().equals(sessionParts[1])) {
                                listener.onUsersReceived(client, command);
                            } else {
                                client.sendMessage("INFO:Invalid Token");
                            }

                            break;
                        case ENABLE_SUBSCRIPTION:
                            sessionParts = payload.split(":");

                            if (client.getId() != null && sessionParts.length == 2 &&
                                    sessionParts[1] != null &&
                                    client.getId().equals(sessionParts[1])) {
                                listener.onSubscriptionEnabled(client, command);

                            } else {
                                client.sendMessage("INFO:Invalid Token");
                            }
                            break;
                        case DISABLE_SUBSCRIPTION:
                            sessionParts = payload.split(":");

                            if (client.getId() != null && sessionParts.length == 2 &&
                                    sessionParts[1] != null &&
                                    client.getId().equals(sessionParts[1])) {
                                listener.onSubscriptionDisabled(client, command);

                            } else {
                                client.sendMessage("INFO:Invalid Token");
                            }
                            break;
                        case POST_MESSAGE:
                            sessionParts = payload.split(":");
                            if (client.getId() != null && sessionParts.length == 2 &&
                                    sessionParts[1] != null &&
                                    client.getId().equals(sessionParts[1])) {
                                String messages = sessionParts[0].substring(4, sessionParts[0].length());
                                String message = String.join(" ", Arrays.asList(messages));
                                listener.onPostMessage(client, command, message);

                            } else {
                                client.sendMessage("INFO:Invalid Token");
                            }
                            break;
                        case UNSUPPORTED_COMMAND:
                        default:
                            System.out.println("Not Supported");
                            // throw new UnsupportedOperationException();
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
                active = false;
            }
        }
    }

    public void stopReader() {
        active = false;
        super.interrupt();
    }
}
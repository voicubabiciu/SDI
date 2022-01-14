package Proiect.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.lang.model.type.NullType;

import Proiect.Client.Commands.CommandsStatuses;
import Proiect.Server.Commands.Command;
import Proiect.Server.IOOperations.Logger;

public class TCPClientManager extends Thread implements ITCPListeners {

    final ServerSocket serverSocket;
    boolean active = false;
    HashMap<String, ClientModel> activeClients = new HashMap<String, ClientModel>();
    HashMap<String, ClientModel> subscribedClients = new HashMap<String, ClientModel>();

    public TCPClientManager(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        active = true;
    }

    @Override
    public void run() {
        while (active) {
            try {
                Socket clientSocket = serverSocket.accept();
                final ClientModel client = new ClientModel(clientSocket,
                        new DataInputStream(clientSocket.getInputStream()),
                        new DataOutputStream(clientSocket.getOutputStream()));
                client.listensToDataStream(this);
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.writeUTF("INFO:Welcome to the News Server!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopClientManager() {
        active = false;
    }

    @Override
    public void onPayloadReceived(ClientModel client, String payload) {

    }

    @Override
    public void onAuthReceived(ClientModel client, Command<?, NullType> command) {
        try {
            String response = command.executeCommand(null).toString();
            if (CommandsStatuses.valueOf(response.split(":")[1]) == CommandsStatuses.SUCCESS) {
                String clientId = HashCreator.createHash(client.getUsername());
                client.setId(clientId);
                activeClients.put(clientId, client);
            }
            client.write(response);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRegisterReceived(ClientModel client, Command<?, NullType> command) {
        try {
            String response = command.executeCommand(null).toString();
            if (CommandsStatuses.valueOf(response.split(":")[1]) == CommandsStatuses.SUCCESS) {
                String clientId = HashCreator.createHash(client.getUsername());
                client.setId(clientId);
                activeClients.put(clientId, client);
            }
            client.write(response);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUsersReceived(ClientModel client, Command<?, ArrayList<ClientModel>> command) {
        try {

            client.write(command.executeCommand(new ArrayList<ClientModel>(activeClients.values())).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSubscriptionEnabled(ClientModel client, Command<?, NullType> command) {
        try {
            subscribedClients.put(client.id, client);
            client.write(command.executeCommand(null).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSubscriptionDisabled(ClientModel client, Command<?, NullType> command) {
        try {
            subscribedClients.remove(client.id);
            client.write(command.executeCommand(null).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPostMessage(ClientModel client, Command<?, NullType> command, String message) {
        try {
            for (ClientModel sub : subscribedClients.values()) {
                if (!sub.id.equals(client.id)) {
                    final String payload = "INFO:" + "[" + LocalDate.now() + ","
                            + LocalTime.now().toString().replace(":", "|") + "]"
                            + client.username + " says>> " + message;
                    Logger.getInstance().Log(payload);
                    sub.write(payload);
                }
            }
            client.write(command.executeCommand(null).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
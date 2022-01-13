package Proiect.Server;

import java.util.ArrayList;

import javax.lang.model.type.NullType;

import Proiect.Server.Commands.Command;

public interface ITCPListeners {
    void onPayloadReceived(ClientModel client, String payload);

    void onAuthReceived(ClientModel client, Command<?, NullType> command);

    void onRegisterReceived(ClientModel client, Command<?, NullType> command);

    void onUsersReceived(ClientModel client, Command<?, ArrayList<ClientModel>> command);

    void onSubscriptionDisabled(ClientModel client, Command<?, NullType> command);

    void onSubscriptionEnabled(ClientModel client, Command<?, NullType> command);

    void onPostMessage(ClientModel client, Command<?, NullType> command, String message);
}

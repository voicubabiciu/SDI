package Proiect.Client;

import Proiect.Client.Commands.CommandsStatuses;

public interface ITCPListeners {
    void onMessagePayloadReceived(String payload);

    void onAuthReceived(CommandsStatuses status, String payload);

    void onRegisterReceived(CommandsStatuses status, String payload);

    void onUsersReceived(CommandsStatuses status, String payload);

}

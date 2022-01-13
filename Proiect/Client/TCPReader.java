package Proiect.Client;

import java.io.DataInputStream;
import java.io.IOException;

import Proiect.Client.Auth.Session;
import Proiect.Client.Commands.CommandIdentificator;
import Proiect.Client.Commands.CommandsStatuses;
import Proiect.Client.Commands.SupportedCommands;

class TCPReader extends Thread {

    final ITCPListeners listener;
    public boolean active;
    public DataInputStream dataInputStream;

    public TCPReader(DataInputStream input, ITCPListeners listener) {
        this.listener = listener;
        dataInputStream = input;
        active = true;
    }

    public void run() {
        while (active) {
            try {
                final String payload = dataInputStream.readUTF();
                final SupportedCommands command = CommandIdentificator.identify(payload);
                final String[] parts = payload.split(":");
                switch (command) {
                    case AUTH:
                        listener.onAuthReceived(CommandsStatuses.valueOf(parts[1]), parts[2]);
                        break;
                    case REGISTER:
                        listener.onRegisterReceived(CommandsStatuses.valueOf(parts[1]), parts[2]);
                        break;
                    case INFO:
                        listener.onMessagePayloadReceived(parts[1]);
                        break;
                    case USERS:
                        listener.onUsersReceived(CommandsStatuses.valueOf(parts[1]), parts[2]);
                        break;
                    case UNSUPPORTED_COMMAND:
                    default:
                        listener.onMessagePayloadReceived("UNSUPPORTED_COMMAND");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                active = false;
            }
        }
    }

    public void stopReader() {
        active = false;
        super.interrupt();
    }
}
package Proiect.Client.Commands;

public class CommandIdentificator {
    public static SupportedCommands identify(String command) {
        String[] parts = command.split(":");
        if (parts[0].equals("AUTH")) {
            return SupportedCommands.AUTH;
        } else if (parts[0].equals("REGISTER")) {
            return SupportedCommands.REGISTER;
        } else if (parts[0].equals("LIST_USERS")) {
            return SupportedCommands.USERS;
        } else if (parts[0].equals("INFO")) {
            return SupportedCommands.INFO;
        }
        return SupportedCommands.UNSUPPORTED_COMMAND;
    }
}

package Proiect.Server.Commands;

import javax.lang.model.type.NullType;

public class UnsubscribeCommand implements Command<String, NullType> {
    @Override
    public String executeCommand(NullType args) {
        return "INFO:Successfully unsubscribed";
    }

    public static Boolean identifier(String command) {
        final String[] parts = command.split(" ");
        return parts.length == 2 &&
                parts[0].toLowerCase().equals("subscription") &&
                (parts[1].toLowerCase().equals("disable") || parts[1].toLowerCase().equals("-d"));
    }

}

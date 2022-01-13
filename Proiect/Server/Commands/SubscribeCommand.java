package Proiect.Server.Commands;

import javax.lang.model.type.NullType;

public class SubscribeCommand implements Command<String, NullType> {

    @Override
    public String executeCommand(NullType enabled) {
        return "INFO:Successfully subscribed";
    }

    public static Boolean identifier(String command) {
        final String[] parts = command.split(" ");
        return parts.length == 2 &&
                parts[0].toLowerCase().equals("subscription") &&
                (parts[1].toLowerCase().equals("enable") || parts[1].toLowerCase().equals("-e"));
    }
}

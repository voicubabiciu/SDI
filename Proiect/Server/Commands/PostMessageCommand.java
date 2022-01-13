package Proiect.Server.Commands;

import javax.lang.model.type.NullType;

public class PostMessageCommand implements Command<String, NullType> {

    @Override
    public String executeCommand(NullType args) {
        return "INFO:Message sent";
    }

    public static Boolean identifier(String command) {
        final String[] parts = command.split(" ");
        return parts.length > 1 && parts[0].toLowerCase().equals("post");
    }

}

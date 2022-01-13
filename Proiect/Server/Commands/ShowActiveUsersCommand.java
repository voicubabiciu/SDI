package Proiect.Server.Commands;

import java.util.ArrayList;

import Proiect.Server.ClientModel;

public class ShowActiveUsersCommand implements Command<String, ArrayList<ClientModel>> {
    private final String PREFIX = "LIST_USERS:";

    @Override
    public String executeCommand(ArrayList<ClientModel> args) {
        String output = "";
        for (int i = 0; i < args.size(); i++) {
            output += (i + 1) + ") " + args.get(i).getUsername() + "\n";
        }
        return PREFIX + CommandsStatuses.SUCCESS.toString() + ":" + output;
    }

    public static Boolean identifier(String command) {
        final String[] parts = command.split(" ");
        return parts.length == 3 &&
                parts[0].toLowerCase().equals("users") &&
                (parts[1].toLowerCase().equals("list") || parts[1].toLowerCase().equals("-l")) &&
                (parts[2].toLowerCase().equals("all") || parts[2].toLowerCase().equals("-a"));
    }
}

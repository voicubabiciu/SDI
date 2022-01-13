package Proiect.Server.Commands;

public class CommandIdentificator {
    public static SupportedCommands identify(String command) {
        System.out.println(command);
        if (AuthCommand.identifier(command)) {
            return SupportedCommands.AUTH;
        } else if (RegisterCommand.identifier(command)) {
            return SupportedCommands.REGISTER;
        } else if (ShowActiveUsersCommand.identifier(command.split(":")[0])) {
            return SupportedCommands.USERS;
        } else if (SubscribeCommand.identifier(command.split(":")[0])) {
            return SupportedCommands.ENABLE_SUBSCRIPTION;
        } else if (UnsubscribeCommand.identifier(command.split(":")[0])) {
            return SupportedCommands.DISABLE_SUBSCRIPTION;
        } else if (PostMessageCommand.identifier(command.split(":")[0])) {
            return SupportedCommands.POST_MESSAGE;
        }
        return SupportedCommands.UNSUPPORTED_COMMAND;
    }
}

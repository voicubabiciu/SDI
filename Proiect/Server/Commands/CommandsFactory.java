package Proiect.Server.Commands;

public class CommandsFactory {
    public static Command<?, ?> getCommand(String command, SupportedCommands commandName) {
        final String[] parts = command.split(" ");
        switch (commandName) {
            case AUTH:
                return new AuthCommand(parts[2], parts[4]);
            case REGISTER:
                return new RegisterCommand(parts[2], parts[4]);
            case USERS:
                return new ShowActiveUsersCommand();
            case ENABLE_SUBSCRIPTION:
                return new SubscribeCommand();
            case DISABLE_SUBSCRIPTION:
                return new UnsubscribeCommand();
            case POST_MESSAGE:
                return new PostMessageCommand();
            case UNSUPPORTED_COMMAND:
            default:
                System.out.println("Unknown command");
                return null;
        }
    }
}

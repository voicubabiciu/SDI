package Proiect.Server.Commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.lang.model.type.NullType;

import Proiect.Server.HashCreator;
import Proiect.Server.IOOperations.UserStorage;

public class RegisterCommand implements Command<String, NullType> {
    final String username;
    final String password;
    private final String PREFIX = "REGISTER:";

    RegisterCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String executeCommand(NullType args) {
        try {
            final UserStorage storage = UserStorage.getInstance();
            final String id = HashCreator.createHash(username);
            if (storage.userExist(id)) {
                return PREFIX + CommandsStatuses.ERROR.toString() + ":" + "Username already exist";
            }
            UserStorage.getInstance().registerUser(id + ":" + username + ":" + HashCreator.passwordHash(password, id));
            return PREFIX + CommandsStatuses.SUCCESS.toString() + ":" + id;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return PREFIX + CommandsStatuses.ERROR.toString() + ":" + "Unknown error";
    }

    public static Boolean identifier(String command) {
        final String[] parts = command.split(" ");
        return parts.length == 5 &&
                parts[0].toLowerCase().equals("register") &&
                (parts[1].toLowerCase().equals("user") || parts[1].toLowerCase().equals("-u")) &&
                (parts[3].toLowerCase().equals("password") || parts[3].toLowerCase().equals("-p"));
    }
}

package Proiect.Server.IOOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class UserStorage {
    private static UserStorage instance;

    private UserStorage() {
    }

    synchronized public static UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();

        }
        return instance;
    }

    public void registerUser(String message) throws IOException {
        System.out.println("save user");
        FileWriter writer = new FileWriter("./Proiect/Server/Logs/users.txt", true);
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println(message);
        printWriter.close();
        writer.close();
    }

    public Boolean userExist(String hash) throws IOException {
        File myObj = new File("./Proiect/Server/Logs/users.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String[] data = myReader.nextLine().split(":");
            if (data[0].equals(hash)) {
                myReader.close();
                return true;
            }
        }
        myReader.close();
        return false;
    }

    public Boolean authUser(String id, String username, String passwordHash) throws FileNotFoundException {
        File myObj = new File("./Proiect/Server/Logs/users.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String[] data = myReader.nextLine().split(":");
            if (data[0].equals(id) && data[1].equals(username) && data[2].equals(passwordHash)) {
                myReader.close();
                return true;
            }
        }
        myReader.close();
        return false;

    }
}

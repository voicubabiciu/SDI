package Proiect.Server.IOOperations;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private static Logger instance;

    private Logger() {
    }

    synchronized public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void Log(String message) throws IOException {
        System.out.println("save user");
        FileWriter writer = new FileWriter("./Proiect/Server/Logs/history.txt", true);
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println(message);
        printWriter.close();
        writer.close();
    }

}

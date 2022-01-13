package Proiect.Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TCPWriter extends Thread {
    final DataOutputStream dataOutputStream;
    Boolean active = false;

    public TCPWriter(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        active = true;
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
        while (active) {
            String input;
            try {
                input = keyboardReader.readLine();
                dataOutputStream.writeUTF(input);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopWriter() {
        active = false;
        super.interrupt();
    }
}

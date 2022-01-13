package lab02;

import java.net.SocketException;
import java.net.UnknownHostException;

public class App {
    public static void main(String[] args) {

        try {
            UDPServer server = new UDPServer(4445);
            server.start();

            UDPClient client = new UDPClient("localhost", 4445, new IUDPResponse() {
                @Override
                public void onMessage(String payload) {
                    System.out.println(payload);
                }
            });
            client.startPing();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}

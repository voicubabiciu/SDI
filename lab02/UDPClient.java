package lab02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPClient extends Thread {
    private static final int AVERAGE_DELAY = 500;
    private DatagramSocket socket;
    private InetAddress address;
    private final IUDPResponse onMessageReceived;
    private final int port;
    private final String serverAdsress;
    private boolean running;
    private int timeoutCount = 0;

    public UDPClient(String serverAddress, int port, IUDPResponse onMessageReceived)
            throws SocketException, UnknownHostException {
        this.serverAdsress = serverAddress;
        socket = new DatagramSocket();
        this.onMessageReceived = onMessageReceived;
        address = InetAddress.getByName(this.serverAdsress);
        this.port = port;
    }

    @Override
    public void run() {
        while (running) {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            try {
                ping();
                socket.receive(packet);
                socket.setSoTimeout(1000);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                onMessageReceived.onMessage(received);
                timeoutCount = 0;
                Thread.sleep(AVERAGE_DELAY);
            } catch (SocketTimeoutException e) {
                if (timeoutCount++ == 5) {
                    System.out.println("Server did not responds 5 times in a row. Try again.");
                    socket.close();
                    running = false;
                } else {
                    System.out.println("Server did not responds. Try again.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startPing() {
        running = true;
        super.start();
        System.out.println("Start pinging");
    }

    public void ping() throws IOException {

        byte[] buf = "ping".getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
    }

    public void close() {
        socket.close();
    }
}
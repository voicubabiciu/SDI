package lab02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class UDPServer extends Thread {

    private static final double LOSS_RATE = 0.3;
    private static final int AVERAGE_DELAY = 500;
    private DatagramSocket socket;
    private boolean running;
    int port;

    public UDPServer(int port) throws SocketException {
        this.port = port;
        socket = new DatagramSocket(this.port);
    }

    @Override
    public void run() {
        running = true;
        Random random = new Random();
        while (running) {
            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
            try {
                socket.receive(request);
                if (random.nextDouble() < LOSS_RATE) {
                    continue;
                }
                Thread.sleep(AVERAGE_DELAY);
                InetAddress clientHost = request.getAddress();
                int clientPort = request.getPort();
                byte[] buf = ("Reply from " + request.getAddress()).getBytes();
                DatagramPacket reply = new DatagramPacket(buf, buf.length, clientHost, clientPort);
                socket.send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public void stopServer() {
        running = false;
        super.interrupt();
    }
}
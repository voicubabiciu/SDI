package examen;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.*;
import java.time.LocalTime;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class Main {

	public static void main(String[] args) {

		// using serversocket as argument to automatically close the socket
		// the port number is unique for each server

		// list to add all the clients thread
		ArrayList<ServerThread> threadList = new ArrayList<>();

		try (ServerSocket serversocket = new ServerSocket(56879)) {

			System.out.println("Server open the port: 56879");

			while (true) {
				Socket connectedSocket = serversocket.accept();
				InetSocketAddress socketAddress = (InetSocketAddress) connectedSocket.getRemoteSocketAddress();

				String clientIpAddress = socketAddress.getAddress().getHostAddress();

				ServerThread serverThread = new ServerThread(connectedSocket, threadList);

				// starting the thread
				threadList.add(serverThread);
				LocalTime myTime = LocalTime.now();

				System.out.println("#" + myTime + "\tnew connection (" + threadList.size() + ")\t" + clientIpAddress);
				serverThread.start();

				// get all the list of currently running thread

			}
		} catch (Exception e) {
			System.out.println("Error occured in main:" + e.getStackTrace());
		}
	}
}
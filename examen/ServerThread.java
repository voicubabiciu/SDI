package examen;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.*;
import java.time.LocalTime;
import java.util.Random;

public class ServerThread extends Thread {

    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    static int idthread = 0;
    private int id = -1;
    String clientname = "n0b0dy";

    private String subj[] = {
            "nimic",
            
            "care este ideea algoritmului Cristian?",
            // este folosit in sincronizarea proceselor clientilor cu timpul
            // server-ului. Un client cere server-ului timpul curent iar acesta va
            // va return o valoare reprezentand timpul server-ului.
            "care este ideea algoritmului Berkeley?",
            // sincronizarea ceasurilor fizice intre nodurile unui 
            // sistem distribuit
            // Master-ul va trimite un semnal ping catre restul nod-urilor 
            // iar acestia vor raspunde cu ceasul lor intern curent
            "Intr-o retea P2P (protocol Chord) cu maxim 16 noduri avem \n" +
                    "active 5 noduri avand cheile 0,2,5,6,11. Unde se va inregistra\n" +
                    "cheia âExamenâ daca hash-ul acesteia este 7.",

            "ce afirma conjectura Brewer?",
            // Deși este de dorit să avem Consistency, 
            // High-Availability și Partition-tolerance 
            // în fiecare sistem, din păcate niciun sistem 
            // nu le poate realiza pe toate trei în același timp.
            "comform CAP ce nu poate asigura MongoDB?",

            "ce este un timestamp? indicati valoarea curenta",
            // Pentru a ordona evenimentele in cadrul unui sistem distribuit, acestora li se
            // asociaza numere intregi numite timestamp"
            "care este ideea algoritmului Lamport?",
            // ordonarea proceselor atribuind ficaruia o valoare intreaga, initializata cu valoarea 0
            // reprezentand ceasul logic intern
            "ce afirma teorema CAP?",
            // Cele trei proprietăți Consistency - Availability - Partition tolerance nu
            // pot fi îndeplinite simultan.
            "ce este marshalling?"
            // Reprezinta acțiunea de a lua o colecție de elemente de date
            // (în funcție de platformă) și asamblarea acestora în
            // reprezentare externă a datelor (independentă de platformă).
    };

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
        idthread++;
        id = idthread;
    }

    @Override
    public void run() {
        Random rand = new Random();

        try {

            // Reading the input from Client
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // returning the output to the client: true statement is to flush the buffer
            // otherwise
            // we have to do it manually
            output = new PrintWriter(socket.getOutputStream(), true);

            // inifite loop for server
            while (true) {
                String cmd = input.readLine();

                if (cmd == null)
                    continue;
                System.out.println("received[" + id + "]: " + cmd);

                if (this.clientname.equals("n0b0dy")) {
                    // presupun ca se inregistreaza
                    output.println("Hello, " + cmd);
                    this.clientname = cmd;
                } else {
                    if (cmd.equals("GET")) {
                        int rand_i = 1 + rand.nextInt(7);

                        output.println("\nSubiect:\n" + subj[rand_i] + "\n");
                        System.out.println("send[" + id + "]: subject " + rand_i);
                    }

                    if (cmd.equals("POST")) {
                        output.println("OK");
                    }

                    // if user types exit command
                    if (cmd.equals("bye")) {
                        // printToAllClients (outputString);
                        break;
                    }
                    // output.println("server says: "+ cmd);

                }

            }
            LocalTime myTime = LocalTime.now();
            System.out.println("#" + myTime + "\tclose connection [" + this.id + "]\t" + clientname);

        } catch (Exception e) {
            System.out.println("Error occured " + e.getStackTrace());
        }

    }

    private void printToAllClients(String outputString) {
        for (ServerThread sT : threadList) {
            sT.output.println(outputString);
        }
    }

}
package lab05;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SlaveOne {
    static String message = "blank";
    // The HelloWorld object "obj" is the identifier that is
    // used to refer to the remote object that implements
    // the HelloWorld interface.
    static HelloWorld obj = null;

    public static void main(String args[]) {
        try {
            obj = (HelloWorld) Naming.lookup("//" + "localhost" + "/helloWorld");
            System.out.println("Message from the RMI-server was: \"" + obj.helloWorld() + "\"");
        } catch (Exception e) {
            System.out.println("HelloWorldClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
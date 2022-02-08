package lab05;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer {
    private static class HelloWordMethods extends UnicastRemoteObject implements
            HelloWorld {
        private static final long serialVersionUID = 1L;

        public HelloWordMethods() throws RemoteException {
            super(0);
        }

        @Override
        public String helloWorld() throws RemoteException {
            System.out.println("Invocation to helloWorld was succesful!");
            return "Hello World from RMI server!";
        }
    }

    private static class MathComputationsMethods extends UnicastRemoteObject implements
            MathComputations {
        private static final long serialVersionUID = 1L;

        public MathComputationsMethods() throws RemoteException {
            super(0);
        }

        @Override
        public Double add(ArrayList<Double> numbers) throws RemoteException {
            System.out.println("Add");
            Double sum = 0.0;
            for (Double number : numbers) {
                sum += number;
            }
            return sum;
        }

        @Override
        public Double add(Double a, Double b) throws RemoteException {
            System.out.println("Array Add");
            return a + b;
        }
    }

    public static void main(String args[]) throws Exception {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        System.out.println("RMI server started");
        try { // special exception handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            // do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
        Naming.rebind("helloWorld", new HelloWordMethods());
        Naming.rebind("math", new MathComputationsMethods());
        System.out.println("HelloWorld bound in registry");
    }
}
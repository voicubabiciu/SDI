package lab05;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MathComputations extends Remote {
    Double add(Double a, Double b) throws RemoteException;

    Double add(ArrayList<Double> numbers) throws RemoteException;
}
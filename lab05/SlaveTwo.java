package lab05;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

public class SlaveTwo {
    // The HelloWorld object "obj" is the identifier that is
    // used to refer to the remote object that implements
    // the HelloWorld interface.
    static MathComputations math = null;

    public static void main(String args[]) {
        try {
            math = (MathComputations) Naming.lookup("//" + "localhost" + "/math");
            System.out.println("Result " + math.add(3.0, 4.0));
            System.out.println("Result array " + math.add(new ArrayList<Double>(Arrays.asList(1.0,
                    2.0, 3.0, 45.2))));
        } catch (Exception e) {
            System.out.println("HelloWorldClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

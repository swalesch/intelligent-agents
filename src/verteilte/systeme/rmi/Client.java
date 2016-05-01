package verteilte.systeme.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {
    }

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Schnittstelle stub = (Schnittstelle) registry.lookup("Schnittstelle");
            String response = stub.sayHello();
            System.out.println("Antwort des Servers: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

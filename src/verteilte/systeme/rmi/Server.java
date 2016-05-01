package verteilte.systeme.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Schnittstelle {

    public Server() {
    }

    @Override
    public String sayHello() {
        return "Hello, Merseburg!";
    }

    public static void main(String args[]) {
        try {
            Server obj = new Server();
            Schnittstelle stub = (Schnittstelle) UnicastRemoteObject.exportObject(obj, 0);

            // Registry erzeugen
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Schnittstelle", stub);

            System.err.println("Server ist aktiv und bereit ...");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

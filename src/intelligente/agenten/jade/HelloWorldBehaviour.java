package intelligente.agenten.jade;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class HelloWorldBehaviour extends SimpleBehaviour {
    public HelloWorldBehaviour(Agent a) {
        super(a);
    }

    public void action() {
        System.out.println("Hello World! My name is " + myAgent.getLocalName());
    }

    private boolean finished = true;

    public boolean done() {
        return finished;
    }
}

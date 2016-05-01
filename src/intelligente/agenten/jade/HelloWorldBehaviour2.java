package intelligente.agenten.jade;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class HelloWorldBehaviour2 extends CyclicBehaviour {

    public HelloWorldBehaviour2() {
        super();
    }

    public HelloWorldBehaviour2(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        ACLMessage msgRx = myAgent.receive();
        if (msgRx != null) {
            System.out.println("Aktion vom Agenten namens: " + myAgent.getName());
            System.out.println(msgRx);
            ACLMessage msgTx = msgRx.createReply();
            msgTx.setContent("Hallo");
            myAgent.send(msgTx);
        } else {
            block();
        }
    }

}

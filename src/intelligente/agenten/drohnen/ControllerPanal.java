package intelligente.agenten.drohnen;

import intelligente.agenten.jade.HelloWorld;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.SniffOn;
import jade.lang.acl.ACLMessage;
import jade.tools.sniffer.Sniffer;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ControllerPanal {

	public static void main(String[] args)
			throws StaleProxyException, CodecException, OntologyException {
		// Get a hold on JADE runtime
		Runtime rt = Runtime.instance();
		// Exit the JVM when there are no more containers around
		rt.setCloseVM(true);
		System.out.print("runtime created\n");

		// Create a default profile
		Profile profile = new ProfileImpl(null, 1200, null);
		System.out.print("profile created\n");

		System.out
				.println("Launching a whole in-process platform..." + profile);
		AgentContainer mainContainer = rt.createMainContainer(profile);

		// now set the default Profile to start a container
		ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
		System.out.println("Launching the agent container ..." + pContainer);

		AgentContainer cont = rt.createAgentContainer(pContainer);
		System.out.println(
				"Launching the agent container after ..." + pContainer);

		System.out.println("containers created");
		System.out.println("Launching the rma agent on the main container ...");
		AgentController rma = mainContainer.createNewAgent("rma",
				"jade.tools.rma.rma", new Object[0]);
		rma.start();
		Sniffer sniffer = new Sniffer();
		AgentController snifferController = mainContainer
				.acceptNewAgent("sniffer", sniffer);
		snifferController.start();

		HelloWorld anAgent = new HelloWorld();
		AgentController newAgend = cont.acceptNewAgent("HalloWorld", anAgent);
		newAgend.start();

		// TODO download new Jade Version,
		// http://jade.tilab.com/download/jade/license/jade-download/?x=43&y=8
		// add Agend to Sniffer
		AID address = new AID();
		address.setName(newAgend.getName());
		SniffOn sniffOn = new SniffOn();
		sniffOn.addSniffedAgents(address);
		sniffOn.setSniffer(sniffer.getAID());

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setLanguage(new SLCodec().getName());
		msg.setOntology(JADEManagementOntology.getInstance().getName());
		sniffer.getContentManager().registerLanguage(new SLCodec());
		sniffer.getContentManager()
				.registerOntology(JADEManagementOntology.getInstance());
		Action act = new Action(address, sniffOn);
		sniffer.getContentManager().fillContent(msg, act);
		msg.addReceiver(sniffer.getAID());
		sniffer.send(msg);

	}

}

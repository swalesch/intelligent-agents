package jade.controll;

import java.util.List;

import agent.controller.ControllAgent;
import agent.drone.DroneAgent;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.SniffOn;
import jade.lang.acl.ACLMessage;
import jade.tools.rma.rma;
import jade.tools.sniffer.Sniffer;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ControllerPanal {

	public Runtime _runtime;
	public AgentContainer _mainContainer;
	public rma _rmaAgent;
	public AgentContainer _agentContainer;
	public Sniffer _sniffer;
	public ControllAgent _controllAgent;

	public static ControllerPanal createControllerPanal() throws StaleProxyException {
		return new ControllerPanal();
	}

	public void addAndStartAgentAtMainContainer(String name, Agent agentToAdd) throws StaleProxyException {

		if (agentToAdd instanceof DroneAgent) {
			DroneAgent droneAgent = (DroneAgent) agentToAdd;
			droneAgent.setControllAgent(_controllAgent.getAID());
			_controllAgent.addDroneAgent(droneAgent);
		}
		AgentController rmaController = _mainContainer.acceptNewAgent(name, agentToAdd);
		rmaController.start();

	}

	public void close() {
		_runtime.shutDown();

	}

	public void addAgentToSnifferByName(String agentName) throws CodecException, OntologyException {
		AID addressToAgentToSniff = new AID();
		addressToAgentToSniff.setName(agentName);
		SniffOn sniffOn = createSniffOn(addressToAgentToSniff);

		ACLMessage msg = createRequestMessage();

		AID snifferAddress = _sniffer.getAID();
		Action action = new Action(snifferAddress, sniffOn);
		_rmaAgent.getContentManager().fillContent(msg, action);
		msg.addReceiver(snifferAddress);
		_rmaAgent.send(msg);
	}

	public List<DroneAgent> getAllAgents() {
		return _controllAgent.getallDroneAgents();
	}

	public int getNewDroneID() {
		return _controllAgent.getallDroneAgents().size() + 1;
	}

	private SniffOn createSniffOn(AID address) {
		SniffOn sniffOn = new SniffOn();
		sniffOn.addSniffedAgents(address);
		sniffOn.setSniffer(_sniffer.getAID());
		return sniffOn;
	}

	private static ACLMessage createRequestMessage() {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setLanguage(new SLCodec().getName());
		msg.setOntology(JADEManagementOntology.getInstance().getName());
		return msg;
	}

	private static rma getNewRmaAgent() {
		return new rma();
	}

	private static Sniffer getNewSnifferAgent() {
		return new Sniffer();
	}

	private void setupSniffer() throws StaleProxyException {
		_sniffer = ControllerPanal.getNewSnifferAgent();
		addAndStartAgentAtMainContainer("sniffer", _sniffer);
	}

	private void setupMainContainer() {
		Profile profile = new ProfileImpl(null, 1200, null);
		_mainContainer = _runtime.createMainContainer(profile);
	}

	private void setupRuntime() {
		_runtime = Runtime.instance();
		_runtime.setCloseVM(true);
	}

	private void setupRmaAgent() throws StaleProxyException {
		_rmaAgent = getNewRmaAgent();
		addAndStartAgentAtMainContainer("rma", _rmaAgent);
		_rmaAgent.getContentManager().registerLanguage(new SLCodec());
		_rmaAgent.getContentManager().registerOntology(JADEManagementOntology.getInstance());
	}

	private void setupControllAgent() {
		ControllAgent controllAgent = new ControllAgent();
		try {
			addAndStartAgentAtMainContainer("Controll Agent", controllAgent);
			addAgentToSnifferByName(controllAgent.getName());
			_controllAgent = controllAgent;
		} catch (StaleProxyException | CodecException | OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ControllerPanal() throws StaleProxyException {
		setupRuntime();
		setupMainContainer();

		// TODO needed?
		ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
		_agentContainer = _runtime.createAgentContainer(pContainer);

		setupRmaAgent();
		setupSniffer();
		setupControllAgent();

	}

}

package agent.drone;

import drone.Drone;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class DroneAgent extends Agent {

	private final Drone _drone;
	private final DroneBehaviour _droneBehaviour;
	private AID _controllAgentID;

	public enum Behaviours {
		LINEAR_DRONE_BEHAVIOUR
	}

	public DroneAgent(Drone drone, Behaviours behaviour) {
		super();
		_drone = drone;
		_droneBehaviour = getDroneBehaviour(behaviour);
	}
	public void setControllAgent(AID controllAgent) {
		_controllAgentID = controllAgent;
	}

	public Drone getDrone() {
		return _drone;
	}

	public AID getControllAgent() {
		return _controllAgentID;
	}

	public void sendStatusToControllAgent(String status) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent(status);
		msg.addReceiver(_controllAgentID);
		send(msg);
	}

	public String reciveMsg() {
		ACLMessage msg = receive();
		if (msg != null) {
			return msg.getContent();
		}

		return null;
	}

	private DroneBehaviour getDroneBehaviour(Behaviours behaviour) {
		long gameSettingPeriode = 1000;
		LinearDroneBehaviour droneBehaviour = null;
		switch (behaviour) {
			case LINEAR_DRONE_BEHAVIOUR :
				droneBehaviour = new LinearDroneBehaviour(this, gameSettingPeriode);
				break;

			default :
				// TODO error, not yet implemented
				break;
		}

		return droneBehaviour;
	}

	@Override
	protected void setup() {
		addBehaviour(_droneBehaviour);
		System.out.println("Drone Agent is ready.");
	}
}

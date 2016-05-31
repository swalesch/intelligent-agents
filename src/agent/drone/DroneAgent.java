package agent.drone;

import agent.controller.ControllAgent;
import drone.Drone;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class DroneAgent extends Agent {

	private final Drone _drone;
	private final DroneBehaviour _droneBehaviour;
	private ControllAgent _controllAgent;

	public enum Behaviours {
		LINEAR_DRONE_BEHAVIOUR
	}

	public DroneAgent(Drone drone, Behaviours behaviour) {
		super();
		_drone = drone;
		_droneBehaviour = getDroneBehaviour(behaviour);
	}
	public void setControllAgent(ControllAgent controllAgent) {
		_controllAgent = controllAgent;
	}

	public Drone getDrone() {
		return _drone;
	}

	public ControllAgent getControllAgent() {
		return _controllAgent;
	}

	public void sendStatusToControllAgent(String status) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent(status);
		msg.addReceiver(_controllAgent.getAID());
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

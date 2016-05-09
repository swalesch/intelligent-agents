package intelligente.agenten.drohnen;

import jade.core.Agent;

public class DroneAgent extends Agent {

	private final Drone _drone;
	private final DroneBehaviour _droneBehaviour;

	public enum Behaviours {
		LINEAR_DRONE_BEHAVIOUR
	}

	public DroneAgent(Drone drone, Behaviours behaviour) {
		super();
		_drone = drone;
		_droneBehaviour = getDroneBehaviour(behaviour);
	}

	private DroneBehaviour getDroneBehaviour(Behaviours behaviour) {
		long gameSettingPeriode = 1;
		LinearDroneBehaviour droneBehaviour = null;
		switch (behaviour) {
			case LINEAR_DRONE_BEHAVIOUR :
				droneBehaviour = new LinearDroneBehaviour(this,
						gameSettingPeriode);
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

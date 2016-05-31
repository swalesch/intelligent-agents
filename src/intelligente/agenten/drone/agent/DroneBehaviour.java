package intelligente.agenten.drone.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public abstract class DroneBehaviour extends TickerBehaviour {

	public DroneBehaviour(Agent a, long period) {
		super(a, period);
	}

}

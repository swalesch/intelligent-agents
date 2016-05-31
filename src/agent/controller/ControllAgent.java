package agent.controller;

import java.util.List;

import com.google.common.collect.Lists;

import agent.drone.DroneAgent;
import jade.core.Agent;

public class ControllAgent extends Agent {

	public List<DroneAgent> _allDroneAgents;

	@Override
	protected void setup() {
		addBehaviour(new ControllAgentBehaviour());
		_allDroneAgents = Lists.newArrayList();
	}

	public void addDroneAgent(DroneAgent droneAgent) {
		_allDroneAgents.add(droneAgent);
	}

	public List<DroneAgent> getallDroneAgents() {
		return _allDroneAgents;
	}
}

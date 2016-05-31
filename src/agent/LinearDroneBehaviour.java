package agent;

import drone.Drone;
import drone.movement.DroneVector;

public class LinearDroneBehaviour extends DroneBehaviour {

	DroneAgent _droneAgent;

	public LinearDroneBehaviour(DroneAgent a, long period) {
		super(a, period);
		_droneAgent = a;
		// Random Job for testing
		_droneAgent.getDrone().setJob(createRandomJobForTesting());
	}

	private DroneVector createRandomJobForTesting() {
		int x = 0 + (int) (Math.random() * 600);
		int y = 0 + (int) (Math.random() * 600);
		return new DroneVector(x, y);

	}

	@Override
	protected void onTick() {
		// TODO how to solve Job
		StringBuilder droneLog = new StringBuilder();
		droneLog.append("Tick of " + _droneAgent.getName());
		Drone drone = _droneAgent.getDrone();
		if (drone.isOnDestination()) {
			// test for job
			// if job, fullfill job
			// call controll Agend for new Job
			droneLog.append("\nis on Destination");
			drone.setJob(createRandomJobForTesting());
		} else {
			droneLog.append("\nis flying");
			drone.fly();
		}

		System.out.println(droneLog.toString());
	}

}

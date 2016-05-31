package drone;

import drone.movement.DroneVector;

public class Quadrocopter extends Drone {

	public Quadrocopter(int droneId, int maxLoad, double maxSpeed, DroneVector droneStartPoint) {
		super(droneId, maxLoad, maxSpeed, droneStartPoint);
	}

}

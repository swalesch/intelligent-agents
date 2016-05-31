package drone;

import java.util.List;

import drone.movement.DroneVector;

public abstract class Drone {

	private final int DRONE_ID;
	private final int MAX_LOAD;
	private final double MAX_SPEED;
	private List<Integer> _loaded;
	private DroneVector _destination;
	private DroneVector _dronePosition;
	private DroneVector _homeStation;

	Drone(int droneId, int maxLoad, double maxSpeed, DroneVector droneStartPoint) {
		DRONE_ID = droneId;
		MAX_LOAD = maxLoad;
		MAX_SPEED = maxSpeed;
		_dronePosition = droneStartPoint;
		_homeStation = droneStartPoint;
	}

	public void setHomeSation(DroneVector homeStation) {
		_homeStation = homeStation;
	}

	public DroneVector getHomeStation() {
		return _homeStation;
	}

	public DroneVector getDronePosition() {
		return _dronePosition;
	}

	public void setJob(DroneVector destination) {
		// TODO a real job, including warehouse station, ID list and destination
		_destination = destination;
	}

	public void fly() {
		if (_destination != null) {
			_dronePosition = _dronePosition.calculatePositionByDestinationAndSpeed(_destination, MAX_SPEED);
		}
	}

	public boolean hasNoJob() {
		// TODO include Job
		return isOnDestination();
	}

	public boolean isOnDestination() {
		if (_destination == null) {
			return true;
		}

		if (_destination.equals(_dronePosition)) {
			return true;
		}

		return false;
	}

}

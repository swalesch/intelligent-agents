package intelligente.agenten.drone;

import java.util.List;

import intelligente.agenten.drone.movement.Vector;

public abstract class Drone {

	private final int DRONE_ID;
	private final int MAX_LOAD;
	private final double MAX_SPEED;
	private List<Integer> _loaded;
	private Vector _destination;
	private Vector _dronePosition;
	private Vector _homeStation;

	Drone(int droneId, int maxLoad, double maxSpeed, Vector droneStartPoint) {
		DRONE_ID = droneId;
		MAX_LOAD = maxLoad;
		MAX_SPEED = maxSpeed;
		_dronePosition = droneStartPoint;
		_homeStation = droneStartPoint;
	}

	public void setHomeSation(Vector homeStation) {
		_homeStation = homeStation;
	}

	public Vector getHomeStation() {
		return _homeStation;
	}

	public Vector getDronePosition() {
		return _dronePosition;
	}

	public void setJob(Vector destination) {
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

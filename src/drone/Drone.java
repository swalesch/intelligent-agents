package drone;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import drone.movement.DroneVector;
import job.Job;
import job.itemlist.ItemList;

public abstract class Drone {

	private final int DRONE_ID;
	private final int MAX_LOAD;
	private final double MAX_SPEED;
	private List<Integer> _loaded;
	private DroneVector _dronePosition;
	private DroneVector _homeStation;
	private Job _job;

	Drone(int droneId, int maxLoad, double maxSpeed, DroneVector droneStartPoint) {
		DRONE_ID = droneId;
		MAX_LOAD = maxLoad;
		MAX_SPEED = maxSpeed;
		_dronePosition = droneStartPoint;
		_homeStation = droneStartPoint;
		_loaded = new CopyOnWriteArrayList<Integer>();
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

	public void setJob(Job job) {
		_job = job;
	}

	public Job getJob() {
		return _job;
	}

	public void fly(DroneVector destination) {
		_dronePosition = _dronePosition.calculatePositionByDestinationAndSpeed(destination, MAX_SPEED);
	}

	public void flyHome() {
		_dronePosition = _dronePosition.calculatePositionByDestinationAndSpeed(_homeStation, MAX_SPEED);
	}

	public boolean hasJob() {
		return _job != null;
	}

	public boolean isOnPosition(DroneVector destination) {
		return _dronePosition.equals(destination);
	}

	public int getFreeSpace() {
		int freeSpace = MAX_LOAD;
		for (int itemID : _loaded) {
			freeSpace -= ItemList.getWeightOfID(itemID);
		}
		return freeSpace;
	}

	public List<Integer> getLoadedItems() {
		return _loaded;
	}

	public void unloadItemOnCurrentPosition(int itemIDToUnload) {
		// TODO getPositionObject -> Warehouse or JobDestination?
		// add to Position before removing

		for (int i = 0; i < _loaded.size(); i++) {
			if (_loaded.get(i) == itemIDToUnload) {
				_loaded.remove(i);
				break;
			}
		}

	}

	public void loadItemOnCurrentPosition(int itemToLoad) {
		// TODO getPositionObject -> Warehouse or other?
		// remove from Position before adding

		_loaded.add(itemToLoad);

	}
}

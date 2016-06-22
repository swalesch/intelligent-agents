package agent.drone;

import java.util.List;

import org.json.simple.parser.ParseException;

import drone.Drone;
import drone.movement.DroneVector;
import job.Job;
import job.Tuple;
import job.itemlist.ItemList;

public class LinearDroneBehaviour extends DroneBehaviour {

	DroneAgent _droneAgent;
	private boolean _statusSended;
	private DroneVector _currentDestination;
	private DestinationGoal _destinationGoal;
	private int _itemToLoad;

	private enum DestinationGoal {
		LOAD, UNLOAD
	}

	public LinearDroneBehaviour(DroneAgent a, long period) {
		super(a, period);
		_droneAgent = a;
		_statusSended = false;
	}

	@Override
	protected void onTick() {
		StringBuilder droneLog = new StringBuilder();

		if (_droneAgent.getDrone().hasJob()) {
			fullFillJob(droneLog);
		} else {
			droneLog.append(_droneAgent.getLocalName() + " has no Job.");

			if (!_statusSended) {
				droneLog.append("\nask for new Job");
				sendJobRequest();
			}

			String reciveMsg = _droneAgent.reciveMsg();
			if (reciveMsg != null) {
				_statusSended = false;
				droneLog.append("\nnew Job recieved: " + reciveMsg);
				castNewJob(reciveMsg);
			}
		}

		// Drone LOG
		if (droneLog.length() != 0) {
			System.out.println(droneLog.toString());
		}
	}

	private void fullFillJob(StringBuilder droneLog) {
		Drone drone = _droneAgent.getDrone();
		Job job = drone.getJob();

		if (_currentDestination == null) {
			droneLog.append(_droneAgent.getLocalName() + " selected a new Destination");
			selectNewDestinationAndGoalFromJob(job);
		}

		if (drone.isOnPosition(_currentDestination)) {
			String lineBreak = (droneLog.length() == 0) ? "" : "\n";
			switch (_destinationGoal) {
				case LOAD :
					job.removeItem(new Tuple<Integer, DroneVector>(_itemToLoad, _currentDestination));
					droneLog.append(lineBreak + _droneAgent.getLocalName() + " loaded a new Item");
					drone.loadItemOnCurrentPosition(_itemToLoad);
					break;
				case UNLOAD :
					droneLog.append(lineBreak + _droneAgent.getLocalName() + " unloads all Items");
					List<Integer> loadedItems = drone.getLoadedItems();
					for (int item : loadedItems) {
						drone.unloadItemOnCurrentPosition(item);
					}

					if (isJobDone(job)) {
						drone.setJob(null);
					}

					break;
				default :
					break;
			}

			// reset all behavior values
			_currentDestination = null;
			_destinationGoal = null;
			_itemToLoad = 0;

		} else {
			drone.fly(_currentDestination);
		}
	}

	private boolean isJobDone(Job job) {
		return job.getItemLocation().isEmpty();
	}

	private void selectNewDestinationAndGoalFromJob(Job job) {
		int capacityLeft = _droneAgent.getDrone().getFreeSpace();

		for (Tuple<Integer, DroneVector> item : job.getItemLocation()) {
			if (isItemFit(capacityLeft, item)) {
				_destinationGoal = DestinationGoal.LOAD;
				_currentDestination = item.getValueSecond();
				_itemToLoad = item.getValueFirst();
			}
		}

		if (hasNotSpaceOrAllItemsLoaded()) {
			_destinationGoal = DestinationGoal.UNLOAD;
			_currentDestination = job.getDestination();
		}

	}

	private boolean hasNotSpaceOrAllItemsLoaded() {
		return _currentDestination == null && _destinationGoal == null;
	}

	private boolean isItemFit(int capacityLeft, Tuple<Integer, DroneVector> item) {
		return ItemList.getWeightOfID(item.getValueFirst()) <= capacityLeft;
	}

	private void castNewJob(String reciveMsg) {
		try {
			Job job = new Job(reciveMsg);
			_droneAgent.getDrone().setJob(job);
		} catch (ParseException e) {
			System.err.println("Could not parse new Job: " + reciveMsg + "\n" + e.getMessage());
		}
	}

	private void sendJobRequest() {
		_droneAgent.sendStatusToControllAgent("no Job");
		_statusSended = true;
	}
}

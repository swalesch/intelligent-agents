package agent.drone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import drone.Drone;
import drone.movement.DroneVector;

public class LinearDroneBehaviour extends DroneBehaviour {

	DroneAgent _droneAgent;
	private boolean _statusSended;

	public LinearDroneBehaviour(DroneAgent a, long period) {
		super(a, period);
		_droneAgent = a;
		// Random Job for testing
		_statusSended = false;
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
		Drone drone = _droneAgent.getDrone();
		if (drone.isOnDestination()) {
			droneLog.append("Tick of " + _droneAgent.getName());
			// test for job
			// if job, fullfill job
			// call controll Agend for new Job
			if (!_statusSended) {
				droneLog.append("\nno Job status send");
				_droneAgent.sendStatusToControllAgent("no Job");
				_statusSended = true;
			}

			String reciveMsg = _droneAgent.reciveMsg();
			if (reciveMsg != null) {
				_statusSended = false;
				droneLog.append("\nno Job status recieved");
				DroneVector destinationFromMsg = getDestinationFromMsg(reciveMsg);
				if (destinationFromMsg != null) {
					droneLog.append("\nnew Destination");
					drone.setJob(destinationFromMsg);
				}
			}
		} else {
			// droneLog.append("\nis flying");
			drone.fly();
		}
		if (droneLog.length() != 0) {
			System.out.println(droneLog.toString());
		}
	}

	private DroneVector getDestinationFromMsg(String jsonMsg) {
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(jsonMsg);
			JSONObject json = (JSONObject) obj;
			String messageType = (String) json.get("messageType");
			if (messageType.equals("new Job")) {
				JSONObject destination = (JSONObject) json.get("destination");
				int x = ((Long) destination.get("x")).intValue();
				int y = ((Long) destination.get("y")).intValue();
				return new DroneVector(x, y);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

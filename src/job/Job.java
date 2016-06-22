package job;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.Lists;

import drone.movement.DroneVector;

public class Job {
	private int _jobID;
	private DroneVector _destination;
	private List<Tuple<Integer, DroneVector>> _itemLocation;

	public Job(int jobID, DroneVector destination, List<Tuple<Integer, DroneVector>> itemLocation) {
		_jobID = jobID;
		_destination = destination;
		_itemLocation = itemLocation;
	}

	public Job(String jsonString) throws ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonString);
		JSONObject json = (JSONObject) obj;
		String messageType = (String) json.get("messageType");
		if (messageType.equals("Job")) {
			_jobID = ((Long) json.get("jobID")).intValue();

			JSONObject destination = (JSONObject) json.get("destination");
			int x = ((Double) destination.get("x")).intValue();
			int y = ((Double) destination.get("y")).intValue();
			_destination = new DroneVector(x, y);

			_itemLocation = Lists.newArrayList();
			List<JSONObject> itemLocation = (List<JSONObject>) json.get("itemLocation");
			for (JSONObject item : itemLocation) {
				int itemID = ((Long) item.get("itemId")).intValue();
				int itemX = ((Double) item.get("x")).intValue();
				int itemY = ((Double) item.get("y")).intValue();;
				_itemLocation.add(new Tuple<Integer, DroneVector>(itemID, new DroneVector(itemX, itemY)));
			}
		}
	}

	public int getJobID() {
		return _jobID;
	}

	public DroneVector getDestination() {
		return _destination;
	}

	public List<Tuple<Integer, DroneVector>> getItemLocation() {
		return _itemLocation;
	}

	@SuppressWarnings("unchecked")
	public String jobToJasonString() {
		JSONObject json = new JSONObject();
		json.put("messageType", "Job");
		json.put("jobID", _jobID);

		JSONObject destination = new JSONObject();
		json.put("destination", destination);
		destination.put("x", _destination.getXValue());
		destination.put("y", _destination.getYValue());

		List<JSONObject> itemList = Lists.newArrayList();
		for (Tuple<Integer, DroneVector> itemTuple : _itemLocation) {
			Integer itemID = itemTuple.getValueFirst();
			DroneVector location = itemTuple.getValueSecond();

			JSONObject item = new JSONObject();
			item.put("itemId", itemID);
			item.put("x", location.getXValue());
			item.put("y", location.getYValue());
			itemList.add(item);
		}
		json.put("itemLocation", itemList);

		return json.toString();
	}

	public void removeItem(Tuple<Integer, DroneVector> itemToDelete) {
		for (int i = 0; i < _itemLocation.size(); i++) {
			if (_itemLocation.get(i).equals(itemToDelete)) {
				_itemLocation.remove(i);
				break;
			}
		}

	}
}

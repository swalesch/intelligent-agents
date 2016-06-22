package job.warhouse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import drone.movement.DroneVector;

public class WarhouseList {
	private static Map<Integer, DroneVector> _warhouses;

	public static void createNRandomWarhouses(int n, int maxX, int maxY) {
		_warhouses = Maps.newHashMap();
		for (int i = 1; i <= n; i++) {
			int x = 0 + (int) (Math.random() * maxX);
			int y = 0 + (int) (Math.random() * maxY);
			_warhouses.put(i, new DroneVector(x, y));
		}
	}

	public static List<DroneVector> getAllLocations() {
		return _warhouses.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
	}
}

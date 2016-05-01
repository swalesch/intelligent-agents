package intelligente.agenten.drohnen;

import java.util.List;

public abstract class Drone {

    private final int DRONE_ID;
    private final int MAX_LOAD;
    private final int MAX_SPEED;
    private List<Integer> _loaded;
    private MapPoint _destination;
    private MapPoint _dronePosition;

    Drone(int droneId, int maxLoad, int maxSpeed, MapPoint droneStartPoint) {
        DRONE_ID = droneId;
        MAX_LOAD = maxLoad;
        MAX_SPEED = maxSpeed;
        _dronePosition = droneStartPoint;
    }

    public void fly() {
        if (_destination != null) {
            _dronePosition = _dronePosition.calculateNewMapPointByDestinationAndSpeed(_destination, MAX_SPEED);
        }
    }

}

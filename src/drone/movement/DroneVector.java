package drone.movement;

public class DroneVector {

	private double _xValue;
	private double _yValue;

	public DroneVector(double xValue, double yValue) {
		_xValue = xValue;
		_yValue = yValue;
	}

	public static DroneVector getCopyOfDroneVector(DroneVector toCopy) {
		return new DroneVector(toCopy);
	}

	private DroneVector(DroneVector a) {
		_xValue = a.getXValue();
		_yValue = a.getYValue();
	}

	public void setValues(double xValue, double yValue) {
		_xValue = xValue;
		_yValue = yValue;
	}

	public double getXValue() {
		return _xValue;
	}

	public double getYValue() {
		return _yValue;
	}

	public DroneVector multiplyByScalar(double scalar) {
		_xValue = _xValue * scalar;
		_yValue = _yValue * scalar;
		return this;
	}

	public DroneVector getUnitDroneVector() {
		double magnitude = getMagnitude();
		if (magnitude == 0) {
			return new DroneVector(0, 0);
		}
		return new DroneVector(_xValue / magnitude, _yValue / magnitude);
	}

	public boolean isZeroVector() {
		return _xValue == 0 && _yValue == 0;
	}

	public double getMagnitude() {
		return Math.sqrt(Math.pow(_xValue, 2) + Math.pow(_yValue, 2));
	}

	public DroneVector calculateAddDroneVector(DroneVector toAdd) {
		return new DroneVector(_xValue + toAdd.getXValue(), _yValue + toAdd.getYValue());
	}

	public DroneVector calculateSubtractionDroneVector(DroneVector toSub) {
		return new DroneVector(_xValue - toSub.getXValue(), _yValue - toSub.getYValue());
	}

	public DroneVector calculatePositionByDestinationAndSpeed(DroneVector destination, double speed) {
		DroneVector directionDroneVector = destination.calculateSubtractionDroneVector(this);

		// destination reached
		if (directionDroneVector.getMagnitude() <= speed) {
			return getCopyOfDroneVector(destination);
		}

		DroneVector unitDirectionDroneVector = directionDroneVector.getUnitDroneVector().multiplyByScalar(speed);

		// new position
		return unitDirectionDroneVector.calculateAddDroneVector(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DroneVector other = (DroneVector) obj;
		if (Double.doubleToLongBits(_xValue) != Double.doubleToLongBits(other._xValue))
			return false;
		if (Double.doubleToLongBits(_yValue) != Double.doubleToLongBits(other._yValue))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(_xValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_yValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "(" + _xValue + ", " + _yValue + ")";
	}

}

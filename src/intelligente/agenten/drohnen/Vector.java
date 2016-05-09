package intelligente.agenten.drohnen;

public class Vector {

	private double _xValue;
	private double _yValue;

	public Vector(double xValue, double yValue) {
		_xValue = xValue;
		_yValue = yValue;
	}

	public static Vector getCopyOfVector(Vector toCopy) {
		return new Vector(toCopy);
	}

	private Vector(Vector a) {
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

	public Vector multiplyByScalar(double scalar) {
		_xValue = _xValue * scalar;
		_yValue = _yValue * scalar;
		return this;
	}

	public Vector getUnitVector() {
		double magnitude = getMagnitude();
		if (magnitude == 0) {
			return new Vector(0, 0);
		}
		return new Vector(_xValue / magnitude, _yValue / magnitude);
	}

	public boolean isZeroVector() {
		return _xValue == 0 && _yValue == 0;
	}

	public double getMagnitude() {
		return Math.sqrt(Math.pow(_xValue, 2) + Math.pow(_yValue, 2));
	}

	public Vector calculateAddVector(Vector toAdd) {
		return new Vector(_xValue + toAdd.getXValue(), _yValue + toAdd.getYValue());
	}

	public Vector calculateSubtractionVector(Vector toSub) {
		return new Vector(_xValue - toSub.getXValue(), _yValue - toSub.getYValue());
	}

	public Vector calculatePositionByDestinationAndSpeed(Vector destination, double speed) {
		Vector directionVector = destination.calculateSubtractionVector(this);

		// destination reached
		if (directionVector.getMagnitude() <= speed) {
			return getCopyOfVector(destination);
		}

		Vector unitDirectionVector = directionVector.getUnitVector().multiplyByScalar(speed);

		// new position
		return unitDirectionVector.calculateAddVector(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
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

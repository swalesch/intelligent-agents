package intelligente.agenten.drohnen;

public class MapPoint {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _xValue;
        result = prime * result + _yValue;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MapPoint other = (MapPoint) obj;
        if (_xValue != other._xValue)
            return false;
        if (_yValue != other._yValue)
            return false;
        return true;
    }

    private int _xValue;
    private int _yValue;

    public MapPoint(int xValue, int yValue) {
        _xValue = xValue;
        _yValue = yValue;
    }

    public void setValues(int xValue, int yValue) {
        _xValue = xValue;
        _yValue = yValue;
    }

    public int getXValue() {
        return _xValue;
    }

    public int getYValue() {
        return _yValue;
    }

    public MapPoint calculateNewMapPointByDestinationAndSpeed(MapPoint destination, int speed) {
        double directionVektorX = destination.getXValue() - _xValue;
        double directionVektorY = destination.getYValue() - _yValue;
        double distance = Math.sqrt(Math.pow(directionVektorX, 2) + Math.pow(directionVektorY, 2));
        double movedDistance = distance - speed;
        if (movedDistance <= 0) {
            return new MapPoint(destination.getXValue(), destination.getYValue());
        }
        double movingX = directionVektorX / distance * movedDistance;
        double movingY = directionVektorY / distance * movedDistance;

        return new MapPoint((int) Math.round(_xValue + movingX), (int) Math.round(_yValue + movingY));
    }

}

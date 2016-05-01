package intelligente.agenten.drohne;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import intelligente.agenten.drohnen.MapPoint;

public class MapPointTest {
    @Test
    public void testCalculateNewMapPointByDestinationAndSpeed_destinationReached() {
        MapPoint point1 = new MapPoint(0, 0);
        MapPoint point2 = new MapPoint(4, 4);
        MapPoint newPosition = point1.calculateNewMapPointByDestinationAndSpeed(point2, 6);
        assertThat(newPosition).isEqualTo(point2);

        point1 = new MapPoint(4, 4);
        point2 = new MapPoint(0, 0);
        newPosition = point1.calculateNewMapPointByDestinationAndSpeed(point2, 6);
        assertThat(newPosition).isEqualTo(point2);
    }

    @Test
    public void testCalculateNewMapPointByDestinationAndSpeed_destinationNotReached() {
        MapPoint point1 = new MapPoint(0, 0);
        MapPoint point2 = new MapPoint(4, 4);
        MapPoint newPosition = point1.calculateNewMapPointByDestinationAndSpeed(point2, 5);
        assertThat(newPosition).isEqualTo(new MapPoint(3, 3));
    }
}

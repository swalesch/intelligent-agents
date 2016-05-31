package intelligente.agenten.drone.movement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import drone.movement.DroneVector;

public class VectorTest {
    @Test
    public void testCalculateNewVectorByDestinationAndSpeed_destinationReached() {
        DroneVector droneVector1 = new DroneVector(0, 0);
        DroneVector droneVector2 = new DroneVector(4, 4);
        
        assertThat(droneVector1.calculatePositionByDestinationAndSpeed(droneVector2, 6)).isEqualTo(droneVector2);
        assertThat(droneVector2.calculatePositionByDestinationAndSpeed(droneVector1, 6)).isEqualTo(droneVector1);

        DroneVector droneVector4 = new DroneVector(1, 1);
        assertThat(droneVector1.calculatePositionByDestinationAndSpeed(droneVector4, Math.sqrt(2))).isEqualTo(droneVector4);
        assertThat(droneVector4.calculatePositionByDestinationAndSpeed(droneVector1, Math.sqrt(2))).isEqualTo(droneVector1);
        
        
        assertThat(droneVector1.calculatePositionByDestinationAndSpeed(droneVector1, Math.sqrt(2))).isEqualTo(droneVector1);
    }

    @Test
    public void testCalculateNewVectorByDestinationAndSpeed_destinationNotReached() {
        DroneVector droneVector1 = new DroneVector(0, 0);
        DroneVector droneVector2 = new DroneVector(2, 2);
        assertThat(droneVector1.calculatePositionByDestinationAndSpeed(droneVector2, Math.sqrt(2))).isEqualTo(new DroneVector(1, 1));
        assertThat(droneVector2.calculatePositionByDestinationAndSpeed(droneVector1, Math.sqrt(2))).isEqualTo(new DroneVector(1, 1));
        
        DroneVector droneVector3 = new DroneVector(1, 2);
        assertThat(droneVector3.calculatePositionByDestinationAndSpeed(droneVector2,0.4)).isEqualTo(new DroneVector(1.4, 2));
        assertThat(droneVector2.calculatePositionByDestinationAndSpeed(droneVector3,0.4)).isEqualTo(new DroneVector(1.6, 2));
        
    }
    
    @Test
    public void testCalculateSubtractionVector(){
    	 DroneVector droneVector1 = new DroneVector(0, 0);
         DroneVector droneVector2 = new DroneVector(1, 1);
         assertThat(droneVector1.calculateSubtractionDroneVector(droneVector2)).isEqualTo(new DroneVector(-1,-1));
         assertThat(droneVector2.calculateSubtractionDroneVector(droneVector1)).isEqualTo(new DroneVector(1,1));
    }
    
    @Test
    public void testCalculateAddVector(){
    	 DroneVector droneVector1 = new DroneVector(0, 0);
         DroneVector droneVector2 = new DroneVector(1, 1);
         DroneVector droneVector3 = new DroneVector(2, 2);
         assertThat(droneVector1.calculateAddDroneVector(droneVector2)).isEqualTo(new DroneVector(1,1));
         assertThat(droneVector2.calculateAddDroneVector(droneVector1)).isEqualTo(new DroneVector(1,1));
         assertThat(droneVector3.calculateAddDroneVector(droneVector2)).isEqualTo(new DroneVector(3,3));
         assertThat(droneVector2.calculateAddDroneVector(droneVector3)).isEqualTo(new DroneVector(3,3));
    }
    
    @Test
    public void testCalculateMegnitude(){
    	assertThat(new DroneVector(0, 0).getMagnitude()).isEqualTo(0);
    	assertThat(new DroneVector(1, 1).getMagnitude()).isEqualTo(Math.sqrt(2));
    	assertThat(new DroneVector(-1, -1).getMagnitude()).isEqualTo(Math.sqrt(2));
    	assertThat(new DroneVector(2, 2).getMagnitude()).isEqualTo(Math.sqrt(8));
    }
    @Test
    public void testUnitVector(){
    	assertThat(new DroneVector(0, 0).getUnitDroneVector()).isEqualTo(new DroneVector(0,0));
    	assertThat(new DroneVector(0, 4).getUnitDroneVector()).isEqualTo(new DroneVector(0,1));
    	assertThat(new DroneVector(3, 0).getUnitDroneVector()).isEqualTo(new DroneVector(1,0));
    	assertThat(new DroneVector(1, 1).getUnitDroneVector()).isEqualTo(new DroneVector(1/Math.sqrt(2),1/Math.sqrt(2)));
    	assertThat(new DroneVector(1, 2).getUnitDroneVector()).isEqualTo(new DroneVector(1/Math.sqrt(5),2/Math.sqrt(5)));
    }
    @Test
    public void testMultiplyByScalar(){
    	assertThat(new DroneVector(0, 0).multiplyByScalar(0)).isEqualTo(new DroneVector(0,0));
    	assertThat(new DroneVector(1, 1).multiplyByScalar(0)).isEqualTo(new DroneVector(0,0));
    	assertThat(new DroneVector(1, 2).multiplyByScalar(2)).isEqualTo(new DroneVector(2,4));
    	assertThat(new DroneVector(1, 2).multiplyByScalar(-2)).isEqualTo(new DroneVector(-2,-4));
    }
}

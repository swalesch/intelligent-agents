package intelligente.agenten.drohne;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import intelligente.agenten.drohnen.Vector;

public class VectorTest {
    @Test
    public void testCalculateNewVectorByDestinationAndSpeed_destinationReached() {
        Vector vector1 = new Vector(0, 0);
        Vector vector2 = new Vector(4, 4);
        
        assertThat(vector1.calculatePositionByDestinationAndSpeed(vector2, 6)).isEqualTo(vector2);
        assertThat(vector2.calculatePositionByDestinationAndSpeed(vector1, 6)).isEqualTo(vector1);

        Vector vector4 = new Vector(1, 1);
        assertThat(vector1.calculatePositionByDestinationAndSpeed(vector4, Math.sqrt(2))).isEqualTo(vector4);
        assertThat(vector4.calculatePositionByDestinationAndSpeed(vector1, Math.sqrt(2))).isEqualTo(vector1);
        
        
        assertThat(vector1.calculatePositionByDestinationAndSpeed(vector1, Math.sqrt(2))).isEqualTo(vector1);
    }

    @Test
    public void testCalculateNewVectorByDestinationAndSpeed_destinationNotReached() {
        Vector vector1 = new Vector(0, 0);
        Vector vector2 = new Vector(2, 2);
        assertThat(vector1.calculatePositionByDestinationAndSpeed(vector2, Math.sqrt(2))).isEqualTo(new Vector(1, 1));
        assertThat(vector2.calculatePositionByDestinationAndSpeed(vector1, Math.sqrt(2))).isEqualTo(new Vector(1, 1));
        
        Vector vector3 = new Vector(1, 2);
        assertThat(vector3.calculatePositionByDestinationAndSpeed(vector2,0.4)).isEqualTo(new Vector(1.4, 2));
        assertThat(vector2.calculatePositionByDestinationAndSpeed(vector3,0.4)).isEqualTo(new Vector(1.6, 2));
        
    }
    
    @Test
    public void testCalculateSubtractionVector(){
    	 Vector vector1 = new Vector(0, 0);
         Vector vector2 = new Vector(1, 1);
         assertThat(vector1.calculateSubtractionVector(vector2)).isEqualTo(new Vector(-1,-1));
         assertThat(vector2.calculateSubtractionVector(vector1)).isEqualTo(new Vector(1,1));
    }
    
    @Test
    public void testCalculateAddVector(){
    	 Vector vector1 = new Vector(0, 0);
         Vector vector2 = new Vector(1, 1);
         Vector vector3 = new Vector(2, 2);
         assertThat(vector1.calculateAddVector(vector2)).isEqualTo(new Vector(1,1));
         assertThat(vector2.calculateAddVector(vector1)).isEqualTo(new Vector(1,1));
         assertThat(vector3.calculateAddVector(vector2)).isEqualTo(new Vector(3,3));
         assertThat(vector2.calculateAddVector(vector3)).isEqualTo(new Vector(3,3));
    }
    
    @Test
    public void testCalculateMegnitude(){
    	assertThat(new Vector(0, 0).getMagnitude()).isEqualTo(0);
    	assertThat(new Vector(1, 1).getMagnitude()).isEqualTo(Math.sqrt(2));
    	assertThat(new Vector(-1, -1).getMagnitude()).isEqualTo(Math.sqrt(2));
    	assertThat(new Vector(2, 2).getMagnitude()).isEqualTo(Math.sqrt(8));
    }
    @Test
    public void testUnitVector(){
    	assertThat(new Vector(0, 0).getUnitVector()).isEqualTo(new Vector(0,0));
    	assertThat(new Vector(0, 4).getUnitVector()).isEqualTo(new Vector(0,1));
    	assertThat(new Vector(3, 0).getUnitVector()).isEqualTo(new Vector(1,0));
    	assertThat(new Vector(1, 1).getUnitVector()).isEqualTo(new Vector(1/Math.sqrt(2),1/Math.sqrt(2)));
    	assertThat(new Vector(1, 2).getUnitVector()).isEqualTo(new Vector(1/Math.sqrt(5),2/Math.sqrt(5)));
    }
    @Test
    public void testMultiplyByScalar(){
    	assertThat(new Vector(0, 0).multiplyByScalar(0)).isEqualTo(new Vector(0,0));
    	assertThat(new Vector(1, 1).multiplyByScalar(0)).isEqualTo(new Vector(0,0));
    	assertThat(new Vector(1, 2).multiplyByScalar(2)).isEqualTo(new Vector(2,4));
    	assertThat(new Vector(1, 2).multiplyByScalar(-2)).isEqualTo(new Vector(-2,-4));
    }
}

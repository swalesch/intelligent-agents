package intelligente.agenten.drohnen;

import jade.core.Agent;

public class QuadcopterDrone extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new NormalDroneBehaviour(this));
    }
}

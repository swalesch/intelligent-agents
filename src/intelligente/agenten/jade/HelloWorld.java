package intelligente.agenten.jade;

import jade.core.Agent;

public class HelloWorld extends Agent {
	@Override
	protected void setup() {
		System.out.println("Starte Hallo World");
		addBehaviour(new HelloWorldBehaviour2(this));
	}
}

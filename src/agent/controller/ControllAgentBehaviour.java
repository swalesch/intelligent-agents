package agent.controller;

import org.json.simple.JSONObject;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ControllAgentBehaviour extends CyclicBehaviour {

	private enum ActionPerform {
		CREATE_NEW_Random_JOB, ERROR
	}

	@Override
	public void action() {
		ACLMessage msgRx = myAgent.receive();
		if (msgRx != null) {
			System.out.println("Receive msg from: " + msgRx.getSender().getLocalName());
			System.out.println(msgRx);

			ACLMessage msgTx = msgRx.createReply();
			switch (validateMsgContent(msgRx.getContent())) {
				case CREATE_NEW_Random_JOB :
					msgTx.setContent(createRandomJob());
					break;

				default :
					msgTx.setContent("Error, not yet implemented");
					break;
			}

			myAgent.send(msgTx);
		} else {
			block();
		}
	}

	private ActionPerform validateMsgContent(String content) {
		if (content.equals("no Job")) {
			return ActionPerform.CREATE_NEW_Random_JOB;
		} else {
			return ActionPerform.ERROR;
		}
	}

	private String createRandomJob() {
		int x = 0 + (int) (Math.random() * 600);
		int y = 0 + (int) (Math.random() * 600);

		JSONObject json = new JSONObject();
		json.put("messageType", "new Job");
		JSONObject destination = new JSONObject();
		json.put("destination", destination);
		destination.put("x", x);
		destination.put("y", y);

		return json.toString();
	}

}

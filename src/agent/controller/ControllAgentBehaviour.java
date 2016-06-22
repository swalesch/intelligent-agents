package agent.controller;

import java.util.List;

import com.google.common.collect.Lists;

import drone.movement.DroneVector;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import job.Job;
import job.Tuple;
import job.itemlist.ItemList;
import job.warhouse.WarhouseList;

public class ControllAgentBehaviour extends CyclicBehaviour {

	private enum ActionPerform {
		CREATE_NEW_Random_JOB, ERROR
	}

	private int _globalJobID = 0;

	@Override
	public void action() {
		ACLMessage msgRx = myAgent.receive();
		if (msgRx != null) {
			System.out.println(
					"Receive msg from: " + msgRx.getSender().getLocalName() + " with content: " + msgRx.getContent());

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

		List<Tuple<Integer, DroneVector>> itemLocations = createRandomIdemLocationsFromWarhouses();
		Job job = new Job(++_globalJobID, new DroneVector(x, y), itemLocations);

		return job.jobToJasonString();
	}

	/**
	 * creates joblist out of 1 up to 7 items
	 */
	private List<Tuple<Integer, DroneVector>> createRandomIdemLocationsFromWarhouses() {
		List<Tuple<Integer, DroneVector>> itemLocations = Lists.newArrayList();

		List<DroneVector> allWarehouseLocations = WarhouseList.getAllLocations();
		int itemIDRange = ItemList.getSize();
		int itemCount = 1 + (int) (Math.random() * 6);
		for (int i = 0; i < itemCount; i++) {
			int warhouse = 0 + (int) (Math.random() * (allWarehouseLocations.size()));
			int itemID = 1 + (int) (Math.random() * itemIDRange);
			itemLocations.add(new Tuple<Integer, DroneVector>(itemID, allWarehouseLocations.get(warhouse)));
		}

		return itemLocations;
	}

}

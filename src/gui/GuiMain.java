package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import agent.drone.DroneAgent;
import drone.Drone;
import drone.Quadrocopter;
import drone.movement.DroneVector;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.controll.ControllerPanal;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import job.itemlist.ItemList;
import job.warhouse.WarhouseList;

public class GuiMain extends JFrame {

	private static final long serialVersionUID = 5845195683189476922L;
	public static JFrame _mainFrame;
	private static ControllerPanal _controllerPanal;
	private static MapGraphicsPanel _graphicsPanel;

	public static void main(String[] args)
			throws CodecException, OntologyException, StaleProxyException, InterruptedException {
		_mainFrame = new JFrame();
		_mainFrame.setTitle("Intelligente Dronen");
		_mainFrame.setSize(800, 600);
		_mainFrame.setResizable(false);
		_mainFrame.setLayout(null);

		_mainFrame.setVisible(true);
		_mainFrame.addWindowListener(getWindowListener());

		_graphicsPanel = new MapGraphicsPanel(0, 0, 600, 600);
		_mainFrame.add(_graphicsPanel);

		JButton button = new JButton("Add Drone");
		button.addActionListener(simplebuttonActionAddDrone());
		button.setBounds(600, 0, 100, 30);
		_mainFrame.add(button);

		ItemList.createRandomListWithXItems(15);
		WarhouseList.createNRandomWarhouses(3, 600, 600);
		setupAgentController();

		paintDronePosition();
	}

	private static void paintDronePosition() throws InterruptedException {
		while (true) {
			Thread.sleep(1000);
			_graphicsPanel.clear();

			List<DroneVector> allWarhouseLocations = WarhouseList.getAllLocations();
			for (DroneVector warhouse : allWarhouseLocations) {
				_graphicsPanel.drawWarhouse((int) warhouse.getXValue(), (int) warhouse.getYValue(), Color.RED);
			}

			List<DroneAgent> allAgents = _controllerPanal.getAllAgents();
			for (DroneAgent drone : allAgents) {
				DroneVector dronePosition = drone.getDrone().getDronePosition();
				_graphicsPanel.drawCrossWithLable(drone.getLocalName(), (int) dronePosition.getXValue(),
						(int) dronePosition.getYValue(), Color.BLACK);
			}

			for (DroneAgent drone : allAgents) {
				if (drone.getDrone().hasJob()) {
					DroneVector jobDestination = drone.getDrone().getJob().getDestination();
					_graphicsPanel.drawJobDestiantion((int) jobDestination.getXValue(),
							(int) jobDestination.getYValue(), Color.BLUE);
				}

			}
		}

	}

	private static ActionListener simplebuttonActionAddDrone() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int droneID = _controllerPanal.getNewDroneID();
				Drone drone = new Quadrocopter(droneID, 50, 10.0, new DroneVector(0, 0));
				DroneAgent droneAgent = new DroneAgent(drone, DroneAgent.Behaviours.LINEAR_DRONE_BEHAVIOUR);
				try {
					_controllerPanal.addAndStartAgentAtMainContainer("Drone " + droneID, droneAgent);
					_controllerPanal.addAgentToSnifferByName(droneAgent.getName());

				} catch (StaleProxyException | CodecException | OntologyException exeption) {
					exeption.printStackTrace();
				}
			}
		};
	}

	private static void setupAgentController() throws StaleProxyException, CodecException, OntologyException {
		_controllerPanal = ControllerPanal.createControllerPanal();

		_controllerPanal.addAgentToSnifferByName(_controllerPanal._rmaAgent.getName());
		try {
			AgentController amsAgentController = _controllerPanal._mainContainer.getAgent("ams");
			_controllerPanal.addAgentToSnifferByName(amsAgentController.getName());

			AgentController dfAgentController = _controllerPanal._mainContainer.getAgent("df");
			_controllerPanal.addAgentToSnifferByName(dfAgentController.getName());
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}

	private static WindowListener getWindowListener() {
		return new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				if (_controllerPanal != null) {
					_controllerPanal.close();
				}
				System.exit(JFrame.DISPOSE_ON_CLOSE);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		};
	}

}

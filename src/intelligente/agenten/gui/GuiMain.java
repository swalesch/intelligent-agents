package intelligente.agenten.gui;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import intelligente.agenten.drone.agent.ControllerPanal;
import intelligente.agenten.jade.HelloWorld;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class GuiMain extends JFrame {

	private static final long serialVersionUID = 5845195683189476922L;
	public static JFrame _mainFrame;
	private static ControllerPanal _controllerPanal;
	private static MapGraphicsPanel _graphicsPanel;

	public static void main(String[] args) throws CodecException, OntologyException, StaleProxyException {
		_mainFrame = new JFrame();
		_mainFrame.setTitle("Intelligente Dronen");
		_mainFrame.setSize(800, 600);
		_mainFrame.setResizable(false);

		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		_mainFrame.add(jPanel);
		_mainFrame.setVisible(true);
		_mainFrame.addWindowListener(getWindowListener());

		_graphicsPanel = new MapGraphicsPanel(600, 600);
		jPanel.add(_graphicsPanel);
		_graphicsPanel.drawLine(0, 0, 100, 100, Color.BLACK);
		_graphicsPanel.drawLine(0, 100, 100, 0, Color.RED);
		_graphicsPanel.drawCross(20, 40, Color.GREEN);

		setupAgentController();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HelloWorld anAgent = new HelloWorld();
		_controllerPanal.addAndStartAgentAtMainContainer("HalloWorld", anAgent);
		_controllerPanal.addAgentToSnifferByName(anAgent.getName());

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

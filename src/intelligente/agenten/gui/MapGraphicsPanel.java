package intelligente.agenten.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MapGraphicsPanel extends JPanel {
	private static final long serialVersionUID = 6689198061018337620L;

	private BufferedImage _image;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (_image != null) {
			g.drawImage(_image, 0, 0, null);
		}
	}

	public MapGraphicsPanel(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		// setSize(width, height);
		createEmptyImage();
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public void drawLine(int x1, int y1, int x2, int y2, Color color) {
		Graphics2D g2d = (Graphics2D) _image.getGraphics();
		g2d.setColor(color);
		g2d.drawLine(x1, y1, x2, y2);
		repaint();
	}
	public void drawCross(int x, int y, Color color) {

		drawLine(x - 4, y + 4, x + 4, y - 4, color);
		drawLine(x - 4, y - 4, x + 4, y + 4, color);
	}

	public void drawText(String textToDraw, int x, int y, Color color) {
		Graphics2D g2d = (Graphics2D) _image.getGraphics();
		g2d.setColor(color);
		g2d.drawString(textToDraw, x, y);
		repaint();
	}

	public void drawCrossWithLable(String textToDraw, int x, int y, Color color) {
		drawCross(x, y, color);
		drawText(textToDraw, x - textToDraw.length() * 3, y - 5, color);
	}

	private void createEmptyImage() {
		_image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		repaint();
	}

	public void clear() {
		createEmptyImage();
	}
}

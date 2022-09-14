import javax.swing.*;
import java.awt.*;

public class GraphicPanel extends JPanel {
	private Color graphicColor = Color.GREEN;
	private Integer width;
	private Integer height;

	public void paint(Graphics g) {
		super.paint(g);
		width = getWidth();
		height = getHeight();

		drawGrid(g);
		drawAxis(g);
		drawGraphic(g);
	}

	private void drawGrid(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);

		for(int x = width/2; x < width; x += 30) {
			g.drawLine(x, 0, x, height);
		}

		for(int x = width/2; x > 0; x -= 30) {
			g.drawLine(x, 0, x, height);
		}

		for(int y = height/2; y < height; y += 30) {
			g.drawLine(0, y, width, y);
		}

		for(int y = height/2; y > 0; y -= 30) {
			g.drawLine(0, y, width, y);
		}
	}

	private void drawAxis(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(width/2, 0, width/2, height);
		g.drawLine(0, height/2, width, height/2);
	}

	private void drawGraphic(Graphics g) {
		g.setColor(graphicColor);

		for(int x = 0; x < width; x++) {
			int realX = x - width/2;
			double rad = realX/30.0;
			double func = Math.sin(rad);
			int y = height + (int)(sin * 90);

			g.drawOval(x, y, 2, 2);
		}
	}
}

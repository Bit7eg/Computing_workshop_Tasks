import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class GraphicPanel extends JPanel {
	private Function<Double, Double> function = (x)->0.0;
	private Interpolation interpolationObj;
	private Color functionColor = Color.RED;
	private Color polynomialColor = Color.GREEN;
	private Color lineColor = Color.BLUE;
	private Double minX = -1.0;
	private Double maxX = 1.0;
	private Integer argumentsNumber = 2;
	private Integer width;
	private Integer height;

	public GraphicPanel() {
		super();
		this.interpolationObj = new Interpolation(this.argumentsNumber);
	}

	public GraphicPanel(Function<Double, Double> func) {
		super();
		this.function = func;
		this.interpolationObj = new Interpolation(this.argumentsNumber);
	}

	public void paint(Graphics g) {
		super.paint(g);
		width = getWidth();
		height = getHeight();

		drawGrid(g);
		drawAxis(g);
		drawGraphic(g);
	}

	public void setFunctionColor(String color) {
		try {
			this.functionColor = Color.decode(color);
		} catch (Exception e) {
			this.functionColor = Color.RED;
		}
		repaint();
	}

	public void setPolynomialColor(String color) {
		try {
			this.polynomialColor = Color.decode(color);
		} catch (Exception e) {
			this.polynomialColor = Color.GREEN;
		}
		repaint();
	}

	public void setLineFuncColor(String color) {
		try {
			this.lineColor = Color.decode(color);
		} catch (Exception e) {
			this.lineColor = Color.BLUE;
		}
		repaint();
	}

	public void setMinX(Double minX) {
		this.minX = minX;
		interpolationReload();
 		repaint();
	}

	public void setMaxX(Double maxX) {
		this.maxX = maxX;
		interpolationReload();
		repaint();
	}

	public void setArgumentsNumber(Integer number) {
		this.argumentsNumber = number;
		interpolationReload();
		repaint();
	}

	private void interpolationReload() {
		Double step = (this.maxX - this.minX)/this.argumentsNumber;
		this.interpolationObj.clear();
		for (Double x = this.minX; x <= this.maxX; x += step) {
			this.interpolationObj.putPair(x, function.apply(x));
		}
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
		Double yAxisCoefficient = (maxX)/(maxX - minX);
		if (yAxisCoefficient < 1 && yAxisCoefficient >0)
			g.drawLine(width/2, 0, width/2, height);

		g.drawLine(0, height/2, width, height/2);
	}

	private void drawGraphic(Graphics g) {
		g.setColor(functionColor);

		for(int x = 0; x < width; x++) {
			int realX = x - width/2;
			double rad = realX/30.0;
			double func = Math.sin(rad);
			int y = height + (int)(func * 90);

			g.drawOval(x, y, 2, 2);
		}
	}
}

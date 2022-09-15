import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class GraphicPanel extends JPanel {
	private Function<Double, Double> function = (x)->0.0;
	private Interpolation interpolationObj;
	private Color functionColor = Color.RED;
	private Color polynomialColor = Color.GREEN;
	private Color lineColor = Color.BLUE;
	private Color nodeColor = Color.YELLOW;
	private Double minX = -1.0;
	private Double maxX = 1.0;
	private Double minY = -1.05;
	private Double maxY = 1.05;
	private Integer argumentsNumber = 2;
	private Integer width;
	private Integer height;

	public GraphicPanel() {
		super();
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		interpolationReload();
	}

	public GraphicPanel(Function<Double, Double> func) {
		super();
		this.function = func;
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		interpolationReload();
	}

	public void paint(Graphics g) {
		super.paint(g);
		width = getWidth();
		height = getHeight();

		drawGrid(g);
		drawAxis(g);
		drawGraphic(g);
	}

	public Color setFunctionColor(String color) {
		try {
			this.functionColor = Color.decode(color);
		} catch (NumberFormatException exception) {
			this.functionColor = Color.RED;
		}
		repaint();
		return this.functionColor;
	}

	public Color setPolynomialColor(String color) {
		try {
			this.polynomialColor = Color.decode(color);
		} catch (NumberFormatException exception) {
			this.polynomialColor = Color.GREEN;
		}
		repaint();
		return this.polynomialColor;
	}

	public Color setLineFuncColor(String color) {
		try {
			this.lineColor = Color.decode(color);
		} catch (NumberFormatException exception) {
			this.lineColor = Color.BLUE;
		}
		repaint();
		return this.lineColor;
	}

	public Color setNodeColor(String color) {
		try	{
			this.nodeColor = Color.decode(color);
		} catch (NumberFormatException exception) {
			this.nodeColor = Color.ORANGE;
		}
		repaint();
		return this.nodeColor;
	}

	public Double setMinX(String minX) {
		try	{
			this.minX = Double.parseDouble(minX);
		} catch (NumberFormatException exception) {
			this.minX = -1.0;
		}
		if (this.minX > this.maxX) this.minX = this.maxX - 2.0;
		interpolationReload();
		repaint();
		return this.minX;
	}

	public Double setMaxX(String maxX) {
		try	{
			this.maxX = Double.parseDouble(maxX);
		} catch (NumberFormatException exception) {
			this.maxX = 1.0;
		}
		if (this.minX > this.maxX) this.maxX = this.minX + 2.0;
		interpolationReload();
		repaint();
		return this.maxX;
	}

	public Integer setArgumentsNumber(String number) {
		try {
			this.argumentsNumber = Integer.parseInt(number);
		} catch (NumberFormatException exception) {
			this.argumentsNumber = 2;
		}
		if (this.argumentsNumber < 2) this.argumentsNumber = 2;
		interpolationReload();
		repaint();
		return this.argumentsNumber;
	}

	private void yBoundsCalc() {
		//TODO: make y bounds calculation (calculate poly twice?)
		this.minY = interpolationObj.getLowerYBound();
		this.maxY = interpolationObj.getUpperYBound();
		if (maxY < 0) {
			this.minY *= 1.05;
			this.maxY *= 0.95;
		} else if (this.minY > 0) {
			this.minY *= 0.95;
			this.maxY *= 1.05;
		} else {
			this.minY *= 1.05;
			this.maxY *= 1.05;
		}
	}

	private void interpolationReload() {
		Double step = (this.maxX - this.minX)/(this.argumentsNumber - 1);
		this.interpolationObj.clear();
		for (Double x = this.minX; x <= this.maxX; x += step) {
			this.interpolationObj.putPair(x, function.apply(x));
		}
		yBoundsCalc();
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
		Double yAxisCoefficient = minX/(minX - maxX), xAxisCoefficient = minY/(minY - maxY);
		if (yAxisCoefficient < 1 && yAxisCoefficient > 0)
			g.drawLine((int)Math.round(width * yAxisCoefficient), 0,
					(int)Math.round(width * yAxisCoefficient), height);

		if (xAxisCoefficient < 1 && xAxisCoefficient > 0)
			g.drawLine(0, (int)Math.round(height * xAxisCoefficient),
					width, (int)Math.round(height * xAxisCoefficient));
	}

	private void drawGraphic(Graphics g) {
		Double xCoefficient = width * minX/(minX - maxX),
				yCoefficient = height * minY/(minY - maxY);

		g.setColor(nodeColor);
		g.drawOval(0, (int)Math.round(
				this.interpolationObj.lineFunctionY(
						xCoefficient
				) + yCoefficient
		), 10, 10);

		for(Integer x = 0; x < width; x++) {
			Double realX = x - xCoefficient;

			Double func = this.function.apply(realX);
			Double poly = this.interpolationObj.polynomialFunctionY(realX);
			Double line = this.interpolationObj.lineFunctionY(realX);

			Integer yFunc = (int)Math.round(func + yCoefficient);
			Integer yPoly = (int)Math.round(poly + yCoefficient);
			Integer yLine = (int)Math.round(line + yCoefficient);

			g.setColor(functionColor);
			g.drawOval(x, yFunc, 1, 1);

			g.setColor(polynomialColor);
			g.drawOval(x, yPoly, 1, 1);

			g.setColor(lineColor);
			g.drawOval(x, yLine, 1, 1);

			if (yPoly.equals(yLine) && this.interpolationObj.getPairsCount() > 2) {
				g.setColor(nodeColor);
				g.drawOval(x, yLine, 10, 10);
			}
		}

		g.setColor(nodeColor);
		g.drawOval(width - 1, (int)Math.round(
				this.interpolationObj.lineFunctionY(
						width - 1 - xCoefficient
				) + yCoefficient
		), 10, 10);
	}
}

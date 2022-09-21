import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class GraphicPanel extends JPanel {
	private Function<Double, Double> function = (x)->0.0;
	private Function<Integer, Double> nodeCoefficient = (i)->1.0;
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
	private Integer width = 600;
	private Integer height = 300;

	public GraphicPanel() {
		super();
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		interpolationReload();
		this.setLayout(null);
	}

	public GraphicPanel(Function<Double, Double> func) {
		super();
		this.function = func;
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		interpolationReload();
		this.setLayout(null);
	}

	public GraphicPanel(Function<Double, Double> func, Function<Integer, Double> nodeCoefficient) {
		super();
		this.function = func;
		this.nodeCoefficient = nodeCoefficient;
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		interpolationReload();
		this.setLayout(null);
	}

	public void updateGraphic() {
		removeAll();
		repaint();
		revalidate();
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
		updateGraphic();
		return this.functionColor;
	}

	public Color setPolynomialColor(String color) {
		try {
			this.polynomialColor = Color.decode(color);
		} catch (NumberFormatException exception) {
			this.polynomialColor = Color.GREEN;
		}
		updateGraphic();
		return this.polynomialColor;
	}

	public Color setLineFuncColor(String color) {
		try {
			this.lineColor = Color.decode(color);
		} catch (NumberFormatException exception) {
			this.lineColor = Color.BLUE;
		}
		updateGraphic();
		return this.lineColor;
	}

	public Color setNodeColor(String color) {
		try	{
			this.nodeColor = Color.decode(color);
		} catch (NumberFormatException exception) {
			this.nodeColor = Color.ORANGE;
		}
		updateGraphic();
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
		updateGraphic();
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
		updateGraphic();
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
		updateGraphic();
		return this.argumentsNumber;
	}

	private void yBoundsCalc() {
		minY = maxY = function.apply(minX);
		Double xCoefficient = (maxX - minX)/width;
		for(Integer x = 1; x < width; x++) {
			Double realX = x * xCoefficient + minX;

			Double func = this.function.apply(realX);
			Double poly = this.interpolationObj.polynomialFunctionY(realX);

			if (func > this.maxY) this.maxY = func;
			if (func < this.minY) this.minY = func;
			if (poly > this.maxY) this.maxY = poly;
			if (poly < this.minY) this.minY = poly;
		}
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
		Double x = this.minX;
		for (Integer i = 0; x < this.maxX && i < argumentsNumber ; i++) {
			this.interpolationObj.putPair(x, function.apply(x));
			x += step * this.nodeCoefficient.apply(i);
		}
		this.interpolationObj.putPair(this.maxX, function.apply(this.maxX));
		yBoundsCalc();
	}

	private void drawGrid(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		Double yAxisCoefficient = minX/(minX - maxX), xAxisCoefficient = minY/(minY - maxY),
				xCoefficient = (maxX - minX)/width, yCoefficient = (maxY - minY)/height;

		removeAll();
		repaint();
		revalidate();

		for(int x = width/2; x < width; x += 30) {
			g.drawLine(x, 0, x, height);

			JLabel coords = new JLabel(Double.toString(x * xCoefficient + minX));
			if (xAxisCoefficient >= 1) coords.setBounds(x, height - 10, 25, 10);
			else if (xAxisCoefficient <= 0) coords.setBounds(x, 1, 25, 10);
			else if (xAxisCoefficient >= 0.5) coords.setBounds(x,
					(int)Math.round(height * xAxisCoefficient) - 10, 25, 10);
			else coords.setBounds(x,
						(int)Math.round(height * xAxisCoefficient), 25, 10);
			this.add(coords);
		}

		for(int x = width/2 - 30; x > 0; x -= 30) {
			g.drawLine(x, 0, x, height);

			JLabel coords = new JLabel(Double.toString(x * xCoefficient + minX));
			if (xAxisCoefficient >= 1) coords.setBounds(x, height - 10, 25, 10);
			else if (xAxisCoefficient <= 0) coords.setBounds(x, 1, 25, 10);
			else if (xAxisCoefficient >= 0.5) coords.setBounds(
					x, (int)Math.round(height * xAxisCoefficient) - 10, 25, 10);
			else coords.setBounds(x, (int)Math.round(height * xAxisCoefficient), 25, 10);
			this.add(coords);
		}

		for(int y = height/2; y < height; y += 30) {
			g.drawLine(0, y, width, y);

			JLabel coords = new JLabel(Double.toString(-(y * yCoefficient + minY)));
			if (yAxisCoefficient >= 1) coords.setBounds(width - 25, y, 25, 10);
			else if (yAxisCoefficient <= 0) coords.setBounds(1, y, 25, 10);
			else if (yAxisCoefficient >= 0.5) coords.setBounds(
					(int)Math.round(width * yAxisCoefficient) - 25, y, 25, 10);
			else coords.setBounds((int)Math.round(width * yAxisCoefficient), y, 25, 10);
			this.add(coords);
		}

		for(int y = height/2 - 30; y > 0; y -= 30) {
			g.drawLine(0, y, width, y);

			JLabel coords = new JLabel(Double.toString(-(y * yCoefficient + minY)));
			if (yAxisCoefficient >= 1) coords.setBounds(width - 25, y, 25, 10);
			else if (yAxisCoefficient <= 0) coords.setBounds(1, y, 25, 10);
			else if (yAxisCoefficient >= 0.5) coords.setBounds(
					(int)Math.round(width * yAxisCoefficient) - 25, y, 25, 10);
			else coords.setBounds((int)Math.round(width * yAxisCoefficient), y, 25, 10);
			this.add(coords);
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
		Double xCoefficient = (maxX - minX)/width,
				yCoefficient = height/(maxY - minY);

		Integer lastLineY, lastPolyY, lastFuncY;
		lastPolyY = lastFuncY = lastLineY =
				(int)Math.round((-this.interpolationObj.lineFunctionY(minX) - minY) * yCoefficient);

		g.setColor(nodeColor);
		Integer dotWidth = 10, dotHeight = 10;
		for (Double x = minX; x != null; x = this.interpolationObj.getHigherNode(x)) {
			g.fillOval((int)Math.round((x - minX)/xCoefficient) - dotWidth/2,
					(int)Math.round((-this.interpolationObj.getNodeValue(x) - minY) * yCoefficient) - dotHeight/2,
					dotWidth, dotHeight);
		}

		for(Integer x = 0; x < width; x++) {
			Double realX = x * xCoefficient + minX;

			Double func = this.function.apply(realX);
			Double poly = this.interpolationObj.polynomialFunctionY(realX);
			Double line = this.interpolationObj.lineFunctionY(realX);

			Integer yFunc = (int)Math.round((-func - minY) * yCoefficient);
			Integer yPoly = (int)Math.round((-poly - minY) * yCoefficient);
			Integer yLine = (int)Math.round((-line - minY) * yCoefficient);

			g.setColor(functionColor);
			g.drawLine(x-1, lastFuncY, x, yFunc);

			g.setColor(polynomialColor);
			g.drawLine(x-1, lastPolyY, x, yPoly);

			g.setColor(lineColor);
			g.drawLine(x-1, lastLineY, x, yLine);

			lastFuncY = yFunc;
			lastPolyY = yPoly;
			lastLineY = yLine;
		}
	}
}

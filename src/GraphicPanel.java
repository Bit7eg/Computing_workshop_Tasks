import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class GraphicPanel extends JPanel {
	interface NodeCounter {
		Double getNode(Integer node, Double minX, Double maxX, Integer nodesNumber);
	}
	private Function<Double, Double> function = (x)->0.0;
	private NodeCounter nodeCounter = (i, minX, maxX, nodes)->(maxX - minX)/(nodes - 1) * i + minX;
	private Interpolation interpolationObj;
	private Color functionColor = Color.RED;
	private Color polynomialColor = Color.GREEN;
	private Color lineColor = Color.BLUE;
	private Color nodeColor = Color.YELLOW;
	private Double minX = 500.0;
	private Double maxX = 501.0;
	private Double screenMinX;
	private Double screenMaxX;
	private Double minY;
	private Double maxY;
	private Integer argumentsNumber = 5;
	private Integer width = 600;
	private Integer height = 300;
	private Boolean isScaleFree = true;

	public GraphicPanel() {
		super();
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		xScreenUpdate();
		interpolationReload();
		this.setLayout(null);
	}

	public GraphicPanel(Function<Double, Double> func) {
		super();
		this.function = func;
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		xScreenUpdate();
		interpolationReload();
		this.setLayout(null);
	}

	public GraphicPanel(NodeCounter nodeCounter) {
		super();
		this.nodeCounter = nodeCounter;
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		xScreenUpdate();
		interpolationReload();
		this.setLayout(null);
	}

	public GraphicPanel(Function<Double, Double> func, NodeCounter nodeCounter) {
		super();
		this.function = func;
		this.nodeCounter = nodeCounter;
		this.interpolationObj = new Interpolation(this.argumentsNumber);
		xScreenUpdate();
		interpolationReload();

		for (int i = 1; i < 50; i++) {
			Double x = minX + (maxX - minX)/50 * i;
			Double y = this.function.apply(x);
			System.out.printf("%15.10f %15.10f\n", x, y);
		}

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
		if (this.minX >= this.maxX) this.minX = this.maxX - 2.0;
		xScreenUpdate();
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
		if (this.minX >= this.maxX) this.maxX = this.minX + 2.0;
		xScreenUpdate();
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

	public Boolean switchScaleState() {
		this.isScaleFree = !this.isScaleFree;
		xScreenUpdate();
		yBoundsCalc();
		updateGraphic();
		return this.isScaleFree;
	}

	private void xScreenUpdate() {
		if (this.isScaleFree) {
			this.screenMinX = this.minX - (this.maxX - this.minX)/20;
			this.screenMaxX = this.maxX + (this.maxX - this.minX)/20;
		}
	}

	private void yBoundsCalc() {
		if (this.isScaleFree) {
			minY = maxY = function.apply(minX);
			Double xCoefficient = (screenMaxX - screenMinX)/width;
			Integer lastPixel = (int)((maxX - screenMinX)/xCoefficient);
			for(Integer x = (int)((minX - screenMinX)/xCoefficient); x <= lastPixel; x++) {
				Double realX = x * xCoefficient + screenMinX;

				Double func = this.function.apply(realX);
				Double poly = this.interpolationObj.polynomialFunctionY(realX);

				if (func > this.maxY) this.maxY = func;
				if (func < this.minY) this.minY = func;
				if (poly > this.maxY) this.maxY = poly;
				if (poly < this.minY) this.minY = poly;
			}
			Double extension = (maxY - minY)/20;
			this.minY -= extension;
			this.maxY += extension;
		}
	}

	private void interpolationReload() {
		this.interpolationObj.clear();
		Double x = this.nodeCounter.getNode(0, this.minX, this.maxX, this.argumentsNumber);
		for (Integer i = 1; x >= this.minX && x <= this.maxX && i < argumentsNumber ; i++) {
			this.interpolationObj.putPair(x, function.apply(x));
			x = this.nodeCounter.getNode(i, this.minX, this.maxX, this.argumentsNumber);
		}
		this.interpolationObj.putPair(x, function.apply(x));
		yBoundsCalc();
	}

	private void drawGrid(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		Double yAxisCoefficient = screenMinX/(screenMinX - screenMaxX), xAxisCoefficient = maxY/(maxY - minY),
				xCoefficient = (screenMaxX - screenMinX)/width, yCoefficient = (maxY - minY)/height;

		Integer xStep = 65, yStep = 30;

		if (xAxisCoefficient >= 1) xAxisCoefficient = 1.0;
		else if (xAxisCoefficient <= 0) xAxisCoefficient = 0.0;
		if (yAxisCoefficient >= 1) yAxisCoefficient = 1.0;
		else if (yAxisCoefficient <= 0) yAxisCoefficient = 0.0;

		removeAll();
		repaint();
		revalidate();

		for(int x = (int)(width * yAxisCoefficient) + xStep; x < width; x += xStep) {
			g.drawLine(x, 0, x, height);

			JLabel coords = new JLabel(String.format("%.2e", x * xCoefficient + screenMinX));
			if (xAxisCoefficient >= 0.75) coords.setBounds(x,
					(int)Math.round(height * xAxisCoefficient) - 10, xStep - 5, 10);
			else coords.setBounds(x,
						(int)Math.round(height * xAxisCoefficient), xStep - 5, 10);
			this.add(coords);
		}

		for(int x = (int)(width * yAxisCoefficient) - xStep; x > 0; x -= xStep) {
			g.drawLine(x, 0, x, height);

			JLabel coords = new JLabel(String.format("%.2e", x * xCoefficient + screenMinX));
			if (xAxisCoefficient >= 0.75) coords.setBounds(
					x, (int)Math.round(height * xAxisCoefficient) - 10, xStep - 5, 10);
			else coords.setBounds(
					x, (int)Math.round(height * xAxisCoefficient), xStep - 5, 10);
			this.add(coords);
		}

		for(int y = (int)(height * xAxisCoefficient) + yStep; y < height; y += yStep) {
			g.drawLine(0, y, width, y);

			JLabel coords = new JLabel(String.format("%.2e", maxY - y * yCoefficient));
			if (yAxisCoefficient >= 0.75) coords.setBounds(
					(int)Math.round(width * yAxisCoefficient) - xStep + 5, y, xStep - 5, 10);
			else coords.setBounds((int)Math.round(width * yAxisCoefficient), y, xStep - 5, 10);
			this.add(coords);
		}

		for(int y = (int)(height * xAxisCoefficient) - yStep; y > 0; y -= yStep) {
			g.drawLine(0, y, width, y);

			JLabel coords = new JLabel(String.format("%.2e", maxY - y * yCoefficient));
			if (yAxisCoefficient >= 0.75) coords.setBounds(
					(int)Math.round(width * yAxisCoefficient) - xStep + 5, y, xStep - 5, 10);
			else coords.setBounds((int)Math.round(width * yAxisCoefficient), y, xStep - 5, 10);
			this.add(coords);
		}
	}

	private void drawAxis(Graphics g) {
		g.setColor(Color.BLACK);
		Double yAxisCoefficient = screenMinX/(screenMinX - screenMaxX), xAxisCoefficient = maxY/(maxY - minY);
		if (yAxisCoefficient < 1 && yAxisCoefficient > 0)
			g.drawLine((int)Math.round(width * yAxisCoefficient), 0,
					(int)Math.round(width * yAxisCoefficient), height);

		if (xAxisCoefficient < 1 && xAxisCoefficient > 0)
			g.drawLine(0, (int)Math.round(height * xAxisCoefficient),
					width, (int)Math.round(height * xAxisCoefficient));
	}

	private void drawGraphic(Graphics g) {
		Double usedMaxX = screenMaxX, usedMinX = screenMinX;
		if (maxX < usedMaxX) usedMaxX = maxX;
		if (minX > usedMinX) usedMinX = minX;

		Double xCoefficient = (screenMaxX - screenMinX)/width,
				yCoefficient = height/(maxY - minY);

		Integer lastLineY = (int)Math.round((maxY - this.interpolationObj.lineFunctionY(usedMinX)) * yCoefficient),
				lastPolyY = (int)Math.round((maxY - this.interpolationObj.polynomialFunctionY(usedMinX)) * yCoefficient),
				lastFuncY = (int)Math.round((maxY - this.function.apply(usedMinX)) * yCoefficient);

		g.setColor(nodeColor);
		Integer dotWidth = 10, dotHeight = 10;
		Double x = this.interpolationObj.getCeilingNode(usedMinX);
		if (x != null) {
			for (Integer coordinateX = (int) Math.round((x - screenMinX) / xCoefficient);
				 coordinateX < width;
				 coordinateX = (int) Math.round((x - screenMinX) / xCoefficient)) {
				g.fillOval(coordinateX - dotWidth / 2,
						(int) Math.round((maxY - this.interpolationObj.getNodeValue(x)) * yCoefficient) - dotHeight / 2,
						dotWidth, dotHeight);
				x = this.interpolationObj.getHigherNode(x);
				if (x == null) break;
			}
		}

		for(Integer coordinateX = (int)Math.round((usedMinX - screenMinX)/xCoefficient);
			coordinateX < width;
			coordinateX++) {
			Double realX = coordinateX * xCoefficient + screenMinX;

			Double func = this.function.apply(realX);
			Double poly = this.interpolationObj.polynomialFunctionY(realX);
			Double line = this.interpolationObj.lineFunctionY(realX);

			Integer yFunc = (int)Math.round((maxY - func) * yCoefficient);
			Integer yPoly = (int)Math.round((maxY - poly) * yCoefficient);
			Integer yLine = (int)Math.round((maxY - line) * yCoefficient);

			g.setColor(functionColor);
			g.drawLine(coordinateX-1, lastFuncY, coordinateX, yFunc);

			g.setColor(polynomialColor);
			g.drawLine(coordinateX-1, lastPolyY, coordinateX, yPoly);

			g.setColor(lineColor);
			g.drawLine(coordinateX-1, lastLineY, coordinateX, yLine);

			lastFuncY = yFunc;
			lastPolyY = yPoly;
			lastLineY = yLine;
			if (realX > usedMaxX) break;
		}
	}
}

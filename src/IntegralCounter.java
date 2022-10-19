import java.util.function.Function;

public class IntegralCounter {
    private Function<Double, Double> function = (x)->{
        return 1.0;
    };
    private Double a = 0.0;
    private Double b = 1.0;

    public IntegralCounter(Function<Double, Double> f, Double minX, Double maxX) {
        this.function = f;
        this.a = minX;
        this.b = maxX;
    }

    public Double leftRectangleCalculate(Integer rectangleNumber) {
        double integral = 0.0;

        Double rectangleWidth = (this.b - this.a)/rectangleNumber;
        for (Double xi = this.a; this.b - xi >= rectangleWidth/4 ; xi += rectangleWidth) {
            integral += function.apply(xi) * rectangleWidth;
        }

        return integral;
    }

    public Double centerRectangleCalculate(Integer rectangleNumber) {
        double integral = 0.0;

        Double rectangleWidth = (this.b - this.a)/rectangleNumber;
        for (Double xi = this.a + rectangleWidth/2; this.b - xi >= rectangleWidth/4; xi += rectangleWidth) {
            integral += function.apply(xi) * rectangleWidth;
        }

        return integral;
    }

    public Double trapezoidCalculate(Integer trapezoidNumber) {
        double integral = 0.0;

        double trapezoidHeight = (this.b - this.a)/trapezoidNumber;
        double lastX = this.a;
        for (double xi = lastX + trapezoidHeight; xi - this.b <= trapezoidHeight/4; xi += trapezoidHeight) {
            integral += (function.apply(lastX) + function.apply(xi))/2 * trapezoidHeight;
            lastX = xi;
        }

        return integral;
    }

    public Double SimpsonCalculate(Integer intervalNumber) {
        return 0.0;
    }

    public Double GaussCalculate(Integer intervalNumber, Integer nodesNumber) {
        return 0.0;
    }
}

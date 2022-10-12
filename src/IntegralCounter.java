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
        Double integral = 0.0;

        Double rectangleWidth = (this.b - this.a)/rectangleNumber;
        for (Double xi = this.a; xi < this.b; xi += rectangleWidth) {
            integral += function.apply(xi) * rectangleWidth;
        }

        return integral;
    }

    public Double centerRectangleCalculate(Integer rectangleNumber) {
        Double integral = 0.0;

        Double rectangleWidth = (this.b - this.a)/rectangleNumber;
        for (Double xi = this.a + rectangleWidth/2; xi < this.b; xi += rectangleWidth) {
            integral += function.apply(xi) * rectangleWidth;
        }

        return integral;
    }

    public Double trapezoidCalculate(Integer trapezoidNumber) {
        Double integral = 0.0;

        Double trapezoidHeight = (this.b - this.a)/trapezoidNumber;
        Double lastX = this.a;
        for (Double xi = lastX + trapezoidHeight; xi <= this.b; xi += trapezoidHeight) {
            integral += (function.apply(lastX) + function.apply(xi))/2 * trapezoidHeight;
            lastX = xi;
        }

        return integral;
    }
}

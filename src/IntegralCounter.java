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
        double lastY = function.apply(this.a);
        for (double xi = this.a + trapezoidHeight; xi - this.b <= trapezoidHeight/4; xi += trapezoidHeight) {
            double yi = function.apply(xi);
            integral += (lastY + yi)/2 * trapezoidHeight;
            lastY = yi;
        }

        return integral;
    }

    public Double SimpsonCalculate(Integer intervalNumber) {
        double integral = 0.0;

        double intervalLength = (this.b - this.a)/intervalNumber;
        double lastY = function.apply(this.a);
        for (double xi = this.a + intervalLength; xi - this.b <= intervalLength/4; xi += intervalLength) {
            double yi = function.apply(xi);
            integral += (lastY + 4 * function.apply(xi - intervalLength/2) + yi) * intervalLength/6;
            lastY = yi;
        }

        return integral;
    }

    public Double GaussCalculate(Integer intervalNumber, Integer polynomialPower) {
        double integral = 0.0;

        LegendrePolynomial polynomial = new LegendrePolynomial(polynomialPower);

        return integral;
    }
}

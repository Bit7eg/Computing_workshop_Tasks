public class LegendrePolynomial {
    private double[] coefficients;
    private double[] prevCoefficients;
    private double[] roots;
    LegendrePolynomial(int power) {
        this.coefficients = new double[power + 1];
        if (power == 0) {
            this.coefficients[0] = 1;
            this.prevCoefficients = new double[1];
            this.prevCoefficients[0] = 0;
            return;
        }
        this.prevCoefficients = new double[power];
        this.roots = new double[power];
        if (power == 1) {
            this.coefficients[1] = 1;
            this.coefficients[0] = 0;
            this.prevCoefficients[0] = 1;
            this.roots[0] = 0;
            return;
        }
        LegendrePolynomial p1 = new LegendrePolynomial(power - 1),
                p2 = new LegendrePolynomial(power - 2);
        this.coefficients[0] = (-(double)(power - 1)/(double)power) *
                p2.coefficients[0];
        this.prevCoefficients[0] = p1.coefficients[0];
        for (int i = 1; i < power - 1; i++) {
            this.coefficients[i] =
                    ((double)(2 * power - 1)/(double)power) * p1.coefficients[i - 1] -
                            ((double)(power - 1)/(double)power) * p2.coefficients[i];
            this.prevCoefficients[i] = p1.coefficients[i];
        }
        this.coefficients[power - 1] = ((double)(2 * power - 1)/
                (double)power) * p1.coefficients[power - 2];
        this.coefficients[power] = ((double)(2 * power - 1)/
                (double)power) * p1.coefficients[power - 1];
        this.prevCoefficients[power - 1] = p1.coefficients[power - 1];

        for (int i = 1; i <= power; i++) {
            double prevRoot = Math.cos((Math.PI * (4 * i - 1))/(double)(4 * power + 2));
            this.roots[i - 1] = prevRoot -
                    this.calculate(prevRoot)/this.calculateDerivative(prevRoot);
            while (Math.abs(prevRoot - this.roots[i - 1]) > 1.0E-15) {
                prevRoot = this.roots[i - 1];
                this.roots[i - 1] = prevRoot -
                        this.calculate(prevRoot)/this.calculateDerivative(prevRoot);
            }
        }
    }

    public double calculate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public double calculateDerivative(double x) {
        double result = this.prevCoefficients[0];
        int power = this.prevCoefficients.length;
        for (int i = 1; i < power; i++) {
            result += (this.prevCoefficients[i] - this.coefficients[i - 1]) * Math.pow(x, i);
        }
        result -= this.coefficients[power - 1] * Math.pow(x, power);
        result -= this.coefficients[power] * Math.pow(x, power + 1);
        result *= (double)power/(1 - x*x);
        return result;
    }

    public double[] getRoots() {
        return this.roots;
    }
}

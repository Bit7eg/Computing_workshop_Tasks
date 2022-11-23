import java.util.function.Function;

public class Task2 {
    static final int firstColumnWidth = 20,
            numberOutputAccuracy = 25,
            intervalsNumber = 8;
    static final double a = -Math.log(Math.sqrt(3)), b = -a;

    public static void main(String[] args) {
        IntegralCounter integral = new IntegralCounter(
                (x)->{
                    return 1.0/(Math.exp(x) + Math.exp(-x));
                    /*return (Math.sin(x)*(1 - x) - Math.cos(x)*(1 + x));*/
                },
                a,
                b
        );
        Function<Double, Double> antiderivative = (x) -> (
                Math.sin(x)
                /*(x - 2.0) * Math.cos(x) - (x + 2.0) * Math.sin(x)*/
        );
        Double realIntegralValue = Math.PI/6.0;

        System.out.println("Mistake values table:\n");
        System.out.printf("%" + firstColumnWidth + "s | " +
                "%" + (numberOutputAccuracy + 2) + "s | " +
                "%" + (numberOutputAccuracy + 2) + "s | " +
                "%s | " +
                "%" + (numberOutputAccuracy + 2) + "s | \n",
                "", "N", "2*N", "N/(2*N)", "Improved");

        printRow("Left Rectangles", integral::leftRectangleCalculate, realIntegralValue);
        printRow("Center Rectangles", integral::centerRectangleCalculate, realIntegralValue);
        printRow("Trapezoid", integral::trapezoidCalculate, realIntegralValue);
        printRow("Simpson", integral::SimpsonCalculate, realIntegralValue);
        printRow("Gauss for 2 roots", i -> integral.GaussCalculate(i, 2), realIntegralValue);
        printRow("Gauss for 3 roots", i -> integral.GaussCalculate(i, 3), realIntegralValue);
        //printRow("Gauss for 4 roots", i -> integral.GaussCalculate(i, 4), realIntegralValue);
    }

    static void printRow(String name, Function<Integer, Double> method, double realValue) {
        double resultN = method.apply(intervalsNumber);
        double result2N = method.apply(2 * intervalsNumber);
        double result4N = method.apply(4 * intervalsNumber);
        int accuracyOrder = (int) Math.round(Math.log((resultN - result2N)/(result2N - result4N))/Math.log(2));
        double improved = NetsListCounting.increaseAccuracy(resultN, result2N, accuracyOrder);
        double nError = Math.abs(realValue - resultN);
        double n2Error = Math.abs(realValue - result2N);
        System.out.printf("%" + firstColumnWidth + "s | " +
                "%" + (numberOutputAccuracy + 2) + "." + numberOutputAccuracy + "f | " +
                "%" + (numberOutputAccuracy + 2) + "." + numberOutputAccuracy + "f | " +
                "%7.3f | " +
                "%" + (numberOutputAccuracy + 2) + "." + numberOutputAccuracy + "f | \n",
                name, nError, n2Error, nError/n2Error, Math.abs(realValue - improved));
    }
}

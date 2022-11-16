import java.util.function.Function;

public class Task2 {
    static final int firstColumnWidth = 20,
            numberOutputAccuracy = 25,
            intervalsNumber = 64;

    public static void main(String[] args) {
        IntegralCounter integral = new IntegralCounter(
                (x)->{
                    return Math.cos(x);
                },
                -Math.PI,
                Math.PI/2
        );
        Double realIntegralValue = 1.0;

        System.out.println("Mistake values table:\n");
        System.out.printf("%" + firstColumnWidth + "s | " +
                "%" + (numberOutputAccuracy + 5) + "s | " +
                "%" + (numberOutputAccuracy + 5) + "s | " +
                "%" + (numberOutputAccuracy + 5) + "s | \n",
                "", "N", "2*N", "Improved");

        printRow("Left Rectangles", integral::leftRectangleCalculate, realIntegralValue, 1);
        printRow("Center Rectangles", integral::centerRectangleCalculate, realIntegralValue, 2);
        printRow("Trapezoid", integral::trapezoidCalculate, realIntegralValue, 2);
        printRow("Simpson", integral::SimpsonCalculate, realIntegralValue, 4);
        printRow("Gauss for 2 roots", i -> integral.GaussCalculate(i, 2), realIntegralValue, 4);
        printRow("Gauss for 3 roots", i -> integral.GaussCalculate(i, 3), realIntegralValue, 6);
    }

    static void printRow(String name, Function<Integer, Double> method, double realValue, int accuracyOrder) {
        double resultN = method.apply(intervalsNumber);
        double result2N = method.apply(2 * intervalsNumber);
        double improved = NetsListCounting.increaseAccuracy(resultN, result2N, accuracyOrder);
        System.out.printf("%" + firstColumnWidth + "s | " +
                "%" + (numberOutputAccuracy + 5) + "." + numberOutputAccuracy + "f | " +
                "%" + (numberOutputAccuracy + 5) + "." + numberOutputAccuracy + "f | " +
                "%" + (numberOutputAccuracy + 5) + "." + numberOutputAccuracy + "f | \n",
                name, Math.abs(realValue - resultN), Math.abs(realValue - result2N), Math.abs(realValue - improved));
    }
}

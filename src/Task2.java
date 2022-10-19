public class Task2 {
    public static void main(String[] args) {
        Integer columnsWidth = 25,
                firstColumnWidth = 10,
                numberOutputAccuracy = 20;
        Integer[] accuracies = {
                10, 100, 1000,
        };

        IntegralCounter integral = new IntegralCounter(
                (x)->{
                    return x*x*x + x*x + x + 1;
                },
                0.0,
                1.0
        );
        Double realIntegralValue = 2.0 + 1.0/12.0;

        System.out.println("Mistake values table:\n");
        System.out.printf("%" + firstColumnWidth + "s | " +
                "%" + columnsWidth + "s | " +
                "%" + columnsWidth + "s | " +
                "%" + columnsWidth + "s | \n",
                "", "Left Rectangles", "Center Rectangles", "Trapezoid");

        for (Integer i: accuracies) {
            System.out.printf("%" + firstColumnWidth + "d | " +
                            "%" + columnsWidth + "." + numberOutputAccuracy + "f | " +
                            "%" + columnsWidth + "." + numberOutputAccuracy + "f | " +
                            "%" + columnsWidth + "." + numberOutputAccuracy + "f | \n",
                    i, Math.abs(realIntegralValue - integral.leftRectangleCalculate(i)),
                    Math.abs(realIntegralValue - integral.centerRectangleCalculate(i)),
                    Math.abs(realIntegralValue - integral.trapezoidCalculate(i)));
        }
    }
}

public class Task2 {
    public static void main(String[] args) {
        Integer columnsWidth = 30,
                firstColumnWidth = 10,
                numberOutputAccuracy = 25;
        Integer[] accuracies = {
                10, 100, 1000,
        };

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
                "%" + columnsWidth + "s | " +
                "%" + columnsWidth + "s | " +
                "%" + columnsWidth + "s | " +
                "%" + columnsWidth + "s | " +
                "%" + columnsWidth + "s | \n",
                "", "Left Rectangles", "Center Rectangles", "Trapezoid", "Simpson", "Gauss");

        for (Integer i: accuracies) {
            System.out.printf("%" + firstColumnWidth + "d | " +
                            "%" + columnsWidth + "." + numberOutputAccuracy + "f | " +
                            "%" + columnsWidth + "." + numberOutputAccuracy + "f | " +
                            "%" + columnsWidth + "." + numberOutputAccuracy + "f | " +
                            "%" + columnsWidth + "." + numberOutputAccuracy + "f | " +
                            "%" + columnsWidth + "." + numberOutputAccuracy + "f | \n",
                    i, Math.abs(realIntegralValue - integral.leftRectangleCalculate(i)),
                    Math.abs(realIntegralValue - integral.centerRectangleCalculate(i)),
                    Math.abs(realIntegralValue - integral.trapezoidCalculate(i)),
                    Math.abs(realIntegralValue - integral.SimpsonCalculate(i)),
                    Math.abs(realIntegralValue - integral.GaussCalculate(i)));
        }
    }
}

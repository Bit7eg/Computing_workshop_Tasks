import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Task1 {
    public static PrintWriter outLines;
    public static PrintWriter outPolynomial;
    public static HashMap<Double, Double> XAndDividedDifference;
    public static TreeMap<Double, Double> XtoY = new TreeMap<>(Double::compareTo);

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Scanner inFile;

        System.out.print("Enter filepath for...\n" +
                "\tKnown pairs data: ");
        String Path = in.nextLine();
        try {
            inFile = new Scanner(new File(Path));
        } catch (FileNotFoundException exception) {
            System.err.print("ERROR: File not found\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }

        System.out.print("\n\tLines function values: ");
        Path = in.nextLine();
        try {
            outLines = new PrintWriter("lines.txt");
        } catch (FileNotFoundException exception) {
            System.err.print("ERROR: File not found\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }

        System.out.print("\n\tPolynomial function values: ");
        Path = in.nextLine();
        try {
            outPolynomial = new PrintWriter("polynomial.txt");
        } catch (FileNotFoundException exception) {
            System.err.print("ERROR: File not found\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }

        System.out.print("\nInput data processing...\n");

        Integer N = inFile.nextInt();
        XAndDividedDifference = new HashMap<>(N, 1.0f);
        Double knownX;
        for (Integer i = 0; i <= N; i++) {
            knownX = inFile.nextDouble();
            XtoY.put(knownX, inFile.nextDouble());
            XAndDividedDifference.put(knownX, dividedDifference(XtoY));
        }

        Thread outputDumping = new Thread(()->{
            System.out.print("\nDumping functions data into output files...\n");

            Double start = XtoY.firstKey(),
                    end = XtoY.lastKey(),
                    step = (start - end) / 10;
            start -= step;
            end += step;
            step /= 500;
            for (Double i = start; i <= end; i+= step) {
                outLines.printf("%f %f", i, lineFunctionY(XtoY, i));
                outPolynomial.printf("%f %f", i,
                        polynomialFunctionY(XAndDividedDifference, i));
            }

            System.out.print("\nDumping complete.\n");
        }, "DumpingThread");
        outputDumping.start();

        System.out.print("\n\nEnter argument to calculate: ");
        Double x = in.nextDouble();

        System.out.printf("\n\nLine result: %f\n",
                lineFunctionY(XtoY, x));
        System.out.printf("Polynomial result: %f\n",
                polynomialFunctionY(XAndDividedDifference, x));

        try {
            outputDumping.join();
        } catch (InterruptedException exception) {
            System.err.print("ERROR: " + outputDumping.getName() + " thread interrupted\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }
    }
    private static Double dividedDifference(Map<Double, Double> values) {
        Set<Double> args = values.keySet();
        Double prod, sum = 0.0;
        for (Double i: args) {
            prod = 1.0;
            for (Double j: args) {
                if (!i.equals(j))
                    prod *= i - j;
            }
            sum += values.get(i)/prod;
        }
        return sum;
    }
    private static Double lineFunctionY(TreeMap<Double, Double> values, Double x) {
        Map.Entry<Double, Double> rPair = null,
                lPair = null;
        for (Map.Entry<Double, Double> pair:
                values.entrySet()) {
            if (x < pair.getKey()) {
                rPair = pair;
                break;
            }
        }
        if (rPair == null)
            rPair = values.lowerEntry(x);
        lPair = values.lowerEntry(rPair.getKey());
        if (lPair == null) {
            lPair = rPair;
            rPair = values.higherEntry(lPair.getKey());
        }
        return (x - lPair.getKey()) / (rPair.getKey() - lPair.getKey()) *
                (rPair.getValue() - lPair.getValue()) + lPair.getValue();
    }
    private static Double polynomialFunctionY(Map<Double, Double> values, Double x) {
        Set<Double> args = values.keySet();
        Double prod, sum = 0.0;
        for (Double i: args) {
            prod = 1.0;
            for (Double j: args) {
                if (i.equals(j)) break;
                prod *= x - j;
            }
            sum += values.get(i) * prod;
        }
        return sum;
    }
}
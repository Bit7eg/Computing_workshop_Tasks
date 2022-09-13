import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Task1 {
    public static PrintWriter outLines;
    public static PrintWriter outPolynomial;
    public static PrintWriter outError;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Scanner inFile;

        System.out.print("Enter filepath for...\n" +
                "\tKnown pairs data: ");
        String Path = in.nextLine();
        try {
            inFile = new Scanner(new File(Path));
        } catch (FileNotFoundException exception) {
            System.err.print("ERROR: File not found.\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }

        System.out.print("\n\tLines function values: ");
        Path = in.nextLine();
        try {
            outLines = new PrintWriter(Path);
        } catch (FileNotFoundException exception) {
            System.err.print("ERROR: File not found.\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }

        System.out.print("\n\tPolynomial function values: ");
        Path = in.nextLine();
        try {
            outPolynomial = new PrintWriter(Path);
        } catch (FileNotFoundException exception) {
            System.err.print("ERROR: File not found.\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }

        System.out.print("\n\tInterpolation error values: ");
        Path = in.nextLine();
        try {
            outError = new PrintWriter(Path);
        } catch (FileNotFoundException exception) {
            System.err.print("ERROR: File not found.\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }

        System.out.print("\nInput data processing...\n");

        Integer N = inFile.nextInt();
        if (N < 0) {
            System.err.print("ERROR: Before listing known pairs, " +
                    "index of the last pair must be indicated. " +
                    "The index of the last pair must be at least 0.");
            return;
        }
        Interpolation interpolationObj = new Interpolation(N + 1);
        for (Integer i = 0; i <= N; i++) {
            try {
                interpolationObj.putPair(inFile.nextDouble(), inFile.nextDouble());
            } catch (InputMismatchException exception) {
                System.err.print("ERROR: Not enough pairs.\n" +
                        "SYSTEM_MESSAGE: " + exception.getMessage());
                return;
            }
        }

        Interpolation interpolationCopy;
        try {
            interpolationCopy = interpolationObj.clone();
        } catch (CloneNotSupportedException exception) {
            System.err.print("ERROR: Cloneable not implemented.\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            interpolationCopy = interpolationObj;
        }
        Thread outputDumping = new Thread(()->{
            System.out.print("\nDumping functions data into output files...\n");

            Double start = interpolationObj.getLowerXBound(),
                    end = interpolationObj.getUpperXBound(),
                    step = (start - end) / 10;
            start -= step;
            end += step;
            step /= 500;
            for (Double x = start, value = 0.0, error = 0.0; x <= end; x+= step) {
                outLines.printf("%f %f", x, interpolationObj.lineFunctionY(x));
                value = interpolationObj.polynomialFunctionY(x);
                error = interpolationObj.getError(x);
                outPolynomial.printf("%f %f", x, value);
                outError.printf("%f %f\n%f %f",
                            x, value - error,
                            x, value + error);
            }

            System.out.print("\nDumping complete.\n");
        }, "DumpingThread");
        outputDumping.start();

        System.out.print("\n\nEnter argument to calculate: ");
        Double x = in.nextDouble();

        System.out.printf("\n\nLine result: %f\n",
                interpolationCopy.lineFunctionY(x));
        System.out.printf("Polynomial result: %f\n",
                interpolationCopy.polynomialFunctionY(x));

        try {
            outputDumping.join();
        } catch (InterruptedException exception) {
            System.err.print("ERROR: " + outputDumping.getName() + " thread interrupted\n" +
                    "SYSTEM_MESSAGE: " + exception.getMessage());
            return;
        }

        inFile.close();
        outLines.close();
        outPolynomial.close();
        outError.close();
    }
}
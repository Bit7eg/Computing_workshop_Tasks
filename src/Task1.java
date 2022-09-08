import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Task1 {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args){
        firstSubtask();
    }
    private static void firstSubtask() {
        System.out.printf("Enter number of known pairs: ");
        Integer N = in.nextInt();
        TreeMap<Double, Double> XtoY = new TreeMap<>(Double::compareTo);
        System.out.printf("\nEnter %d known pairs:\n", N);
        for (Integer i = 0; i <= N; i++) {
            XtoY.put(in.nextDouble(), in.nextDouble());
        }
        System.out.printf("\nEnter argument to calculate: ");
        Double x = in.nextDouble();
        Map.Entry<Double, Double> rPair = null,
                lPair = null;
        for (Map.Entry<Double, Double> pair:
             XtoY.entrySet()) {
            if (x < pair.getKey()) {
                rPair = pair;
                break;
            }
        }
        Double y = null;
        try {
            lPair = XtoY.lowerEntry(rPair.getKey());
            y = (x - lPair.getKey()) / (rPair.getKey() - lPair.getKey()) *
                    (rPair.getValue() - lPair.getValue()) + lPair.getValue();
        } catch (NullPointerException e){
            System.out.printf("ERROR: Argument out of range");
            return;
        }
        System.out.printf("\nResult: %50.25e", y);
    }
    private static void secondSubtask() {
        // TODO: add program logic
    }
}
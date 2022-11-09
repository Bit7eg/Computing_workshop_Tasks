public class NetsListCounting {
    public static double increaseAccuracy(double resultN, double result2N, int accuracyOrder) {
        double alpha = 1.0/(1.0 - Math.pow(2.0, accuracyOrder));
        return alpha * resultN + (1 - alpha) * result2N;
    }
}

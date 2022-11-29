public class Task3 {
    static double x2 = -1.5, x1 = -2.5, x0 = 3, eps = 1.0E-10;
    static int N = 15;
    static int rootStringLength = 12, rootAccuracy = 10;
    public static void main(String[] args){
        double a2 = -x2 - x1 - x0,
                a1 = x2*x1 + x2*x0 + x1*x0,
                a0 = -(x2*x1*x0);
        RootFounder dichotomyFounder = RootFounder.dichotomyCalculate(a2, a1, a0, eps, N);
        RootFounder NewtonFounder = RootFounder.NewtonCalculate(a2, a1, a0, eps, N);

        if (rootStringLength < 9) rootStringLength = 9;
        System.out.printf("%" + rootStringLength + "s | %" + rootStringLength + "s", "Dichotomy", "Newton");
        double[] dichotomyRoots = dichotomyFounder.getRoots(),
                NewtonRoots = NewtonFounder.getRoots();

        for (int i = 0; i < 3; i++) {
            System.out.printf("%" + rootStringLength + "." + rootAccuracy + "f | " +
                    "%" + rootStringLength + "." + rootAccuracy + "f", dichotomyRoots[i], NewtonRoots[i]);
        }
    }
}

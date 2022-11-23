public class Task3 {
    static double a2 = 0, a1 = 0, a0 = 0, eps = 0;
    static int N = 0;
    static int rootStringLength = 0, rootAccuracy = 3;
    public static void main(String[] args){
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

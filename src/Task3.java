public class Task3 {
    static double x2 = -150, x1 = 250, x0 = 250.5, eps = 1.0E-5;
    static int N = 20;
    static int stringLength = 20, rootAccuracy = 12;
    public static void main(String[] args){
        double a2 = -x2 - x1 - x0,
                a1 = x2*x1 + x2*x0 + x1*x0,
                a0 = -(x2*x1*x0);

        printTable("Dichotomy", RootFounder.dichotomyCalculate(a2, a1, a0, eps, N));
        printTable("Newton", RootFounder.NewtonCalculate(a2, a1, a0, eps, N));
    }

    public static void printTable(String name, RootFounder founder) {
        if (stringLength < 10) stringLength = 10;

        System.out.println(name + ":\n");
        System.out.printf("\t%" + stringLength + "s | " +
                "%" + stringLength + "s | " +
                "%" + stringLength + "s\n",
                "Root", "Error", "Iterations");

        double[] roots = founder.getRoots(), epsilons = founder.getEps();
        int[] Ns = founder.getN();
        for (int i = 0; i < 3; i++) {
            System.out.printf("\t%" + stringLength + "." + rootAccuracy + "f | " +
                            "%" + stringLength + "." + rootAccuracy + "f | " +
                            "%" + stringLength + "d\n",
                    roots[i], epsilons[i], Ns[i]);
        }

        System.out.println("\n");
    }
}

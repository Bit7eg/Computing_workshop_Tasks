public class RootFounder {
    private double[] a = new double[3];
    private double[] roots = new double[3];
    private double eps;
    private int N;
    private RootFounder(double a2, double a1, double a0, double eps, int N) {
        this.a[0] = a0;
        this.a[1] = a1;
        this.a[2] = a2;
        this.eps = eps;
        this.N = N;
    }

    private static double dichotomyApproximate (double leftEnd, double rightEnd) {

        return 0;
    }

    private static double NewtonApproximate (double leftEnd, double rightEnd) {

        return 0;
    }

    public static RootFounder dichotomyCalculate(double a2, double a1, double a0, double eps, int N) {
        RootFounder founder = new RootFounder(a2, a1, a0, eps, N);



        return founder;
    }

    public static RootFounder NewtonCalculate(double a2, double a1, double a0, double eps, int N) {
        RootFounder founder = new RootFounder(a2, a1, a0, eps, N);



        return founder;
    }

    public double[] getRoots() {
        return this.roots;
    }
}

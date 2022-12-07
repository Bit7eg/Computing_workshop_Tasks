public class RootFounder {
    private double[] a = new double[3];
    private double[] roots = new double[3];
    private double[] eps = new double[3];
    private int[] N = new int[3];
    private RootFounder(double a2, double a1, double a0) {
        this.a[0] = a0;
        this.a[1] = a1;
        this.a[2] = a2;
    }

    private void dichotomyApproximate (double leftEnd, double rightEnd, double eps, int N, int i) {
        double lX = leftEnd, rX = rightEnd;

        int j;
        for (j = 0; (rX - lX > eps) && (j < N); j++) {
            double mX = (rX + lX)/2.0;
            if ((lX*lX*lX + a[2]*lX*lX + a[1]*lX + a[0]) * (mX*mX*mX + a[2]*mX*mX + a[1]*mX + a[0]) > 0)
            {
                lX = mX;
            }
            else rX = mX;
        }

        this.eps[i] = (rX - lX)/2;
        this.N[i] = j;
        this.roots[i] = (rX + lX)/2.0;
    }

    private void NewtonApproximate (double leftEnd, double rightEnd, double eps, int N, int i) {
        double x = (rightEnd + leftEnd)/2.0;
        double lastX;

        if ((6.0 * x + 2.0 * a[2])*(3.0*x*x + 2.0*a[2]*x + a[1]) < 0) lastX = leftEnd;
        else lastX = rightEnd;
        x = lastX - (lastX*lastX*lastX + a[2]*lastX*lastX + a[1]*lastX + a[0])/
                (3.0 * lastX*lastX + 2.0 * a[2]*lastX + a[1]);

        double h = Math.sqrt(a[2]*a[2] - 3.0*a[1]),
                dr1 = (-a[2] - h)/3.0,
                dr2 = (-a[2] + h)/3.0;

        double lX = x - eps, rX = x + eps, m1;
        if (lX < dr1 && rX > dr1 || lX < dr2 && rX > dr2) m1 = 0;
        else if (rX < dr1) m1 = 3.0*rX*rX + 2.0*a[2]*rX + a[1];
        else if (lX > dr2) m1 = 3.0*lX*lX + 2.0*a[2]*lX + a[1];
        else if (lX - dr1 < dr2 - rX) m1 = 3.0*lX*lX + 2.0*a[2]*lX + a[1];
        else m1 = 3.0*rX*rX + 2.0*a[2]*rX + a[1];

        int j;
        for (j = 0; (Math.abs((x*x*x + a[2]*x*x + a[1]*x + a[0])/m1) > eps) && (j < N); j++) {
            lastX = x;
            x = lastX - (lastX*lastX*lastX + a[2]*lastX*lastX + a[1]*lastX + a[0])/
                    (3.0 * lastX*lastX + 2.0 * a[2]*lastX + a[1]);

            lX = x - eps;
            rX = x + eps;
            if (lX < dr1 && rX > dr1 || lX < dr2 && rX > dr2) m1 = 0;
            else if (rX < dr1) m1 = 3.0*rX*rX + 2.0*a[2]*rX + a[1];
            else if (lX > dr2) m1 = 3.0*lX*lX + 2.0*a[2]*lX + a[1];
            else if (lX - dr1 < dr2 - rX) m1 = 3.0*lX*lX + 2.0*a[2]*lX + a[1];
            else m1 = 3.0*rX*rX + 2.0*a[2]*rX + a[1];
        }

        this.eps[i] = Math.abs((x*x*x + a[2]*x*x + a[1]*x + a[0])/m1);
        this.N[i] = j;
        this.roots[i] = x;
    }

    public static RootFounder dichotomyCalculate(double a2, double a1, double a0, double eps, int N) {
        RootFounder founder = new RootFounder(a2, a1, a0);

        double[] l = new double[3], r = new double[3];
        double h = Math.sqrt(a2*a2 - 3.0*a1);
        l[1] = (-a2 - h)/3.0;
        r[1] = (-a2 + h)/3.0;
        r[0] = l[1];
        l[2] = r[1];

        h = -a2/3.0;
        if ((l[1]*l[1]*l[1] + a2*l[1]*l[1] + a1*l[1] + a0) * (h*h*h + a2*h*h + a1*h + a0) > 0) l[1] = h;
        else r[1] = h;

        a2 = Math.abs(a2);
        a1 = -a1;

        if (a2 < 2) a2 = 2;
        if (a1 < 4) a1 = 4;

        h = a2 * Math.sqrt(a1);
        l[0] = r[0] - h;
        r[2] = l[2] + h;

        for (int i = 0; i < 3; i++) {
            founder.dichotomyApproximate(l[i], r[i], eps, N, i);
        }

        return founder;
    }

    public static RootFounder NewtonCalculate(double a2, double a1, double a0, double eps, int N) {
        RootFounder founder = new RootFounder(a2, a1, a0);

        double[] l = new double[3], r = new double[3];
        double h = Math.sqrt(a2*a2 - 3.0*a1);
        l[1] = (-a2 - h)/3.0;
        r[1] = (-a2 + h)/3.0;
        r[0] = l[1];
        l[2] = r[1];

        h = -a2/3.0;
        if ((l[1]*l[1]*l[1] + a2*l[1]*l[1] + a1*l[1] + a0) * (h*h*h + a2*h*h + a1*h + a0) > 0) l[1] = h;
        else r[1] = h;

        a2 = Math.abs(a2);      //TODO: уменьшить интервалы с корнем
        a1 = -a1;

        if (a2 < 2) a2 = 2;
        if (a1 < 4) a1 = 4;

        h = a2 * Math.sqrt(a1);
        l[0] = r[0] - h;
        r[2] = l[2] + h;

        for (int i = 0; i < 3; i++) {
            founder.NewtonApproximate(l[i], r[i], eps, N, i);
        }

        return founder;
    }

    public double[] getRoots() {
        return this.roots;
    }

    public double[] getEps() {
        return eps;
    }

    public int[] getN() {
        return N;
    }
}

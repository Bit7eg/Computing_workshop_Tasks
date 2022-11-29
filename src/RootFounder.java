public class RootFounder {
    private double[] a = new double[3]; //TODO: добавить массивы для границ?
    private double[] roots = new double[3];
    private double eps; //TODO: подумать над итерациями и погрешностью
    private int N;
    private RootFounder(double a2, double a1, double a0, double eps, int N) {   //TODO: перенести вычисление границ в
        this.a[0] = a0;                                                         //      конструктор или сделать
        this.a[1] = a1;                                                         //      отдельную функцию для них
        this.a[2] = a2;
        this.eps = eps;
        this.N = N;
    }

    private double dichotomyApproximate (double leftEnd, double rightEnd) {
        while (rightEnd - leftEnd > eps) {
            double x = (rightEnd + leftEnd)/2.0,
                    middleY = x*x*x + a[2]*x*x + a[1]*x + a[0];
            if (leftEnd * middleY > 0) leftEnd = middleY;
            else rightEnd = middleY;
        }
        return (rightEnd + leftEnd)/2.0;
    }

    private double NewtonApproximate (double leftEnd, double rightEnd) {
        double x = (rightEnd + leftEnd)/2.0;
        double lastX;
        if (6.0 * x + 2.0 * a[2] < 0) lastX = rightEnd;
        else lastX = leftEnd;
        x = lastX - (lastX*lastX*lastX + a[2]*lastX*lastX + a[1]*lastX + a[0])/
                (3.0 * lastX*lastX + 2.0 * a[2]*lastX + a[1]);
        while (Math.abs(x - lastX) < eps) {     //TODO: нормальное условие погрешности
            lastX = x;
            x = lastX - (lastX*lastX*lastX + a[2]*lastX*lastX + a[1]*lastX + a[0])/
                    (3.0 * lastX*lastX + 2.0 * a[2]*lastX + a[1]);
        }
        return x;
    }

    public static RootFounder dichotomyCalculate(double a2, double a1, double a0, double eps, int N) {
        RootFounder founder = new RootFounder(a2, a1, a0, eps, N);

        double[] l = new double[3], r = new double[3];
        double h = Math.sqrt(a2*a2 - 3.0*a1);
        l[1] = (-a2 - h)/3.0;
        r[1] = (-a2 + h)/3.0;
        r[0] = l[1];
        l[2] = r[1];

        h = -a2/3.0;
        if (h*l[1] > 0) l[1] = h;
        else r[1] = h;

        a2 = Math.abs(a2);
        a1 = -a1;

        if (a2 < 2) a2 = 2;
        if (a1 < 4) a1 = 4;

        h = a2 * Math.sqrt(a1);
        l[0] = r[0] - h;
        r[2] = l[2] + h;

        for (int i = 0; i < 3; i++) {
            founder.roots[i] = founder.dichotomyApproximate(l[i], r[i]);
        }

        return founder;
    }

    public static RootFounder NewtonCalculate(double a2, double a1, double a0, double eps, int N) {
        RootFounder founder = new RootFounder(a2, a1, a0, eps, N);

        double[] l = new double[3], r = new double[3];
        double h = Math.sqrt(a2*a2 - 3.0*a1);
        l[1] = (-a2 - h)/3.0;
        r[1] = (-a2 + h)/3.0;
        r[0] = l[1];
        l[2] = r[1];

        h = -a2/3.0;
        if (h*l[1] > 0) l[1] = h;
        else r[1] = h;

        a2 = Math.abs(a2);
        a1 = -a1;

        if (a2 < 2) a2 = 2;
        if (a1 < 4) a1 = 4;

        h = a2 * Math.sqrt(a1);
        l[0] = r[0] - h;
        r[2] = l[2] + h;

        for (int i = 0; i < 3; i++) {
            founder.roots[i] = founder.NewtonApproximate(l[i], r[i]);
        }

        return founder;
    }

    public double[] getRoots() {
        return this.roots;
    }
}

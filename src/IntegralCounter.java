import java.util.function.Function;

public class IntegralCounter {
    private Function<Double, Double> function = (x)->{
        return 1.0;
    };
    private Double a = 0.0;
    private Double b = 1.0;

    public IntegralCounter(Function<Double, Double> f, Double minX, Double maxX) {
        this.function = f;
        this.a = minX;
        this.b = maxX;
    }

    public Double calculate() {
        //TODO: integral calculating
        return 1.0;
    }
}

import java.util.*;

public class Interpolation implements Cloneable {
    private class Pair<K, V> {
        public K first;
        public V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return first.equals(pair.first) && second.equals(pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

    private ArrayList<Pair<Double, Double>> XAndDividedDifference;
    private TreeMap<Double, Double> XtoY = new TreeMap<>(Double::compareTo);

    public Interpolation() {
        XAndDividedDifference = new ArrayList<>();
    }

    public Interpolation(Integer pairsNumber) {
        XAndDividedDifference = new ArrayList<>(pairsNumber);
    }

    public void putPair(Double x, Double y) {
        XtoY.put(x, y);
        XAndDividedDifference.add(new Pair<>(x, dividedDifference()));
    }

    public Integer getPairsCount() {
        return XtoY.size();
    }

    public Double getHigherNode(Double x) {
        return XtoY.higherKey(x);
    }

    public Double getCeilingNode(Double x) {
        return XtoY.ceilingKey(x);
    }

    public Double getLowerNode(Double x) {
        return XtoY.lowerKey(x);
    }

    public Double getFloorNode(Double x) {
        return XtoY.floorKey(x);
    }

    public Double getNodeValue(Double nodeKey) {
        return XtoY.get(nodeKey);
    }

    public void clear() {
        XtoY.clear();
        XAndDividedDifference.clear();
    }

    /**
     * Функция вычисляет значение кусочно-линейной функции для переданного аргумента.
     *
     * @param x аргумент, для которого вычисляется значение функции.
     * @return Значение кусочно-линейной функции.
     */
    public Double lineFunctionY(Double x) {
        Map.Entry<Double, Double> rPair = null,
                lPair = null;
        for (Map.Entry<Double, Double> pair:
                XtoY.entrySet()) {
            if (x < pair.getKey()) {
                rPair = pair;
                break;
            }
        }
        if (rPair == null)
            rPair = XtoY.lowerEntry(x);
        lPair = XtoY.lowerEntry(rPair.getKey());
        if (lPair == null) {
            lPair = rPair;
            rPair = XtoY.higherEntry(lPair.getKey());
        }
        return (x - lPair.getKey()) / (rPair.getKey() - lPair.getKey()) *
                (rPair.getValue() - lPair.getValue()) + lPair.getValue();
    }

    /**
     * Функция вычисляет значение интерполяционного полинома в форме Ньютона для переданного аргумента.
     *
     * @param x аргумент, для которого вычисляется значение полинома.
     * @return Значение интерполяционного полинома.
     */
    public Double polynomialFunctionY(Double x) {
        Double prod = 1.0, sum = 0.0;
        for (Pair<Double, Double> pair: XAndDividedDifference) {
            sum += pair.second * prod;
            prod *= (x - pair.first);
        }
        return sum;
    }

    public Double getLowerXBound() {
        return XtoY.firstKey();
    }

    public Double getLowerYBound() {
        Double min = XtoY.get(XtoY.firstKey());

        for (Double i: XtoY.values()) {
            if (i < min) min = i;
        }

        return min;
    }

    public Double getUpperXBound() {
        return XtoY.lastKey();
    }

    public Double getUpperYBound() {
        Double max = XtoY.get(XtoY.firstKey());

        for (Double i: XtoY.values()) {
            if (i > max) max = i;
        }

        return max;
    }

    public Interpolation clone() throws CloneNotSupportedException {
        Interpolation cloneObj = (Interpolation) super.clone();
        cloneObj.XtoY = (TreeMap<Double, Double>) XtoY.clone();
        cloneObj.XAndDividedDifference =
                (ArrayList<Pair<Double, Double>>) XAndDividedDifference.clone();
        return cloneObj;
    }

    /**
     * Вычисляет разделённую разность, используя все известные узлы интерполяции и соответсвтующие им значения функции.
     *
     * @return Разделённую разность
     */
    private Double dividedDifference() {
        Set<Double> args = XtoY.keySet();
        Double prod, sum = 0.0;
        for (Double i: args) {
            prod = 1.0;
            for (Double j: args) {
                if (!i.equals(j))
                    prod *= (i - j);
            }
            sum += XtoY.get(i)/prod;
        }
        return sum;
    }
}

import java.util.*;

public class Interpolation implements Cloneable {
    private HashMap<Double, Double> XAndDividedDifference;
    private TreeMap<Double, Double> XtoY = new TreeMap<>(Double::compareTo);
    private Double lastX = null;
    private Double leftError = null;
    private Double middleError = null;
    private Double rightError = null;

    public Interpolation() {
        XAndDividedDifference = new HashMap<>();
    }

    public Interpolation(Integer pairsNumber) {
        XAndDividedDifference = new HashMap<>(pairsNumber);
    }

    public void putPair(Double x, Double y) {
        lastX = x;
        XtoY.put(x, y);
        XAndDividedDifference.put(x, dividedDifference());
        leftError = middleError = rightError = null;
    }

    public Integer getPairsCount() {
        return XtoY.size();
    }

    public Double ceilingNode(Double x) {
        return XtoY.ceilingKey(x);
    }

    public Double floorNode(Double x) {
        return XtoY.floorKey(x);
    }

    public void clear() {
        lastX = leftError = middleError = rightError = null;
        XtoY.clear();
        XAndDividedDifference.clear();
    }

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

    public Double polynomialFunctionY(Double x) {
        Set<Double> args = XAndDividedDifference.keySet();
        Double prod = 1.0, sum = 0.0;
        for (Double i: args) {
            sum += XAndDividedDifference.get(i) * prod;
            prod *= x - i;
        }
        return sum;
    }

    public Double getError(Double x) {
        if (middleError == null) countError();
        if (x < XtoY.higherKey(XtoY.firstKey()))
            return leftError;
        if (x > XtoY.lowerKey(XtoY.lastKey()))
            return rightError;
        return middleError;
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

    public Double getCeilingNode(Double x) {
        return XtoY.ceilingKey(x);
    }

    public Double getFloorNode(Double x) {
        return XtoY.floorKey(x);
    }

    public Interpolation clone() throws CloneNotSupportedException {
        Interpolation cloneObj = (Interpolation) super.clone();
        cloneObj.XtoY = (TreeMap<Double, Double>) XtoY.clone();
        cloneObj.XAndDividedDifference =
                (HashMap<Double, Double>) XAndDividedDifference.clone();
        return cloneObj;
    }

    private void countError() {
        rightError = leftError = XAndDividedDifference.get(lastX);
        Double first = XtoY.firstKey(), last = XtoY.lastKey(),
                second = XtoY.higherKey(first), beforeLast = XtoY.lowerKey(last);
        for (Integer i = 0; i < XAndDividedDifference.size(); i++) {
            leftError *= beforeLast - first;
            rightError *= last - second;
        }
        middleError = Double.max(leftError, rightError);
    }

    private Double dividedDifference() {
        Set<Double> args = XtoY.keySet();
        Double prod, sum = 0.0;
        for (Double i: args) {
            prod = 1.0;
            for (Double j: args) {
                if (!i.equals(j))
                    prod *= i - j;
            }
            sum += XtoY.get(i)/prod;
        }
        return sum;
    }
}

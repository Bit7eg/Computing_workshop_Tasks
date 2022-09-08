import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class MathFunction {
    private String infixExpr;
    private String postfixExpr;
    private Map<Character, Integer> operationPriority;

    private void InitPriorities() {
        this.operationPriority = new HashMap<>();
        operationPriority.put('(', 0);
        operationPriority.put('+', 1);
        operationPriority.put('-', 1);
        operationPriority.put('*', 2);
        operationPriority.put('/', 2);
        operationPriority.put('~', 3);
    }

    MathFunction() {
        this.InitPriorities();
        this.infixExpr = "0";
        this.postfixExpr = "0";
    }

    MathFunction(String expression) throws NullPointerException {
        this.InitPriorities();
        this.infixExpr = expression;
        this.postfixExpr = this.ToPostfix(this.infixExpr);
    }

    private String GetStringNumber(String expression, Integer position) {
        String strNumber = "";

        for (;position < expression.length(); position++) {
            Character num = expression.charAt(position);
            if (Character.isDigit(num)) {
                strNumber += num;
            } else {
                position--;
                break;
            }
        }

        return strNumber;
    }

    private String ToPostfix(String infixExpr) throws NullPointerException {
        String postfixExpr = "";
        LinkedList<Character> stack = new LinkedList<>();

        for (Integer i = 0; i < infixExpr.length(); i++) {
            Character ch = infixExpr.charAt(i);
            if (Character.isDigit(ch)) {
                postfixExpr += this.GetStringNumber(infixExpr, i) + " ";
            } else if (ch == '(') {
                stack.addFirst(ch);
            } else if (ch == ')') {
                Character buff = stack.pollFirst();
                while (buff != '(') {
                    postfixExpr += buff;
                    buff = stack.pollFirst();
                }
            } else if (this.operationPriority.containsKey(ch)) {
                if (ch == '-' && (i == 0 || (i > 0 &&
                        this.operationPriority.containsKey(infixExpr.charAt(i-1))))) {
                    ch = '~';
                } else {
                    while (!stack.isEmpty() &&
                            (this.operationPriority.get(stack.peekFirst()) >= this.operationPriority.get(ch))) {
                        postfixExpr += stack.pollFirst();
                    }
                }
                stack.addFirst(ch);
            }
        }
        for (Character op : stack) {
            postfixExpr += op;
        }

        return postfixExpr;
    }

    private Double Execute(Character op, Double first, Double second) throws IllegalArgumentException {
        switch (op) {
            case '+': return first + second;
            case '-': return first - second;
            case '*': return first * second;
            case '/': return first / second;
            default: throw new IllegalArgumentException(
                    "The argument \"op\" must be one of the operators: +, -, *, /");
        }
    }

    public Double Calculate() throws ArithmeticException, IllegalArgumentException {
        LinkedList<Double> intermediateValues = new LinkedList<>();

        for (Integer i = 0; i < this.postfixExpr.length(); i++) {
            Character ch = postfixExpr.charAt(i);
            if (Character.isDigit(ch)) {
                String number = this.GetStringNumber(postfixExpr, i);
                intermediateValues.addFirst(Double.parseDouble(number));
            } else if (this.operationPriority.containsKey(ch)) {
                if (ch == '~') {
                    if (intermediateValues.isEmpty())
                        throw new ArithmeticException("Not enough operands to complete the operation ~");
                    Double last = intermediateValues.pollFirst();
                    intermediateValues.addFirst(this.Execute('-', 0.0, last));
                } else {
                    if (intermediateValues.size() < 2)
                        throw new ArithmeticException("Not enough operands to complete the operation " + ch);
                    Double second = intermediateValues.pollFirst(),
                            first = intermediateValues.pollFirst();
                    intermediateValues.addFirst(this.Execute(ch, first, second));
                }
            }
        }

        return intermediateValues.pollFirst();
    }
}

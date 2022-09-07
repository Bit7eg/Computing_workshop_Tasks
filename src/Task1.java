import java.util.LinkedList;
import java.util.Map;

class MathFunction {
    // TODO: think about using this class from other file
    private String expression;
    MathFunction() {
        this.expression = "0";
    }
    MathFunction(String expression) throws NullPointerException {
        // TODO: rewrite this using https://habr.com/ru/post/596925/
        char ch;
        Character buff = null;
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < expression.length(); i++) {
            ch = expression.charAt(i);
            if (ch <= '9' && ch >= '0') {
                this.expression += ch;
            } else if (ch == '+' || ch == '-') {
                this.expression += " ";
                buff = stack.peekFirst();
                while (buff != null && (buff == '*' || buff == '/')) {
                    this.expression += stack.pollFirst() + " ";
                    buff = stack.peekFirst();
                }
                stack.addFirst(ch);
            } else if (ch == '*' || ch == '/') {
                this.expression += " ";
                stack.addFirst(ch);
            } else if (ch == '(') {
                stack.addFirst(ch);
            } else if (ch == ')') {
                this.expression += " ";
                buff = stack.pollFirst();
                while (buff != '(') {
                    this.expression += buff + " ";
                    buff = stack.pollFirst();
                }
            }
        }
    }
    MathFunction(Map<Double, Double> known_pairs) {
        //TODO: add functional
    }
    public double calculate(double x) {
        //TODO: add calculating
        return x;
    }
}

public class Task1 {
    public static void main(String[] args){
        //TODO: add program logic
    }
}
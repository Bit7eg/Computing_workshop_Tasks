public class Task3 {
    public static void main(String[] args){
        double a = 1;
        double b = a/2;
        while (b != b/2) {
            a = b;
            b /= 2;
        }
        System.out.println(a);
    }
}

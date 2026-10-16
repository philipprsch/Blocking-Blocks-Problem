
import java.text.NumberFormat;
import java.util.Locale;

public class Partitions {
    private static long getNumberOfKLongPartitionsOfN(int n, int k) {
        if (k == 0 && n > 0) return 0;
        if (k == n) return 1;
        return getNumberOfKLongPartitionsOfN(n - 1, k -1 ) + k * getNumberOfKLongPartitionsOfN(n -1, k);
    }
    private static long faculty(int n) {
        if (n == 0) return 1;
        return n * faculty(n - 1);
    }

    public static void main(String[] args) {

//        NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMANY);
//
//        System.out.println(faculty(5));
//
//
//
//
//        System.out.println(getNumberOfKLongPartitionsOfN(4, 3));
//
//        long one = faculty(5)*getNumberOfKLongPartitionsOfN(19, 5);
//        long two = faculty(4)*getNumberOfKLongPartitionsOfN(19, 4);
//        long three = faculty(3)*getNumberOfKLongPartitionsOfN(19, 3);
//        System.out.println(one);
//        System.out.println(two);
//        System.out.println(three);
//        System.out.println(numberFormat.format(one + two + three));
//        System.out.println("6.085.872.616.824");

        for (int i = 0; i < 10000; i++) {
            System.out.println(i + ": " + sum(0.001, i));
        }
    }

    public static double fun(double x, int k) {
        if (0 <= x && x <= (double) 1 /k) return k*k * x;
        if ((double) 1 /k < x && x <= (double) 2 /k) return 2*k - k*k*x;
        return 0;
    }

    public static double sum(double x, int n) {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += fun(x, i);
        }
        return sum;
    }

}

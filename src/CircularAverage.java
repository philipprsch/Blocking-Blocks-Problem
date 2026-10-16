import java.util.Arrays;
import java.util.Random;

public class CircularAverage {

/*    private static double cd(double a, double b) {
        if (a < 0) a = 360 + a;
        if (b < 0 ) b = 360 + b;
        return Math.min(Math.abs(a - b), Math.abs(a- b - 360));
    }*/
    private static double cd(double a, double b) {
        if (a < 0) a = 360 + a;
        if (b < 0 ) b = 360 + b;
        double diff = Math.abs(a - b) % 360;
        return (diff <= 180 ? diff : 360 - diff);
    }
    private static double sumD(double[] values, double angle) {
        double sum = 0.0;
        for (double value : values) {
            sum += cd(angle, value);
        }
        return sum;
    }
    private static double cavgByMinSumD(double[] values) {
        double cavg = 0.0;
        double cavgSumD = Double.MAX_VALUE;
        for (double i = 0.0; i < 360; i++) {
            if (sumD(values, i) < cavgSumD) {
                cavg  = i;
                cavgSumD = sumD(values, i);
            }
        }
        return cavg;
    }
    private static double cavg(double[] values) {
        double x = 0.0;
        double y = 0.0;
        for (double value: values) {
            x += Math.cos(Math.toRadians(value));
            y += Math.sin(Math.toRadians(value));
        }
        return Math.toDegrees(Math.atan2(y, x));
    }
    private static double naturalMean(double[] values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return (sum / values.length);
    }
    public static double[] generateRandomArray(int N, long seed, int b) {
        Random rand = new Random(seed);
        double[] array = new double[N];

        for (int i = 0; i < N; i++) {
            array[i] = (double)rand.nextInt(b);  // Generates number between 0 and b
        }

        return array;
    }
    public static double[] generateConcentratedArray(int N, long seed, int a, int v) {
        Random rand = new Random(seed);
        double[] array = new double[N];

        for (int i = 0; i < N; i++) {
            array[i] = a + rand.nextInt(-v, v);
        }

        return array;
    }
    public static void testCd() throws Exception {
        for (int i = 0; i < 360; i++) {
            for (int j = 0; j < 360; j++) {
                if (cd(i, j) > 180) {
                    throw new Exception(i + ", " + j + " illigal");
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println(cd(355, 5));
        Random random = new Random(360);
        testCd();

        for (int i = 0; i < 25; i++) {
            double[] values = generateRandomArray(20, random.nextLong(), 360);
            double minSumD = cavgByMinSumD(values);
            double cavg = cavg(values);
            //System.out.println("MinSumD: " + minSumD + " Atan: " + String.format("%.1f", cavg) + " Diff: " + String.format("%.1f", cd(cavg, minSumD)) + " Values: " + Arrays.toString(values));
            double nat = naturalMean(values);
            System.out.println("Nat: " + nat + " Atan: " + String.format("%.1f", cavg) + " Diff: " + String.format("%.1f", cd(cavg, nat)) + " MinSumD: " + minSumD + " Values: " + Arrays.toString(values));
        }
        System.out.println("-------Concentrated test----------");
        for (int i = 0; i < 25; i++) {
            double[] values = generateConcentratedArray(20, random.nextLong(), 5, 20);
            double minSumD = cavgByMinSumD(values);
            double cavg = cavg(values);
            System.out.println("Nat: " + naturalMean(values) + " MinSumD: " + minSumD + " Atan: " + String.format("%.1f", cavg) + " Diff: " + String.format("%.1f", cd(cavg, minSumD)) + " Values: " + Arrays.toString(values));
        }
    }
}

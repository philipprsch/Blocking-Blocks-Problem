import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunningAverage {

    public static int normalAverage(int[] values) {
        return (int) Arrays.stream(values).sum() / values.length;
    }
    public static int runningAverage(int[] values) {
        int average = 0;
        int counter = 0;
        for (int value : values) {
            counter++;
            average += ((int)(value - average)) / counter;
        }
        return average;
    }
    public static int[] getRunningAvgWorstCase(int n, int b) {
        return null;
    }
    public static void main(String[] args) {
        Random randSeed = new Random(165);
        int max_diff = 0;
        for (int i = 0; i < 90000; i++) {
            int[] values = generateRandomArray(5000, randSeed.nextLong(), 1000000);
            int normal = normalAverage(values);
            int running = runningAverage(values);
            int diff = Math.abs(normal - running);
            if (diff > max_diff) {
                max_diff = diff;
            }

            //System.out.println( + " " + runningAverage(values));
        }
        System.out.println(max_diff);
    }

    public static int[] generateRandomArray(int N, long seed, int b) {
        Random rand = new Random(seed);
        int[] array = new int[N];

        for (int i = 0; i < N; i++) {
            array[i] = rand.nextInt(b);  // Generates number between 0 and 1023
        }

        return array;
    }
}

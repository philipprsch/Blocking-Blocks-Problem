import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockingBlocksTests {
    public static ArrayList<BlockingBlocks.Field> getRandomFields(int n, int m, int k, long seed) {
        Random random = new Random(seed); // Use seed for reproducibility
        ArrayList<BlockingBlocks.Field> fields = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            int first = random.nextInt(n); // 0 to n-1
            int second = random.nextInt(m); // 0 to m-1
            fields.add(BlockingBlocks.Field.from(first, second));
        }
        return fields;
    }
    static final int TEST_CASES = 50; // Generate X test cases

    static Stream<Arguments> provideRandomTestCases() {
        Random rand = new Random(123); // Seeded for reproducibility
        return Stream.generate(() -> {
            int k = rand.nextInt(10) + 1; // 1 ≤ k ≤ 10
            int n = rand.nextInt(15) + 1; // 1 ≤ n ≤ 100
            int m = rand.nextInt(15) + 1; // 1 ≤ m ≤ 100
            long seed = rand.nextLong(); // Any long value

            return Arguments.of(n, m, k, seed);
        }).limit(TEST_CASES);
    }
    @ParameterizedTest
    @MethodSource("provideRandomTestCases")
    public void testBlockingBLocks(int n, int m, int k, long seed) {
        System.out.println("Testing with random  field seed: " + seed + ", n: " + n + ", m: "+m + ", k: "+k);
        ArrayList<BlockingBlocks.Field> obstacles = getRandomFields(n, m, k, seed);
        BlockingBlocks game = new BlockingBlocks(n, m);
        System.out.println("Obstacles: ");
        for (BlockingBlocks.Field obst : obstacles) {
            System.out.println(obst);
            game.placeObstacle(obst);
        }

        //assertEquals(game.traverse(), game.countFreePaths());
        assertEquals(game.countFreePaths(), game.countFreePathsAlt());
    }
}

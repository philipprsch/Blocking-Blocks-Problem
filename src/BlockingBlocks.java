import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BlockingBlocks {

    private boolean[][] grid;
    private final List<Field> obstacles;

    private final int n;
    private final int m;

    private final Field lowerLeft;
    private final Field upperRight;

    public BlockingBlocks(int n, int m) {
        this.n = n;
        this.m = m;
        this.lowerLeft = new Field(0, 0);
        this.upperRight = new Field(n - 1, m - 1);
        grid = new boolean[n][m];
        obstacles = new ArrayList<>();
    }
    public static class Field {
        public int x;
        public int y;
        public Field(int x, int y) {
            this.y = y;
            this.x = x;
        }
        public static Field from(int xa, int ya) {
            return new Field(xa, ya);
        }
        public boolean equals(Field other) {
            return this.x == other.x && this.y == other.y;
        }
        public String toString() {
            return "Field x: " + x + ", y: " + y;
        }
    }
    public static long binomial(int n, int k) {
        if (k > n) return 0;
        if (k == 0 || k == n) return 1;

        long res = 1;
        k = Math.min(k, n - k); // Use property C(n, k) == C(n, n-k)

        for (int i = 0; i < k; i++) {
            res *= (n - i);
            res /= (i + 1);
        }

        return res;
    }

    public void placeObstacle(int x, int y) {
        placeObstacle(Field.from(x, y));
    }
    public void placeObstacle(Field obst) {
        if (this.grid[obst.x][obst.y]) return; //Already placed
        this.grid[obst.x][obst.y] = true;
        this.obstacles.add(obst);
        obstacles.sort(Comparator.comparingInt((Field a) -> a.x).thenComparing(Comparator.comparingInt((Field a)-> a.y)));
    }
    public long countPathsFromTo(Field from, Field to) {
        if (to.x - from.x < 0 || to.y - from.y < 0) return 0;
        return binomial(to.x - from.x + to.y - from.y, to.x - from.x);
    }
    //List must already be sorted, such that for each two fields in the list, they appear in the order they would be visited
    public long countPathsPassingThrough(List<Field> list) {
        list.addLast(upperRight);
        long res = list.stream().reduce(new Helpers.Pair<Long, Field>(1L, lowerLeft),
                (acc, currField) -> {
                    //if (acc.b.equals(lowerLeft)) return new Helpers.Pair<>(1L, lowerLeft);
                    long newA = acc.a * countPathsFromTo(acc.b, currField);
                    return new Helpers.Pair<>(newA, currField);
                }, (acc0, acc1) -> new Helpers.Pair<>(acc0.a* acc1.a, null)).a;
        return res;
    }
    public long countFreePaths() {
        long blockedPaths = 0;
        for (int i = 1; i <= obstacles.size(); i++) {
            List<List<Field>> iObstacleSubsets = Helpers.getKSubsets(obstacles, i);
            long iObstaclePaths = iObstacleSubsets.stream().mapToLong(this::countPathsPassingThrough).sum();
            if (i % 2 == 0) iObstaclePaths *= -1;
            blockedPaths += iObstaclePaths;
        }
        return binomial(n-1 + m -1, m -1) - blockedPaths;
    }
    public long countFreePathsAlt() {
        List<List<Field>> obstacleSubsets = Helpers.powerSetIter(obstacles, false);
        long blockedPaths = obstacleSubsets.stream().mapToLong(A -> (A.size() % 2 == 0 ? -1 : 1) * countPathsPassingThrough(A)).sum();
        return binomial(n-1 + m -1, m -1) - blockedPaths;
    }
    public int traverse(Field from, Field to) {
        int newTotalWays = 0;
        if (from.equals(to)) { //Reached upper right corner / destination
            return 1;
        }
        if (from.x < n - 1 && !grid[from.x+1][from.y]) { //Right is clear
            newTotalWays += traverse(Field.from(from.x + 1, from.y), to);
        }
        if (from.y < m - 1 && !grid[from.x][from.y+1]) { //Up is clear
            newTotalWays += traverse(Field.from(from.x, from.y + 1), to);
        }
        return newTotalWays;
    }
    public int traverse() {
        return traverse(lowerLeft, upperRight);
    }
    public static void main(String[] args) {
//        BlockingBlocks game = new BlockingBlocks(8, 5);
//        game.placeObstacle(6, 1);
//        game.placeObstacle(4, 0);
//        System.out.println(game.traverse());
//        System.out.println(game.countFreePaths());

        BlockingBlocks game = new BlockingBlocks(5, 11);

        //ArrayList<Field> obstacles = BlockingBlocksTests.getRandomFields(6, 12, 3, 5563054578755686678L);
        //ArrayList<Field> obstacles = new ArrayList<>();
        //obstacles.add(new Field(5, 11));
        ArrayList<Field> obstacles = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            if (i == 5) continue;
//            game.placeObstacle(i, i+ 5);
//        }
        for (int i = 0; i < 5; i++) {
            if (i == 4) continue;
            game.placeObstacle(i, 5);
        }
        for (Field obst : obstacles) {
            System.out.println(obst);
            game.placeObstacle(obst);
        }
        System.out.println(game.traverse());
        System.out.println(game.countFreePaths());

        //System.out.println(binomial(game.n-1 + game.m -1, game.m-1) - binomial(6 + 1, 1)*binomial(3 + 1, 1));
    }
}

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Helpers {
    public static class Pair<A, B> {
        public final A a;
        public final B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }
    public static <T> List<List<T>> powerSetIter(List<T> inputSet, boolean includeEmpty) {
        List<List<T>> powerSet =  new LinkedList<>();
        for (long p = (includeEmpty ? 0L : 1L);  p < Math.pow(2, inputSet.size()); p++) {
            LinkedList<T> curSubset = new LinkedList<T>();
            for (Integer pos : getPositionsOfOnes(p)) {
                curSubset.add(inputSet.get(pos));
            }
            powerSet.add(curSubset);
        }
        return powerSet;
    }
    public static LinkedList<Integer> getPositionsOfOnes(long number) {
        LinkedList<Integer> positions = new LinkedList<>();
        int position = 0;

        while (number != 0) {
            // Check if the least significant bit is 1
            if ((number & 1) == 1) {
                positions.add(position);
            }

            // Right shift the number and increment the position
            number >>= 1;
            position++;
        }

        return positions;
    }

    public static <T> List<List<T>> getKSubsets(List<T> set, int k) {
        List<List<T>> result = new ArrayList<>();
        generateSubsets(set, k, 0, new ArrayList<>(), result);
        return result;
    }

    private static <T> void generateSubsets(List<T> set, int k, int start, List<T> current, List<List<T>> result) {
        if (current.size() == k) {  // Wenn `k` Elemente gewählt wurden
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < set.size(); i++) {
            current.add(set.get(i));  // Element hinzufügen
            generateSubsets(set, k, i + 1, current, result); // Rekursiv weiter
            current.remove(current.size() - 1);  // Backtrack (letztes Element entfernen)
        }
    }
}

public class DoubleNumberProblem {
    public static void main(String[] args) {

    }
    private boolean isASolution(int num) {
        return false;
    }
    private static int getNthDigitFromLeft(int number, int n) {
        String numStr = Integer.toString(Math.abs(number));
        if (n > numStr.length() || n < 1) {
            throw new IllegalArgumentException("Invalid digit position");
        }
        return Character.getNumericValue(numStr.charAt(n - 1));
    }
}

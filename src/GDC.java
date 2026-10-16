public class GDC {
    public static int gdc(int x, int y) {
        int r = x + y;
        while (x != y) {
            if (x < y) {
                y = y -x;
            } else {
                x = x - y;
            }
            r = x + y;
        }
        return x;
    }

    public static void main(String[] args) {
        int result = gdc(35, 10);
    }
}

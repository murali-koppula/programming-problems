
import java.util.*;
import java.util.stream.*;
import java.io.*;

public class Colorful {
    private static final Scanner scanner = new Scanner(System.in);

    static boolean isColorful(int n) {
        n = Math.abs(n);
        HashMap<Integer, Integer> products = new HashMap<Integer, Integer>();
        boolean colorful = true;
        String s = String.valueOf(n);

        for (int i = 0; i < s.length() && colorful; i++) {
            for (int j = i; j < s.length() && colorful; j++) {
                String ss = s.substring(i, j+1);
                int p = ss.chars().reduce(1, (product, k) -> product * (k-(int)'0'));

                if (products.get(p) == null) {
                    products.put(p, Integer.valueOf(ss));
                } else {
                    colorful = false;
                }
            }
        }

        return colorful;
    }

    public static void main(String[] args) {
        System.out.printf("Given Number : ");
        int n = scanner.nextInt();
        System.out.printf("Output :%s Colorful.\n", isColorful(n) ? "" : " Not");
    }
}


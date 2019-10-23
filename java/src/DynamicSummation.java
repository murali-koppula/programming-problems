
import java.util.*;
import java.io.*;

public class DynamicSummation {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

        Map<Integer> edges = new HashMap<Integer>();

        IntStream.range(0,n-1).forEach(i -> {
            String[] sarr = scanner.readLine().split(" ");
            edges.put(Integer.valueOf(sarr[0]), Integer.valueOf(sarr[1]));
        });

        IntStream.range(0,n-1).forEach(i -> {
            String[] sarr = scanner.readLine().split(" ");
            edges.put(Integer.valueOf(sarr[0]), Integer.valueOf(sarr[1]));
        });

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

        for (int i = 0; i < q; i++) {
            String[] sarr = scanner.nextLine().split(" ")
            int troot = Integer.parseInt(sarr[1]);
            int stroot = Integer.parseInt(sarr[2]);

            if ("U".equals(sarr[0])) {
                int a = Integer.parseInt(sarr[3]);
                int b = Integer.parseInt(sarr[4]);
            } else if ("R".equals(sarr[0])) {
                int m = Integer.parseInt(sarr[3]);
            }
        }
    }
}


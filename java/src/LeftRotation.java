
import java.util.*;
import java.util.stream.*;
import java.io.*;

public class LeftRotation {

    private static final Scanner scanner = new Scanner(System.in);

    static int[] rotLeft(int[] a, int d) {
        int n = a.length;
        if (d < n) {
            int[] tempArray = new int[d];

            for (int i = 0; i < d; i++) {
                tempArray[i] = a[i];
            }

            for (int i = 0; i < n-d; i++) {
                a[i] = a[i+d];
            }

            for (int i = 0; i < d; i++) {
                a[n-d+i] = tempArray[i];
            }
        }
        return(a);
    }

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();

        scanner.skip("(\r\n|[\n\r\u0085\u2028\u2029])?");

        int d = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u0085\u2028\u2029])?");

        String[] astr = scanner.nextLine().split(" ");
        int[] a = Arrays.stream(astr).mapToInt(s -> Integer.parseInt(s)).toArray();

        int[] result = rotLeft(a, d);

        // for (int i : result) {
        //     System.out.printf("%d ", i);
        // }

        Arrays.stream(result).forEach(i -> System.out.printf("%d ", i));
        System.out.println();

        BufferedWriter bw = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        for (int i : result) {
            bw.write(String.valueOf(i) + " ");
        }
        bw.newLine();
        bw.close();
    }
}


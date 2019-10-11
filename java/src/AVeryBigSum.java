
import java.util.*;
import java.io.*;

public class AVeryBigSum {
    private static final Scanner scanner = new Scanner(System.in);

    // Complete the aVeryBigSum function below.
    static long aVeryBigSum(long[] ar) {
        long veryBigSum = 0;

        for (int i = 0; i < ar.length; i++) {
            veryBigSum += ar[i];
        }

        return veryBigSum;
    }

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();

        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        long[] ar = new long[n];

        String[] arStr = scanner.nextLine().split(" ");

        for (int i = 0; i < n; i++) {
            ar[i] = Long.parseLong(arStr[i]);
        }

        long result = aVeryBigSum(ar);

        System.out.printf("%d\n", result);

        /*
        BufferedWriter bw = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        bw.write(String.valueOf(result));
        bw.newLine();
        bw.close();
        */

        scanner.close();
    }
}


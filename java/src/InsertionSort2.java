
import java.util.*;
import java.io.*;

public class InsertionSort2 {
    private static final Scanner scanner = new Scanner(System.in);

    static void insertionSort(int n, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];

            for (int j = i; j > 0; j--) {
                if (arr[j-1] <= val) {
                    arr[j] = val;
                    break;
                }
                arr[j] = arr[j-1];
            }

            if (arr[0] > val)
                arr[0] = val;

            if (i > 0) {
                Arrays.stream(arr).forEach(p -> System.out.printf("%d ", p));
                System.out.printf("\n");
            }
        }
    }

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

        String[] sarr = scanner.nextLine().split(" ");
        int[] arr = Arrays.stream(sarr).mapToInt(s -> Integer.parseInt(s)).toArray();

        insertionSort(n, arr);
    }
}


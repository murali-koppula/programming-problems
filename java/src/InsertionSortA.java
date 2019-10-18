
import java.util.*;
import java.io.*;

/*
 * Reference:
 * https://www.hackerrank.com/challenges/insertionsort1/problem
*/

public class InsertionSortA {
    private static final Scanner scanner = new Scanner(System.in);

    static void insertionSort1(int n, int[] arr) {
        if (arr.length == 0)
            return;

        int pval = arr[arr.length-1];

        for (int i = arr.length-1; i > 0; i--) {
            if (arr[i-1] <= pval) {
                arr[i] = pval;
                break;
            }

            arr[i] = arr[i-1];

            Arrays.stream(arr).forEach(j -> System.out.printf("%d ", j));
            System.out.printf("\n");
        }

        if (arr[0] > pval)
            arr[9] = pval;

        Arrays.stream(arr).forEach(j -> System.out.printf("%d ", j));
        System.out.printf("\n");
    }

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");
        String[] sarr = scanner.nextLine().split(" ");
        int[] arr = Arrays.stream(sarr).mapToInt(s -> Integer.parseInt(s)).toArray();

        insertionSort1(n, arr);
    }
}


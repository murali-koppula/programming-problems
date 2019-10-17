
import java.util.*;
import java.util.stream.*;
import java.io.*;

public class IceCream {
    private static final Scanner scanner = new Scanner(System.in);

    public static int[] icecreamParlor(int m, int[] arr) {
        ArrayList<Integer> al = new ArrayList<Integer>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if ((arr[i] + arr[j]) == m) {
                    al.add(i+1);
                    al.add(j+1);
                }
            }
            if (al.size() > 0)
                break;
        }
        int[] result = al.stream().mapToInt(o -> o.intValue()).toArray();
        return result;
    }

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

        Stream<ArrayList<Integer>> oarr = IntStream.range(0,t).mapToObj(i -> {
            int m = scanner.nextInt();
            int n = scanner.nextInt();

            scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

            String[] sarr = scanner.nextLine().split(" ");
            int[] arr = Arrays.stream(sarr).mapToInt(Integer::parseInt).toArray();

            int[] result = icecreamParlor(m, arr);

            ArrayList<Integer> al = Arrays.stream(result).collect(
                ArrayList<Integer>::new,
                (list, j) -> list.add(Integer.valueOf(j)),
                (list1,list2) -> list1.addAll(list2));

            return (ArrayList<Integer>)al;
        });

        oarr.forEach(al -> {
            ((ArrayList<Integer>)al).stream().forEach(i -> System.out.printf("%d ", i));
            System.out.printf("\n");
        });
    }
}



import java.math.BigInteger;
import java.util.*;

class LongExp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

        for (int i=0; i < n; i++) {
            String[] sarr = scanner.nextLine().split(" ");
            String sa = sarr[0];
            String sb = sarr[1];
            String sm = sarr[2];

            BigInteger a = new BigInteger(sa);
            BigInteger b = new BigInteger(sb);
            BigInteger m = new BigInteger(sm);

            BigInteger result = a.pow(b.intValue()).add(a.add(BigInteger.ONE).pow(b.intValue())).add(b.add(BigInteger.ONE).pow(a.intValue()));
            // System.out.println(a.modPow(b,m));
        }
    }
}


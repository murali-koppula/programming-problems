
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class BalancedBrackets {
    private static final Scanner scanner = new Scanner(System.in);

    static String isBalanced(String s) {
        final Map<Character, Character> tokenMap =  new HashMap<Character,Character>();
        tokenMap.put('(',')');
        tokenMap.put('[',']');
        tokenMap.put('{','}');

        ArrayList<Character> bracketStack = new ArrayList<Character>();

        char[] carr = s.toCharArray();
        boolean balanced = true;

        for (char c : carr) {
            Character cobj = Character.valueOf(c);
            Character mobj = tokenMap.get(cobj);

            if ((mobj != null)) {
                bracketStack.add(mobj);

            } else if (tokenMap.containsValue(cobj)) {
                int size = bracketStack.size();

                if ((size <= 0) || (c != bracketStack.remove(size-1).charValue())) {
                    balanced = false;
                    break;
                }
            }
        }

        if (! bracketStack.isEmpty())
            balanced = false;

        return (balanced ? "YES" : "NO");
    }

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        Object[] oarr = IntStream.range(0,n).mapToObj(i -> {
            String str = scanner.nextLine();
            return isBalanced(str);
        }).toArray();

        Arrays.stream(oarr).forEach(s -> System.out.println((String)s));
    }
}


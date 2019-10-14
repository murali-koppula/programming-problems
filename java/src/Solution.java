
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Complete the isBalanced function below.
    static String isBalanced(String s) {
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

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            String s = scanner.nextLine();

            String result = isBalanced(s);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}


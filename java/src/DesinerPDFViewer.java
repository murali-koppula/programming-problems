
import java.util.*;
import java.util.stream.*;
import java.io.*;

public class DesinerPDFViewer {
    private static final Scanner scanner = new Scanner(System.in);

    static int designerPdfViewer(int[] h, String word) {
        /*
        int result = word.chars().reduce(0, (maxHeight, i) -> {
                int h = h[(int)i - (int)'a'];
                maxHeight = h > maxHeight ? h : maxHeight;
                return maxHeight;
            }) * word.length();
        */

        int result = word.chars().reduce(0, (maxHeight, i) ->
                h[(int)i - (int)'a'] > maxHeight ?
                h[(int)i - (int)'a'] : maxHeight) * word.length();

        return(result);
    }

    public static void main(String[] args) throws IOException {
        String[] chars = scanner.nextLine().split(" ");
        int[] charHeights = Arrays.stream(chars).mapToInt(s -> Integer.parseInt(s)).toArray();

        scanner.skip("(\r\n|[\n\r\u0085\u2028\u2029])?");
        String word = scanner.next();

        int result = designerPdfViewer(charHeights, word);

        System.out.printf("%d\n", result);

        BufferedWriter bw = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        bw.write(String.valueOf(result));
        bw.newLine();
        bw.close();

        scanner.close();
    }
}


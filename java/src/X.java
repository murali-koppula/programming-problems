
import java.io.*;

class X {
    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new PrintWriter(System.out));
        bw.write("one");
        bw.newLine();
        bw.flush();

        PrintWriter pw = new PrintWriter(System.out);
        pw.write("one");
        pw.println();
        pw.flush();
    }
}


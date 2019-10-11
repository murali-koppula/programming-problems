
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CycleDetection {
    private static final Scanner scanner = new Scanner(System.in);

    static class SinglyLinkedListNode {
        private int data;
        public SinglyLinkedListNode next;

        public SinglyLinkedListNode(int data) {
            this.data = data;
        }

        public void print(PrintWriter pw) throws IOException {
            pw.printf("%d", this.data);
        }
    }

    static class SinglyLinkedList {
        public SinglyLinkedListNode head;
        public SinglyLinkedListNode tail;

        public void makeCycle(int noderef) {
            int i = 0;
            for (SinglyLinkedListNode node = this.head; node != null; node = node.next) {
                if (i == noderef) {
                    this.tail.next = node;
                    this.tail = node;
                    break;
                }
                i++;
            }
        }

        public void insertNode(int data) {
            SinglyLinkedListNode node = new SinglyLinkedListNode(data);

            if (this.head == null)
                this.head = node;
            else
                this.tail.next = node;

            this.tail = node;
        }

        public void print(char sep, PrintWriter pw) throws IOException {
            ArrayList<SinglyLinkedListNode> al = new ArrayList<SinglyLinkedListNode>();

            for (SinglyLinkedListNode node = this.head; node != null; node = node.next) {
                if (al.indexOf(node) > -1) {
                    pw.printf("<%d>", al.indexOf(node));
                    break;
                }

                if (node != this.head)
                    pw.print(sep);
                node.print(pw);

                al.add(node);
            }
            pw.println();
            pw.flush();
        }

        public void print(String outputPath) throws IOException {
            print(' ', new PrintWriter(new BufferedWriter(new FileWriter(outputPath))));
        }

        public void print() throws IOException {
            print(' ', new PrintWriter(System.out, true));
        }
    }

    static boolean hasCycle(SinglyLinkedListNode head) {
        ArrayList<SinglyLinkedListNode> al = new ArrayList<SinglyLinkedListNode>();

        boolean found = false;
        for (SinglyLinkedListNode node = head; node != null; node = node.next) {

            if (al.indexOf(node) > -1) {
                found = true;
                break;
            }
            al.add(node);
        }

        return found;
    }

    public static void main(String[] args) throws IOException {
        String[] str = scanner.nextLine().split(" ");
        if (str.length == 0 || "".equals(str[0]))
            System.exit(0);

        SinglyLinkedList llist = new SinglyLinkedList();
        IntStream.range(0, str.length - 1).forEach(i -> llist.insertNode(Integer.parseInt(str[i])));

        if (! str[str.length-1].equals("."))
            llist.makeCycle(Integer.parseInt(str[str.length - 1]));

        System.out.println(hasCycle(llist.head));
        llist.print();
    }
}


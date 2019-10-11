
import java.util.*;
import java.io.*;

public class LinkedListSolution {

    static class SinglyLinkedListNode {
        private int data;
        public SinglyLinkedListNode next;

        public SinglyLinkedListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static class SinglyLinkedList {
        public SinglyLinkedListNode head;
        public SinglyLinkedListNode tail;

        public SinglyLinkedList() {
            this.head = null;
            this.tail = null;
        }

        public void insertNode(int data) {
            SinglyLinkedListNode node = new SinglyLinkedListNode(data);

            if (this.head == null) {
                this.head = node;
            } else {
                this.tail.next = node;
            }

            this.tail = node;
        }
    }

    static void printSinglyLinkedList(SinglyLinkedListNode node, String sep, BufferedWriter bw)
                                      throws IOException {
        for (; node != null; node = node.next) {
            bw.write(String.valueOf(node.data));
            if (node.next != null)
                bw.write(sep);
        }
        bw.newLine();
        bw.flush();
    }

    static SinglyLinkedListNode insertNodeAtPosition(SinglyLinkedListNode head, int data, int position) {
        if (head == null) {
            SinglyLinkedList list = new SinglyLinkedList();
            list.insertNode(data);
            head = list.head;
        } else {
            SinglyLinkedListNode node = new SinglyLinkedListNode(data);
            SinglyLinkedListNode ptr = head;
            SinglyLinkedListNode pptr = null;
            int ptrnum = 0;

            for (ptrnum = 0; ptr != null && ptr.next != null; ptrnum++) {
                if (ptrnum == position)
                    break;

                pptr = ptr;
                ptr = ptr.next;
            }

            if (ptrnum == 0) {
                head = node;
            } else {
                pptr.next = node;
            }

            node.next = ptr;
        }

        return head;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        int data;
        int position;
        BufferedWriter bw = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter ow = new BufferedWriter(new PrintWriter(System.out));

        SinglyLinkedList list = new SinglyLinkedList();

        for (int i = 0; i < n; i++) {
            scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");
            data = scanner.nextInt();

            list.insertNode(data);
        }

        SinglyLinkedListNode head = list.head;

        printSinglyLinkedList(head, " ", bw);
        printSinglyLinkedList(head, " ", ow);

        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");
        data = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");
        position = scanner.nextInt();

        head = insertNodeAtPosition(head, data, position);

        printSinglyLinkedList(head, " ", bw);
        printSinglyLinkedList(head, " ", ow);

        bw.close();
        scanner.close();
    }
}


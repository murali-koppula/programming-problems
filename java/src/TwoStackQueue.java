
import java.util.*;
import java.io.*;

public class TwoStackQueue {

    public static void main(String[] args) {
        class SQueue {
            class QStack {
                class QNode {
                    private int data;
                    public QNode next;

                    public QNode(int data) {
                        this.data = data;
                    }
                }

                private QNode top;

                public void push(int data) {
                    QNode qnode = new QNode(data);
                    qnode.next = this.top;
                    this.top = qnode;
                }

                public Integer pop() {
                    Integer obj = null;

                    if (this.top != null) {
                        obj = new Integer(this.top.data);
                        this.top = this.top.next;
                    }

                    return obj;
                }

                public Integer peek() {
                    Integer obj = null;

                    if (this.top != null)
                        obj = new Integer(this.top.data);

                    return obj;
                }

                public boolean isEmpty() {
                    return (this.top == null);
                }
            }

            private QStack head, tail;

            private void move(QStack s1, QStack s2) {
                while (s1.peek() != null)
                    s2.push(s1.pop());
            }

            public SQueue() {
                head = new QStack();
                tail = new QStack();
            }

            public void enqueue(Integer obj) {
                head.push(obj);
            }

            public void dequeue() {
                if (tail.isEmpty())
                    move(head, tail);

                tail.pop();
            }

            public Integer peek() {
                if (tail.isEmpty())
                    move(head, tail);

                return tail.peek();
            }
        }

        final Scanner scanner = new Scanner(System.in);
        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

        SQueue sQueue = new SQueue();

        ArrayList<Integer> al = new ArrayList<Integer>();

        for (int i = 0; i < q; i++) {
            int cmd = scanner.nextInt();
            scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

            if (cmd == 1) {
                int qarg = scanner.nextInt();
                scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

                sQueue.enqueue(qarg);

            } else if (cmd == 2) {
                sQueue.dequeue();

            } else if (cmd == 3) {
                if (sQueue.peek() != null)
                    al.add(sQueue.peek());
            }
        }

        al.stream().forEach(i -> System.out.printf("%d\n", i));
    }
}


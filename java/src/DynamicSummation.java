
import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.math.BigInteger;

public class DynamicSummation {
    private static final Scanner scanner = new Scanner(System.in);

    static TreeNode[] tl;
    static HashMap<List<Long>, List<Long>> updates;
    static HashMap<Integer, HashMap<Integer, ArrayList<List<Long>>>> tmap;
    static HashMap<Integer, HashSet<Integer>> rmap;

    // mmap is used to cache modpow() results to avoid recalculation.
    static HashMap<List<Long>, HashMap<Integer, Integer>> mmap =
           new HashMap<List<Long>, HashMap<Integer, Integer>>();

    // Each node correspond to r or t of an update (U), report (R) operation, or a branch of
    // a node U operation. The subtree cardinalities are stored in U operations.
    // ststack is re-created at each R operation.
    //
    static ArrayList<STNode> ststack = null;

    static class STNode {
        public int id; // TQueue.id
        public int parent; // ststack index corresponding to the previous TQueue instance
        public int sz; // cardinality of the subtree at this node
        public HashMap<Integer, Integer> bmap; // {r, bidx} where bidx is the ststack
                                               // index of the branch of t leading to r.
                                               // bmap entry is null if r came before t.
        public STNode (int id, int parent) {
            this.id = id;
            this.parent = parent;
        }

        public String toString() {
            return String.format("(%d <- %d, {%s}, <%d>)",
                                 parent, id, bmap == null ? "null" :
                                 bmap.entrySet().stream().map(e -> "(" + e.getKey() +
                                 ":" + e.getValue() + ")").reduce("", (cstr, s) -> cstr.concat(s + ",")), sz);
        }
    }

    static class TQNode {
        public int id;
        public TQNode next;

        public TQNode(int id) {
            this.id = id;
        }
    }

    static class TQueue {
        TQNode head;
        TQNode tail;

        public int id; // TQueue.id is r, t, ..
        public TQueue next;
        public int uidx; // ststack index corresponding to this TQueue instance.

        public TQueue(int id, int uidx) {
            this.id = id;
            this.uidx = uidx;
        }

        public void enqueue(TQNode tqnode) {
            if (head == null)
                head = tqnode;

            if (tail != null)
                tail.next = tqnode;

            tail = tqnode;
            tail.next = null;
        }

        public TQNode dequeue() {
            TQNode tqnode = head;

            if (head != null) {
                head = head.next;
                if (head == null)
                    tail = null;
            }

            return tqnode;
        }

        public boolean isEmpty() {
            return (head == null);
        }
    }

    static class QQueue {
        TQueue head;
        TQueue tail;

        public void enqueue(TQueue tqueue) {
            if (head == null)
                head = tqueue;

            if (tail != null)
                tail.next = tqueue;

            tail = tqueue;
            tail.next = null;
        }

        public TQueue dequeue() {
            TQueue tqueue = head;

            if (head != null) {
                head = head.next;
                if (head == null)
                    tail = null;
            }

            return tqueue;
        }

        public boolean isEmpty() {
            return (head == null);
        }
    }

    static class TreeNode {
        public int id;
        public int parent;
        public ArrayList<TreeNode> neighbors = new ArrayList<TreeNode>();

        public TreeNode(int id) {
            this.id = id;
        }
    }

    static int report(int r, int t, int m) {
        TQueue tqueue = new TQueue(r, -1);
        TQNode tqnode = new TQNode(r);
        tqueue.enqueue(tqnode);

        tl[r-1].parent = 0;

        while (!tqueue.isEmpty()) {
            tqnode = tqueue.dequeue();

            if (tqnode.id == t)
                break;

            TreeNode tnode = tl[tqnode.id-1];

            for (TreeNode neighbor : tnode.neighbors) {
                if (neighbor.id == tnode.parent)
                    continue;

                neighbor.parent = tnode.id;
                tqueue.enqueue(new TQNode(neighbor.id));
            }
        }

        if (tqnode == null) {
            System.out.printf("ERROR: invalid values: r: %d, t: %d.\n", r, t);
            System.exit(1);
        }

        tqueue = new TQueue(t, -1);
        tqueue.enqueue(tqnode);

        QQueue qqueue = new QQueue();
        qqueue.enqueue(tqueue);

        ststack = new ArrayList<STNode>();

        while (!qqueue.isEmpty()) {
            tqueue = qqueue.dequeue();

            int uidx = ststack.size();
            STNode stnode = new STNode(tqueue.id, tqueue.uidx);

            while (!tqueue.isEmpty()) {
                tqnode = tqueue.dequeue();
                TreeNode tnode = tl[tqnode.id-1];

                if ((tnode.id != tqueue.id) && (tmap.get(tnode.id) != null ||
                                                tmap.get(tnode.parent) != null)) {
                    TQueue tq = new TQueue(tnode.id, uidx);
                    tq.enqueue(tqnode);
                    qqueue.enqueue(tq);

                    continue;
                }

                stnode.sz++;

                HashSet<Integer> hset = rmap.get(tnode.id);
                if (hset != null) {
                    int counter = hset.size();
                    int cidx = -2;
                    int idx = uidx;

                    for (STNode stn = stnode; stn != null; stn = (idx > -1 ? ststack.get(idx) : null)) {
                        if (counter == 0)
                            break;

                        if (hset.contains(stn.id)) {
                            if (stn.id != tnode.id) {
                                if (stn.bmap == null)
                                    stn.bmap = new HashMap<Integer, Integer>();

                                stn.bmap.put(tnode.id, cidx);
                            }

                            counter--;
                        }

                        cidx = idx;
                        idx = stn.parent;
                    }
                }

                for (TreeNode neighbor : tnode.neighbors) {
                    if (neighbor.id == tnode.parent)
                        continue;

                    neighbor.parent = tnode.id;
                    tqueue.enqueue(new TQNode(neighbor.id));
                }
            }

            ststack.add(stnode);
        }

        qqueue = null;
        tqueue = null;
        tqnode = null;

        int total = 0;
        int ut, ur, val, ncount;
        Integer ridx = null;
        HashMap<Integer, Integer> vmap = null;
        Integer valobj = null;
        ArrayList<List<Long>> al = null;
        BigInteger a = null, b = null;
        BigInteger mobj = BigInteger.valueOf(m);

        for (int i = ststack.size()-1; i > -1; i--) {
            STNode stnode = ststack.get(i);

            if (stnode.parent > -1)
                ststack.get(stnode.parent).sz += stnode.sz;

            HashMap<Integer, ArrayList<List<Long>>> hmap = tmap.get(stnode.id);
            if (hmap == null)
                continue;

            for (Map.Entry<Integer, ArrayList<List<Long>>> e : hmap.entrySet()) {
                ur = e.getKey();
                al = e.getValue();

                ridx = null;
                if (stnode.bmap != null)
                    ridx = stnode.bmap.get(ur);

                ncount = (ridx == null) ? stnode.sz : (ststack.get(0).sz - ststack.get(ridx).sz);

                for (List<Long> u : al) {

                    vmap = mmap.get(u);
                    valobj = (vmap == null) ? null : vmap.get(m);

                    if (valobj == null) {
                        a = BigInteger.valueOf(u.get(0));
                        b = BigInteger.valueOf(u.get(1));

                        val = (a.modPow(b, mobj).intValue() +
                               a.add(BigInteger.ONE).modPow(b, mobj).intValue() +
                               b.add(BigInteger.ONE).modPow(a, mobj).intValue()) % m;

                        valobj = Integer.valueOf(val);

                        if (vmap == null) {
                            vmap = new HashMap<Integer, Integer>();
                            mmap.put(u, vmap);
                        }
                        vmap.put(m, valobj);

                    } else
                        val = valobj.intValue();

                    total += (val * ncount);
                }
            }
        }

        return (total % m);
    }

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

        tl = new TreeNode[n];

        for (int i = 0; i < n; i++)
            tl[i] = new TreeNode(i+1);

        for (int i = 0; i < n-1; i++) {
            String[] sarr = scanner.nextLine().split(" ");

            int x = Integer.parseInt(sarr[0]);
            int y = Integer.parseInt(sarr[1]);

            tl[x-1].neighbors.add(tl[y-1]);
            tl[y-1].neighbors.add(tl[x-1]);
        }

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\r\n\u0085\u2028\u2029])?");

        updates = new HashMap<List<Long>, List<Long>>();
        tmap = new HashMap<Integer, HashMap<Integer, ArrayList<List<Long>>>>();
        rmap = new HashMap<Integer, HashSet<Integer>>();

        for (int i = 0; i < q; i++) {
            String[] sarr = scanner.nextLine().split(" ");

            int r = Integer.parseInt(sarr[1]);
            int t = Integer.parseInt(sarr[2]);

            if ("U".equals(sarr[0])) {
                List<Long> u = (List<Long>)Arrays.asList(new Long[] {new Long(sarr[3]), new Long(sarr[4])});
                updates.putIfAbsent(u, u);

                HashMap<Integer, ArrayList<List<Long>>> hmap = tmap.get(t);
                if (hmap == null) {
                    hmap = new HashMap<Integer, ArrayList<List<Long>>>();
                    tmap.put(t, hmap);
                }

                ArrayList<List<Long>> al = hmap.get(r);
                if (al == null) {
                    al = new ArrayList<List<Long>>();
                    hmap.put(r, al);
                }
                al.add(u);

                HashSet<Integer> hset = rmap.get(r);
                if (hset == null) {
                    hset = new HashSet<Integer>();
                    rmap.put(r, hset);
                }
                hset.add(t);

            } else if ("R".equals(sarr[0])) {
                int total = report(r, t, Integer.parseInt(sarr[3]));
                System.out.printf("%d\n", total);
            }
        }
    }
}


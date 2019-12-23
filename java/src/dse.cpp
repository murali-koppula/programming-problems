
#include <algorithm>
#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <vector>

using namespace std;

#define MaxVal 100004
#define FOR(i, n) for (int i = 0; i < (n); i++)

typedef long long int ll;
ll invcons[5][5];
int mod[10], num[200];
ll UPPER = (ll)1000000000 * (ll)1000000000;

struct bit {
    int a[10];

    bit operator+(const bit &x) const {
        bit y;

        FOR(i, 10) {
            y.a[i] = (x.a[i] + a[i]);

            if (y.a[i] >= mod[i])
                y.a[i] -= mod[i];
        }

        return y;
    }
} tree[MaxVal];

ll binpow(ll a, ll g, int m) {
    a = a % m;
    ll ans = 1;

    while (g) {
        if (g & 1)
            ans = (ans * a) % m;

        a = (a * a) % m;
        g >>= 1;
    }
    return ans;
}

bit read(int index) {
    bit answer;

    FOR(i, 10) answer.a[i] = 0;

    while (index != 0) {
        answer = answer + tree[index];
        index = index - (index & (-index));
    }

    return answer;
}

void update(bit val, int index) {
    while (index < MaxVal) {
        tree[index] = tree[index] + val;
        index = (index + (index & (-index)));
    }
}

void UPDATE(ll a, ll g, int x, int y) {
    if (x > y)
        return;

    bit sending, rsending;

    FOR(i, 10) {
        if (!(i & 1)) {
            sending.a[i] = binpow(a, g, mod[i]);
            sending.a[i] = sending.a[i] + binpow(g + 1, a, mod[i]);

            if (sending.a[i] >= mod[i])
                sending.a[i] -= mod[i];

            sending.a[i] = sending.a[i] + binpow(a + 1, g, mod[i]);

            if (sending.a[i] >= mod[i])
                sending.a[i] -= mod[i];

            rsending.a[i] = mod[i] - sending.a[i];

        } else {
            sending.a[i] = ((ll)sending.a[i - 1] * (ll)x) % mod[i];
            rsending.a[i] = ((ll)rsending.a[i - 1] * (ll)(y + 1)) % mod[i];
        }
    }

    update(sending, x);
    update(rsending, y + 1);
}

bit QUERY(int x, int y) {
    bit ans1, ans2;
    ans2 = read(y);

    if (x == 1) {
        for (int i = 0; i < 10; i += 2) {
            ans2.a[i] = ((ll)ans2.a[i] * (ll)(y + 1)) % mod[i];
        }

        return ans2;
    }

    ans1 = read(x - 1);

    FOR(i, 10) {
        if (!(i & 1))
          ans2.a[i] = ((ll)ans2.a[i] * (ll)(y + 1) - (ll)ans1.a[i] * (ll)(x)) % mod[i];
        else
          ans2.a[i] = ans2.a[i] - ans1.a[i];

      if (ans2.a[i] < 0)
          ans2.a[i] += mod[i];
    }

    return ans2;
}

void constant() {
    mod[0] = mod[1] = 64 * 81 * 25 * 49 * 11 * 13;
    mod[2] = mod[3] = 17 * 19 * 23 * 29 * 31 * 37;
    mod[4] = mod[5] = 43 * 47 * 53 * 59 * 79;
    mod[6] = mod[7] = 61 * 67 * 71 * 73 * 41;
    mod[8] = mod[9] = 83 * 89 * 97 * 101;
    num[17] = num[19] = num[23] = num[29] = num[31] = num[37] = 2;
    num[43] = num[47] = num[53] = num[59] = num[79] = 4;
    num[61] = num[67] = num[71] = num[73] = num[41] = 6;
    num[83] = num[89] = num[97] = num[101] = 8;
}

/* graph part begins */

int start[MaxVal], ending[MaxVal], Root[MaxVal][18], parent[MaxVal],
    depth[MaxVal], store[MaxVal], which, counter;
int N, x, y;

vector<int> graph[MaxVal];

void init() {
    store[0] = 0;
    store[1] = 0;
    store[2] = 1;
    int cmp = 4;

    for (int i = 3; i <= 100000; i++) {
        if (cmp > i)
          store[i] = store[i - 1];

        else {
          store[i] = store[i - 1] + 1;
          cmp <<= 1;
        }
    }
}

void process(int N) {
    memset(Root, -1, sizeof(Root));

    for (int i = 1; i <= N; i++)
        Root[i][0] = parent[i];

    for (int i = 1; (1 << i) <= N; i++)
        for (int j = 1; j <= N; j++)
            if (Root[j][i - 1] != -1)
                Root[j][i] = Root[Root[j][i - 1]][i - 1];
}

int lca(int p, int q) {
    int temp;

    if (depth[p] < depth[q])
        return 1;

    int steps = store[depth[p]];

    for (int i = steps; i >= 0; i--)
        if (depth[p] - (1 << i) >= depth[q] + 1)
          p = Root[p][i];

    if (parent[p] != q)
        return 1;

    which = p;

    return 0;
}

void dfs(int r) {
    counter++;
    start[r] = counter;

    for (vector<int>::iterator it = graph[r].begin(); it != graph[r].end(); it++) {
        if (parent[r] == *it)
            continue;

        parent[*it] = r;
        depth[*it] = depth[r] + 1;
        dfs(*it);
    }

    ending[r] = counter;
}

/* graph part ends */

/* CRT begins */

ll inverse(ll a, ll b) { // b>a
    ll Remainder, p0 = 0, p1 = 1, pcurr = 1, q, m = b;

    while (a != 0) {
      Remainder = b % a;
      q = b / a;
      if (Remainder != 0) {
        pcurr = p0 - (p1 * q) % m;
        if (pcurr < 0)
          pcurr += m;
        p0 = p1;
        p1 = pcurr;
      }
      b = a;
      a = Remainder;
    }
    return pcurr;
}

ll CRT(int rem0, int mod0, int rem1, int mod1, int rm) { // t is the number of pairs of rem and mod
    ll ans = rem0, m = mod[2 * mod0], m1 = mod[2 * mod1];
    ll a = invcons[mod0][mod1] % rm;
    ll b = invcons[mod1][mod0] % rm;

    ans = (((ans * m1) % rm) * b + ((rem1 * m) % rm) * a) % rm;

    return ans;
}

/* CRT ends */

int a[] = {2, 3, 5, 7};

void calculate(bit ans, int m) {
    int s = 1;

    for (int i = 0; i < 4; i++) {
        while (m % a[i] == 0) {
            m /= a[i];
            s *= a[i];
        }

        if (m <= 13) {
            s = s * m;
            m = 1;

            break;
        }
    }

    if (m == 1) { // only mod[0] in action
        int p = ans.a[0] - ans.a[1];

        if (p < 0)
            p += mod[0];

        printf("%d\n", p % (m * s));

    } else if (s == 1) { // num[m]
        int p = ans.a[num[m]] - ans.a[num[m] + 1];

        if (p < 0)
            p += mod[num[m]];

        printf("%d\n", p % (m * s));

    } else { // apply crt
        int rem0, rem1;

        rem0 = ans.a[0] - ans.a[1];
        rem1 = ans.a[num[m]] - ans.a[num[m] + 1];

        if (rem0 < 0)
            rem0 += mod[0];

        if (rem1 < 0)
            rem1 += mod[num[m]];

        printf("%lld\n", CRT(rem0, 0, rem1, num[m] / 2, s * m));
    }
}

int main() {
    constant();

    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++)
        if (i != j)
          invcons[i][j] = inverse(mod[2 * i], mod[2 * j]);

    counter = 0;
    char c;
    int curr, sub, Q, cc;
    ll g, g1, a;
    bit ans;

    scanf("%d", &N);
    assert(N >= 2 && N <= 100000);

    FOR(i, N - 1) {
      scanf("%d%d", &x, &y);
      assert(x >= 1 && x <= N);
      assert(y >= 1 && y <= N);
      graph[x].push_back(y);
      graph[y].push_back(x);
    }

    init();

    parent[1] = 1;
    depth[1] = 0;

    dfs(1);
    process(N);

    scanf("%d", &Q);
    assert(Q >= 1 && Q <= 100000);

    FOR(i, Q) {
        scanf(" %c%d%d%lld", &c, &curr, &sub, &a);
        assert(c == 'U' || c == 'R');
        assert(curr >= 1 && curr <= N);
        assert(sub >= 1 && sub <= N);

        cc = lca(curr, sub);

        if (c == 'U') {
            scanf("%lld", &g);
            assert(a >= 1 && a <= UPPER);
            assert(g >= 1 && g <= UPPER);

            if (curr == sub) {
              UPDATE(a, g, 1, N);

            } else if (cc)
              UPDATE(a, g, start[sub], ending[sub]);

            else {
              UPDATE(a, g, 1, start[which] - 1);
              UPDATE(a, g, ending[which] + 1, N);
            }

        } else {
          assert(a >= 1 && a <= 101);

          if (a == 1) {
              printf("0\n");
              continue;
          }

          if (curr == sub) {
              ans = QUERY(1, N);

          } else if (cc) {
              ans = QUERY(start[sub], ending[sub]);

          } else {
              ans = QUERY(1, start[which] - 1);
              ans = ans + QUERY(ending[which] + 1, N);
          }

          calculate(ans, a);
        }
    }

    return 0;
}

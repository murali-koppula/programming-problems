
import time

p,q,r,k = [int(a) for a in input().split()]
k = 1 if k<1 else k

start = time.time()

count,i,n = 0,0,n
while count < k:
    n = 2*i + 1
    i = i+1
    m1,m2,m3 = p%n,q%n,r%n
    divflag,first = True,True

    while True:
        if m1 == 0 or m2 == 0 or m3 == 0:
            break

        if m1 == p and m2 == q and m3 == r:
            if first:
                first = False
            else:
                divflag = False
                break

        m1,m2,m3 = m2,m3,(m1+m2+m3)%n

    if not divflag:
        count = count + 1

print(n)

end = time.time()
print(end - start)


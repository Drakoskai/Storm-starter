package com.drakos.spout;

import java.math.BigInteger;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.storm.shade.com.google.common.collect.Lists;
import org.apache.storm.shade.com.google.common.collect.Queues;

/**
 *
 * @author Bruce Brown
 */
public final class PiGen {

    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);
    private static final BigInteger FOUR = BigInteger.valueOf(4);
    private static final BigInteger SEVEN = BigInteger.valueOf(7);
    private final List<Integer> place;
    private BigInteger q = BigInteger.ONE;
    private BigInteger r = BigInteger.ZERO;
    private BigInteger t = BigInteger.ONE;
    private BigInteger k = BigInteger.ONE;
    private BigInteger n = BigInteger.valueOf(3);
    private BigInteger l = BigInteger.valueOf(3);
    private BigInteger nn;
    private BigInteger nr;
    private boolean first = true;

    public PiGen() {
        place = Lists.newArrayList();
        place.add(0);
    }

    public int emit() {
        int i = 0;
        while (true) {
            if (FOUR.multiply(q).add(r).subtract(t).compareTo(n.multiply(t)) == -1) {
                int outval = n.intValue();
                System.out.println(outval);

                if (first) {
                    first = false;
                }

                nr = BigInteger.TEN.multiply(r.subtract(n.multiply(t)));
                n = BigInteger.TEN.multiply(THREE.multiply(q).add(r)).divide(t).subtract(BigInteger.TEN.multiply(n));
                q = q.multiply(BigInteger.TEN);
                r = nr;

                int current = place.remove(i);
                if (current == Integer.MAX_VALUE) {
                    i++;
                    current = 0;
                } else {
                    current++;
                }
                place.add(i, current);
                return outval;
            } else {
                nr = TWO.multiply(q).add(r).multiply(l);
                nn = q.multiply((SEVEN.multiply(k))).add(TWO).add(r.multiply(l)).divide(t.multiply(l));
                q = q.multiply(k);
                t = t.multiply(l);
                l = l.add(TWO);
                k = k.add(BigInteger.ONE);
                n = nn;
                r = nr;
            }
        }
    }
}

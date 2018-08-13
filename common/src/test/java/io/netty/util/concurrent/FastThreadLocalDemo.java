package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author chenxiaolei3
 * @Mail chenxiaolei3@jd.com
 * @Description
 * @DATE Created in 19:36 2018/8/3
 * @Modify by
 */
public class FastThreadLocalDemo {
    public static void main(String[] args) {
        final int threadLocalCount = 1000;
        final FastThreadLocal<String>[] caches = new FastThreadLocal[threadLocalCount];
        final Thread mainThread = Thread.currentThread();
        for (int i = 0; i < threadLocalCount; i++) {
            caches[i] = new FastThreadLocal();
        }
        Thread t = new FastThreadLocalThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < threadLocalCount; i++) {
                    caches[i].set("float.lu");
                }
                long start = System.nanoTime();
                for (int i = 0; i < threadLocalCount; i++) {
                    for (int j = 0; j < 1000000; j++) {
                        caches[i].get();
                    }
                }
                long end = System.nanoTime();
                System.out.println("take[" + TimeUnit.NANOSECONDS.toMillis(end - start) +
                        "]ms");
                LockSupport.unpark(mainThread);
            }

        });
        t.start();
        LockSupport.park(mainThread);
    }
}

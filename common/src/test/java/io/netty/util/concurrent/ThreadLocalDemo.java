package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author chenxiaolei3
 * @Mail chenxiaolei3@jd.com
 * @Description
 * @DATE Created in 19:27 2018/8/3
 * @Modify by
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
        final int threadLoaclCount = 1000;
        final ThreadLocal<String>[] caches = new ThreadLocal[threadLoaclCount];
        final Thread main = Thread.currentThread();
        for (int i = 0; i < threadLoaclCount; i++) {
            caches[i] = new ThreadLocal<String>();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < threadLoaclCount; i++) {
                    caches[i].set("flaour");
                }
                long start = System.nanoTime();
                for (int i = 0; i < threadLoaclCount; i++) {
                    for (int j = 0; j < 1000000; j++) {
                        caches[i].get();
                    }
                }
                long end = System.nanoTime();
                System.out.println("take[" + TimeUnit.NANOSECONDS.toMillis(end - start) +
                        "]ms");
                LockSupport.unpark(main);
            }
        }).start();
        LockSupport.park(main);
    }
}

package org.greenleaf.java.nt;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.Callable;

/**
 * Created by wangyonghua on 2019-08-29.
 */
public class Netty {

    final DefaultEventExecutor wang = new DefaultEventExecutor();
    final DefaultEventExecutor li = new DefaultEventExecutor();

    void work() {
        wang.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(
                        Thread.currentThread().getName() + " 1. 这是一道简单的题");
            }
        });

        wang.execute(new Runnable() {
            @Override
            public void run() {
                final Promise<Integer> promise = wang.newPromise();
                promise.addListener(new GenericFutureListener<Future<? super Integer>>() {
                    @Override
                    public void operationComplete(Future<? super Integer> future)
                            throws Exception {
                        System.out.println(Thread.currentThread().getName() + "复杂题执行结果");
                    }
                });
                li.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(
                                Thread.currentThread().getName() + " 2. 这是一道复杂的题");
                        promise.setSuccess(10);
                    }
                });
            }
        });

        wang.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(
                        Thread.currentThread().getName() + " 3. 这是一道简单的题");
            }
        });
    }

    void workErr() {
        wang.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 1. 这是一道简单的题");
            }
        });

        wang.submit(new Runnable() {
            @Override
            public void run() {
                Future<String> result = li.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        for(int i = 0; i <= 10000000; i++){
                            for(int j = 0; j <= 1000000; j++) {
                                ;
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + " 2. 这是一道复杂的题");
                        return null;
                    }
                });
                result.addListener(new GenericFutureListener<Future<? super String>>() {
                    @Override
                    public void operationComplete(
                            Future<? super String> future) throws Exception {
                        System.out.println(Thread.currentThread().getName() + "3. 复杂题执行结果");
                    }
                });
            }
        });

        wang.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 3. 这是一道简单的题");
            }
        });
    }


    public static void main(String[] args) {
        Netty netty = new Netty();
//        netty.work();
        netty.workErr();
    }
}

package org.greenleaf.java.nt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Person extends Thread {
    BlockingQueue<Runnable> taskQueue; //任务队列

    public Person(String name) {
        super(name);
        taskQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        while (true) { //无限循环, 不断从任务队列取任务
            try {
                Runnable task = taskQueue.take();
                task.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void submit(Runnable task) { //将任务提交到任务队列中去
        taskQueue.offer(task);
    }

    public static void main(String[] args) {
        final Person wang = new Person("wang");
        final Person li = new Person("li");
        li.start(); //启动小王
        wang.start(); //启动小李

        wang.submit(new Runnable() { //提交一个简单的题
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 1. 这是一道简单的题");
            }
        });

        wang.submit(new Runnable() { //提交一个复杂的题
            @Override
            public void run() {
                li.submit(new Runnable() { //将复杂的题交给li来做
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + " 2. 这是一道复杂的题");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        wang.submit(new Runnable() { //做完之后将结果作为Task返回给wang
                            @Override
                            public void run() {
                                System.out.println(Thread.currentThread().getName() + " 2 复杂题执行结果");
                            }
                        });
                    }
                });
            }
        });

        wang.submit(new Runnable() { //提交一个简单的题
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 3. 这是一道简单的题");
            }
        });
    }
}
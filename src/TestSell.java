

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

    class Ticket//资源类
    {
        private int number = 30;
        private Lock lock = new ReentrantLock();

        public void saleTicket()
        {
            lock.lock();
            try
            {
                if(number > 0)
                {
                    System.out.println(Thread.currentThread().getName()+"\t卖出第:"+(number--)+"\t还剩下："+number);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    /**
     * 题目：三个售票员     卖出      30张票
     *
     *	多线程编程的企业级套路+模板
     *
     * 1    在高内聚低耦合的前提下，线程          操作(对外暴露的调用方法)          资源类
     */
    public class TestSell
    {
        public static void main(String[] args) throws Exception     //main一切程序入口
        {
            Ticket ticket = new Ticket();

            new Thread(() -> {for (int i = 1; i <=40; i++) ticket.saleTicket();},"A").start();
            new Thread(() -> {for (int i = 1; i <=40; i++) ticket.saleTicket();},"B").start();
            new Thread(() -> {for (int i = 1; i <=40; i++) ticket.saleTicket();},"C").start();
        }
    }







/*new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <=40; i++)
                {
                    ticket.saleTicket();
                }
            }
        },"A").start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <=40; i++)
                {
                    ticket.saleTicket();
                }
            }
        }, "B").start();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <=40; i++)
                {
                    ticket.saleTicket();
                }
            }
        }, "C").start();*/









//    public static void main(String[] args) {
//        Seller s = new Seller();
//
//        Thread a = new Thread(s);
//        a.setName("1号售票点");
//        a.start();
//        Thread b = new Thread(s);
//        b.setName("2号售票点");
//        b.start();
//        Thread c = new Thread(s);
//        c.setName("3号售票点");
//        c.start();
//    }
//}
//
//class Seller implements Runnable {
//    private int num = 300;
//
//    public  synchronized void run() {
//        String name = Thread.currentThread().getName();
//        for (int i = 0; i < 30; i++) {
//            if (this.num > 0) {
//                System.out.println(name + " 卖票，当前卖出的票号为： " + this.num--);
//            }
//        }
//    }
//}



import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程之间按顺序调用，实现A->B->C
 * * 三个线程启动，要求如下：
 * *
 * * AA打印5次，BB打印10次，CC打印15次
 * * 接着
 * * AA打印5次，BB打印10次，CC打印15次
 * * ......来10轮
 * *
 * * 1    高聚低合前提下，线程操作资源类
 * * 2    判断/干活/通知
 * * 3    多线程交互中，必须要防止多线程的虚假唤醒，也即（判断只用while，不能用if）
 * * 4    标志位
 */
class ShareResource {
    private int num = 1;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void print5() {
        lock.lock();
        //判断
        while (num != 1) {
            try {
                condition1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //干活
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "\t" + i);

        }
        //通知
        num = 2;
        condition2.signal();
        lock.unlock();
    }

    public void print10() {
        lock.lock();
        //判断
        while (num != 2) {
            try {
                condition1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //干活
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "\t" + i);

        }
        //通知
        num = 3;
        condition3.signal();
        lock.unlock();
    }

    public void print15() {
        lock.lock();
        //判断
        while (num != 3) {
            try {
                condition1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //干活
        for (int i = 0; i < 15; i++) {
            System.out.println(Thread.currentThread().getName() + "\t" + i);

        }
        //通知
        num = 1;
        condition2.signal();
        lock.unlock();
    }

}


public class ThreadOrderAccess {
    public static void main(String[] args) {

        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i <10; i++) {
            shareResource.print5();
            }
        }, "A").start(); new Thread(() -> {
            for (int i = 0; i <10; i++) {
            shareResource.print10();
            }
        }, "B").start(); new Thread(() -> {
            for (int i = 0; i <10; i++) {
            shareResource.print15();
            }
        }, "C").start();
    }
}

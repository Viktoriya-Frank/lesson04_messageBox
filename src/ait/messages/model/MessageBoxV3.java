package ait.messages.model;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageBoxV3 implements MessageBox{
    private String message;
    private final Lock mutex= new ReentrantLock();
    private final Condition senderWaitCondition = mutex.newCondition();
    private final Condition receiverWaitCondition = mutex.newCondition();



    @Override
    public void post(String message) {
        mutex.lock();
        try {
            while (this.message != null) {
                try {
                    senderWaitCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.message = message;
            receiverWaitCondition.signal();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public String get() {
        mutex.lock();
        try {
            while (this.message == null) {
                try {
                    receiverWaitCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            String result = message;
            this.message = null;
            senderWaitCondition.signal();
            return result;
        } finally {
            mutex.unlock();
        }
    }
}

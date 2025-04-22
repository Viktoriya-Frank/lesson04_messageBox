package ait.messages.model;

public class MessageBoxV2 implements MessageBox{
    private String message;


    @Override
    public synchronized void post(String message) {
        while (this.message != null) {
        try {
            this.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        }
        this.message = message;
        this.notify();
    }

    @Override
    public synchronized String get()  {
        while (message == null) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String result = message;
        message = null;
        this.notifyAll();
        return result;
    }
}

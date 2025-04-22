package ait.messages.model;

public class MessageBoxV1 implements MessageBox{
    private String message;


    @Override
    public synchronized void post(String message) {
        this.message = message;
        notify();
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
        return result;
    }
}

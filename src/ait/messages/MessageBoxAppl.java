package ait.messages;

import ait.messages.model.MessageBox;
import ait.messages.model.MessageBoxV1;
import ait.messages.model.MessageBoxV2;
import ait.messages.service.Receiver;
import ait.messages.service.Sender;

public class MessageBoxAppl {
    private static final int N_MESSAGES = 20;
    private static final int N_RECEIVERS = 5;
    private static MessageBox messageBox = new MessageBoxV2();

    public static void main(String[] args) throws InterruptedException {
        Thread sender = new Thread(new Sender(messageBox, N_MESSAGES));

        for (int i = 0; i < N_RECEIVERS; i++) {
            Thread receiver = new Thread(new Receiver(messageBox));
            receiver.setDaemon(true);
            receiver.start();
        }
        Thread.sleep(100);
        sender.start();
        sender.join();
        Thread.sleep(100);
    }
}

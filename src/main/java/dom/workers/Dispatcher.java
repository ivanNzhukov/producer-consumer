package dom.workers;

import xml.entity.Message;

public interface Dispatcher {

    String makeOrder(Message message);

    void insertOrder(Message message);

    Message getOrder();

    boolean shouldStop();

    void leftTask();
}

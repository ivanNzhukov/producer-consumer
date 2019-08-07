package dom.workers;

import xml.entity.Message;

public interface Taxi extends Runnable {

    void saveXml(Message message);

    void execute(Message message);

}

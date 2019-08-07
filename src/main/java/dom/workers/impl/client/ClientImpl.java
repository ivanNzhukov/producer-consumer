package dom.workers.impl.client;


import dom.start.Starter;
import dom.workers.Client;
import dom.workers.Dispatcher;
import xml.Creator;
import xml.entity.Message;

import java.util.Random;

public class ClientImpl implements Client {

    private Creator creator;
    private Dispatcher dispatcher;

    @Override
    public String get() {
        return makeMessage();
    }

    @Override
    public String makeMessage() {
        Message message = creator.createMessage();
        creator.addTargetId(message, String.valueOf(1 + new Random().nextInt(Starter.TAXI_NUMBER)));
        return dispatcher.makeOrder(message);
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

}

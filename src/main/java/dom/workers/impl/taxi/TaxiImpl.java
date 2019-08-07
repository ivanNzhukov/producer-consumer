package dom.workers.impl.taxi;

import dom.workers.Dispatcher;
import dom.workers.Taxi;
import xml.Creator;
import xml.Reader;
import xml.entity.Message;

import java.util.Random;

import static dom.start.Starter.TIME_TO_WAIT;
import static dom.start.Starter.TIME_TO_WORK;

public class TaxiImpl implements Taxi {

    private Reader reader;
    private Creator creator;
    private Dispatcher dispatcher;


    @Override
    public void run() {
        Message message;
        while (!dispatcher.shouldStop()) {
            try {
                message = dispatcher.getOrder();
                if (message == null) {
                    continue;
                }
                String target = reader.getTargetId(message);
                String thread = Thread.currentThread().getName();
                if (!target.equals(thread)) {
                    dispatcher.insertOrder(message);
                    Thread.sleep(new Random().nextInt(TIME_TO_WAIT));
                    continue;
                }
                execute(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void execute(Message message) {
        try {
            saveXml(message);
            Thread.sleep(TIME_TO_WORK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveXml(Message message) {
        creator.transform(message);
    }


    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}

package dom.workers.impl.dispatcher;

import dom.start.Starter;
import dom.workers.Dispatcher;
import xml.Creator;
import xml.Reader;
import xml.entity.Message;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DispatcherImpl implements Dispatcher {

    private Reader reader;
    private Creator creator;
    private AtomicInteger dispatchId;
    private Queue<Message> emergencyQueue;
    private BlockingQueue<Message> blockingQueue;

    @Override
    public String makeOrder(Message message) {

        if (message == null) {
            throw new NullPointerException();
        }
        creator.addDispatched(message, String.valueOf(dispatchId.incrementAndGet()));
        insertOrder(message);
        return reader.getDispatchId(message);
    }

    @Override
    public void insertOrder(Message message) {
        if (!blockingQueue.offer(message)) {
            emergencyQueue.add(message);
        }
    }

    public void leftTask() {
        while (!shouldStop()) {
            if (Starter.STOP) {
                int size = blockingQueue.size() + emergencyQueue.size();
                StringBuilder builder = new StringBuilder();
                if (size / 10 == 1) {
                    builder.append("Осталось ").append(size).append(" заявок");
                } else {
                    if (size % 10 == 0 || size % 10 >= 5 && size % 10 <= 9) {
                        builder.append("Осталось ").append(size).append(" заявок");
                    } else if (size % 10 >= 2 && size % 10 <= 4) {
                        builder.append("Осталось ").append(size).append(" заявки");
                    } else {
                        builder.append("Осталась ").append(size).append(" заявка");
                    }
                }
                System.out.println(builder.toString());
                try {
                    Thread.sleep(Starter.TIME_TO_WORK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Все заявки выполнены");
    }

    @Override
    public Message getOrder() {

        if (emergencyQueue.isEmpty()) {
            if (!blockingQueue.isEmpty()) {
                return blockingQueue.poll();
            } else {
                return null;
            }
        } else {
            return emergencyQueue.poll();
        }
    }

    @Override
    public boolean shouldStop() {
        return blockingQueue.isEmpty() && emergencyQueue.isEmpty() && Starter.STOP;
    }


    public void setBlockingQueue(BlockingQueue<Message> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void setEmergencyQueue(Queue<Message> emergencyQueue) {
        this.emergencyQueue = emergencyQueue;
    }

    public void setDispatchId(AtomicInteger dispatchId) {
        this.dispatchId = dispatchId;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}

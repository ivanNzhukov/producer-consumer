package dom.start;

import dom.workers.Client;
import dom.workers.Dispatcher;
import dom.workers.Taxi;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import threads.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Starter {

    public static boolean STOP = false;
    private final int CLIENT_NUMBER = 100;
    public static final int TAXI_NUMBER = 10;
    public static final int TIME_TO_WAIT = 50;
    public static final int TIME_TO_WORK = 1000;

    private Taxi taxi;
    private Client client;
    private Dispatcher dispatcher;

    private NamedThreadFactory taxiFactory = new NamedThreadFactory("");
    private NamedThreadFactory clientFactory = new NamedThreadFactory("client - ");
    private ExecutorService taxiService = Executors.newFixedThreadPool(TAXI_NUMBER, taxiFactory);
    private ExecutorService clientService = Executors.newFixedThreadPool(CLIENT_NUMBER, clientFactory);

    public static void main(String[] args) {
        System.out.println(" Start start class ");
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/beans.xml");
        Starter bean = (Starter) context.getBean("starter");
        bean.working();
    }

    private void working() {
        System.out.println(" Start " + CLIENT_NUMBER + " client who call to Dispatcher ");

        for (int i = 0; i < CLIENT_NUMBER; i++) {
            clientService.execute(() -> client.get());
            taxiService.execute(() -> taxi.run());
        }
        System.out.println(" Everyone made call to Dispatcher ");
        new Thread(() -> dispatcher.leftTask()).start();
        Starter.STOP = true;
        clientService.shutdown();
        taxiService.shutdown();

        System.out.println(" Waiting while every task complete ");
    }


    public void setClient(Client client) {

        this.client = client;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}

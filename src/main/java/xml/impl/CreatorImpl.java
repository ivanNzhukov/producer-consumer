package xml.impl;

import xml.Creator;
import xml.Reader;
import xml.entity.Data;
import xml.entity.Dispatch;
import xml.entity.Message;
import xml.entity.Target;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class CreatorImpl implements Creator {

    Reader reader;

    @Override
    public Message createMessage() {
        Message message = new Message();
        Data data = new Data();
        data.setData(Arrays.asList("", "", ""));
        message.setSometags(data);
        return message;
    }

    @Override
    public void addDispatched(Message message, String dispatchId) {
        message.setDispatched(new Dispatch(dispatchId));
    }

    @Override
    public void addTargetId(Message message, String targetId) {
        message.setTarget(new Target(targetId));
    }

    @Override
    public void transform(Message message) {
        File file;
        JAXBContext jc;

        try {

            File dir = new File("taxiDrivers\\" + reader.getTargetId(message));

            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (dir.exists()) {
                file = new File(dir + "\\" + reader.getDispatchId(message) + ".xml");
            } else {
                throw new FileNotFoundException();
            }

            jc = JAXBContext.newInstance(Message.class);

            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(message, file);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}

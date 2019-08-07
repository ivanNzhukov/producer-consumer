package xml;

import xml.entity.Message;

public interface Creator {


    void addDispatched(Message message,String dispatchId);

    void addTargetId(Message message,String targetId);

    void transform(Message message);

    Message createMessage();
}

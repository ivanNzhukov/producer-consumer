package xml;

import xml.entity.Message;

public interface Reader {

    String getTargetId(Message message);

    String getDispatchId(Message message);
}

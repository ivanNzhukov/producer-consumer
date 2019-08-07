package xml.impl;

import xml.Reader;
import xml.entity.Message;

public class ReaderImpl implements Reader {

    @Override
    public String getTargetId(Message message) {

        return message.getTarget().getId();
    }

    @Override
    public String getDispatchId(Message message) {

        return message.getDispatched().getId();
    }
}

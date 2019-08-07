package xml.entity;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", propOrder = {
        "target",
        "dispatched",
        "sometags"}
)
public class Message implements Serializable {

    @XmlElement(name = "target")
    Target target;

    @XmlElement(name = "dispatched")
    Dispatch dispatched;

    @XmlElement(name = "sometags")
    Data sometags;


    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Dispatch getDispatched() {
        return dispatched;
    }

    public void setDispatched(Dispatch dispatched) {
        this.dispatched = dispatched;
    }

    public Data getSometags() {
        return sometags;
    }

    public void setSometags(Data sometags) {
        this.sometags = sometags;
    }

}


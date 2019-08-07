package xml.entity;


import org.springframework.util.CollectionUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sometags", propOrder = {
        "data"
})
public class Data {

    @XmlElement(name = "data")
    private List<String> data;

    public List<String> getData() {
        if (CollectionUtils.isEmpty(data)) {
            data = new ArrayList<>();
        }
        return this.data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}

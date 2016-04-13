package lamao.soh.core.model.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Describes common resource for epoch.
 * Created by Vycheslav Mischeryakov on 04.04.16.
*/
@XStreamAlias("resource")
public class Resource {

    @XStreamAlias("location")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

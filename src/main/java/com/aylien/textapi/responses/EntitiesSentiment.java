package com.aylien.textapi.responses;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class EntitiesSentiment {
    private String text;
    @XmlElementWrapper(name="entities")
    @XmlElement(name="entity")
    private List<EntitiySentiments> entitiySentiments;

    public String getText() {
        return text;
    }

    public List<EntitiySentiments> getEntitiySentiments() {
        return entitiySentiments;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEntitiySentiments(List<EntitiySentiments> entitiySentiments) {
        this.entitiySentiments = entitiySentiments;
    }
}

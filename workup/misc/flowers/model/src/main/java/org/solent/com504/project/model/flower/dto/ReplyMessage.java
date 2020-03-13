package org.solent.com504.project.model.flower.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Schema(description = "Reply Message contains arrays of reply data and response code and a debug message")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplyMessage {

    private Integer code = null;

    private String debugMessage = null;

    @XmlElementWrapper(name = "stringlist")
    @XmlElement(name = "string")
    private List<String> stringList = null;

    @XmlElementWrapper(name = "flowerlist")
    @XmlElement(name = "flower")
    private List<Flower> flowerList = null;

    @Schema(description = "http response code")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Schema(description = "debug message to help developer understand the problem. May include stack trace from server")
    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    @ArraySchema(schema = @Schema(description="returned string values. May be empty.",  implementation = String.class))
    public List<String> getStringList() {
        return stringList;
    }
   

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    @ArraySchema(schema = @Schema(description="flowers returned for query. May be empty.", implementation = Flower.class))
    public List<Flower> getFlowerList() {
        return flowerList;
    }

    public void setFlowerList(List<Flower> flowerList) {
        this.flowerList = flowerList;
    }

}

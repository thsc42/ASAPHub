package net.sharksystem.hub.hubside.lora_ipc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(name="peer")
public class PeerModel {

    private String peerId;

    public PeerModel(String peerId){
        this.peerId = peerId;
    }

    public PeerModel(){}

    @XmlElement(name="peer_id")
    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

}

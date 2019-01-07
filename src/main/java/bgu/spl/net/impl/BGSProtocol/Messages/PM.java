package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.*;
import bgu.spl.net.impl.BGSProtocol.Client;
import bgu.spl.net.impl.BGSProtocol.Inventory;

import java.util.List;

public class PM implements Message {
    private final int opcode = 6;
    private String userName;
    private String content;

    public PM(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public String toString() {
        return "PM" + " " + userName + " " + content;
    }

    @Override
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        Client me = inventory.exists(connectionId);
        Client other = null;
        List<Client> users=inventory.getUsers();
        for(int i =0;i<users.size();i++){
            if(users.get(i).getUserName().equals(userName)){
                other=users.get(i);
            }
        }
        if (me == null) {
            connections.send(connectionId, new Error((short) 6));
            return false;
        }  else if (other == null) {
            connections.send(connectionId, new Error((short) 6));
            return false;
        } else {
            if (inventory.isConnected(other) &&
                    connections.send(inventory.ConnectionId(other.getUserName()), new Notification(0, me.getUserName(), content))) {
            } else {
                other.addMessage(me.getUserName(), content, 0);
            }
            connections.send(connectionId, new ACK((short) 6, null));
            return true;
        }
    }
}

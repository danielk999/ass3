package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Inventory;

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
    public void procses(int connectionId, Connections connections, Inventory inventory) {
        Client me = inventory.exists(connectionId);
        Client other = inventory.exists(inventory.ConnectionId(userName));
        if (me == null) {
            connections.send(connectionId, new Error((short) 6));
        } else if (!inventory.isConnected(me.getUserName())) {
            connections.send(connectionId, new Error((short) 6));
        } else if (other == null) {
            connections.send(connectionId, new Error((short) 6));
        } else {
            if (inventory.isConnected(other) &&
                    connections.send(inventory.ConnectionId(other.getUserName()), new Notification(1, me.getUserName(), content))) {
            } else {
                other.addMessage(me.getUserName(), content, 0);
            }
        }
        connections.send(connectionId, new ACK((short) 6, null));
    }
}

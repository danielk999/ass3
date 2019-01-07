package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.*;
import bgu.spl.net.impl.BGSProtocol.Client;
import bgu.spl.net.impl.BGSProtocol.Inventory;

import java.util.LinkedList;
import java.util.List;

public class Post implements Message {
    private final int opcode = 5;
    private String content;

    public Post(String content) {
        this.content = content;
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

    public String toString() {
        return "POST" + " " + content;
    }

    @Override
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        Client me = inventory.exists(connectionId);
        if (me == null) {
            connections.send(connectionId, new Error((short) 5));
            return false;
        }else {
            List<Client> clients = inventory.getUsers();
            String[] users = content.split(" ");
            List<String> specificUser = new LinkedList<>();
            for (int i = 0; i < users.length; i++) {
                if (users[i].charAt(0) == '@') {
                    specificUser.add(users[i].substring(1));
                }
            }
            for (int i = 0; i < clients.size(); i++) {
                if (((clients.get(i).isFollowing(me.getUserName()) | specificUser.contains(clients.get(i).getUserName())) & inventory.isConnected(clients.get(i).getUserName()))&&
                        connections.send(inventory.ConnectionId(clients.get(i).getUserName()), new Notification(1, me.getUserName(), content))) {
                } else if ((clients.get(i).isFollowing(me.getUserName()) | specificUser.contains(clients.get(i).getUserName())) & !inventory.isConnected(clients.get(i).getUserName())) {
                    clients.get(i).addMessage(me.getUserName(), content, 1);
                }
            }
            connections.send(connectionId, new ACK((short) 5, null));
            me.addPost(content);
            return true;
        }
    }
}

package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.*;
import bgu.spl.net.impl.BGSProtocol.Client;
import bgu.spl.net.impl.BGSProtocol.Inventory;

import java.util.List;

public class Stat implements Message {
    private final int opcode = 8;
    private String userName;

    public Stat(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public String toString() {
        return "STAT" + " " + userName;
    }

    @Override
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        if (inventory.exists(connectionId) == null) {
            connections.send(connectionId, new Error((short) 8));
            return false;
        } else {
            Client me = null;
            List<Client> Allusers = inventory.getUsers();
            for (int i = 0; i < Allusers.size(); i++) {
                if (Allusers.get(i).getUserName().equals(userName)) {
                    me = Allusers.get(i);
                }
            }
            if (me == null) {
                connections.send(connectionId, new Error((short) 8));
                return false;
            } else {
                String[] optional = new String[3];
                optional[0] = "" + me.getPostedMessages().size();
                List<Client> users = inventory.getUsers();
                int followers = 0;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).isFollowing(me.getUserName())) {
                        followers++;
                    }
                }
                optional[1] = "" + followers;
                optional[2] = "" + me.getFollowing().size();
                connections.send(connectionId, new ACK((short) 8, optional));
                return true;
            }
        }
    }
}

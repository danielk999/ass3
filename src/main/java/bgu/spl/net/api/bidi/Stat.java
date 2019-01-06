package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Inventory;

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
    public void procses(int connectionId, Connections connections, Inventory inventory) {
        Client me = inventory.exists(connectionId);
        if (me == null) {
            connections.send(connectionId, new Error((short) 8));
        } else if (!inventory.isConnected(me.getUserName())) {
            connections.send(connectionId, new Error((short) 8));
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
        }
    }
}

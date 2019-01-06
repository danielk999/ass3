package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Inventory;

import java.util.List;

public class UserList implements Message {
    private final int opcode = 7;

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public String toString() {
        return "USERLIST";
    }

    @Override
    public void procses(int connectionId, Connections connections, Inventory inventory) {
        List<Client> registerd = inventory.getUsers();
        String[] users;
        Client me = inventory.exists(connectionId);
        if (me == null) {
            connections.send(connectionId, new Error((short) 7));
        } else if (!inventory.isConnected(me.getUserName())) {
            connections.send(connectionId, new Error((short) 7));
        } else {
            users = new String[registerd.size()];
            for (int i = 0; i < registerd.size(); i++) {
                users[i] = registerd.get(i).getUserName();
            }
            connections.send(connectionId, new ACK((short) 7, users));
        }
    }
}

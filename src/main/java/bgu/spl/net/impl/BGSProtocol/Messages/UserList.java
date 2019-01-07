package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.*;
import bgu.spl.net.impl.BGSProtocol.Client;
import bgu.spl.net.impl.BGSProtocol.Inventory;

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
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        List<Client> registerd = inventory.getUsers();
        String[] users;
        Client me = inventory.exists(connectionId);
        if (me == null) {
            connections.send(connectionId, new Error((short) 7));
            return false;
        } else {
            users = new String[registerd.size()+1];
            users[0]=""+registerd.size();
            for (int i = 1; i < users.length; i++) {
                users[i] = registerd.get(i-1).getUserName();
            }
            connections.send(connectionId, new ACK((short) 7, users));
            return true;
        }
    }
}

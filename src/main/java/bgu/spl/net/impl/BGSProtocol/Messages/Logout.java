package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Inventory;

public class Logout implements Message {
    private final int opcode = 3;

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public String toString() {
        return "LOGOUT";
    }

    @Override
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        boolean resualt = inventory.Logout(connectionId);
        if (resualt) {
            connections.send(connectionId, new ACK((short) 3, null));
            connections.disconnect(connectionId);
            return true;
        } else {
            connections.send(connectionId, new Error((short) 3));
            return false;
        }
    }
}


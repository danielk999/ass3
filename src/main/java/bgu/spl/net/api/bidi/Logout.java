package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Inventory;

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
    public void procses(int connectionId, Connections connections, Inventory inventory) {
        boolean resualt = inventory.Logout(connectionId);
        if (resualt) {
            connections.send(connectionId, new ACK((short) 3, null));
            connections.disconnect(connectionId);
        } else {
            connections.send(connectionId, new Error((short) 3));
        }
    }
}


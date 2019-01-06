package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Inventory;

public interface Message {
    public byte[] encode();

    public String toString();

    public void procses(int connectionId, Connections connections, Inventory inventory);
}

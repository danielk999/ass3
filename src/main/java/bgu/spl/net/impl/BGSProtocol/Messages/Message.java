package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Inventory;

public interface Message {
    public byte[] encode();

    public String toString();

    public void procses(int connectionId, Connections connections, Inventory inventory);
}

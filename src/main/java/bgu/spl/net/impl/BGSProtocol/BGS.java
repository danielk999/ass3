package bgu.spl.net.impl.BGSProtocol;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Messages.Logout;
import bgu.spl.net.impl.BGSProtocol.Messages.Message;

public class BGS implements BidiMessagingProtocol<Message> {
    private int connectionId;
    private Connections connections;
    private Inventory inventory;
    private boolean shouldTerminate = false;

    public BGS(Inventory inventory) {
        this.inventory = inventory;
    }


    @Override
    public void start(int connectionId, Connections connections) {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    @Override
    public void process(Message message) {
        boolean b=message.procses(connectionId, connections, inventory);
        if(b & message instanceof Logout){
            shouldTerminate=true;
        }
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}

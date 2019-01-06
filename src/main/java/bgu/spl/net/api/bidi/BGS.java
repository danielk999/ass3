package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Inventory;

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
        message.procses(connectionId, connections, inventory);
        shouldTerminate = (message instanceof Logout);
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}

package bgu.spl.net.srv;

import bgu.spl.net.impl.BGSProtocol.Inventory;
import bgu.spl.net.impl.BGSProtocol.BGS;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.impl.BGSProtocol.Messages.Message;

import java.util.function.Supplier;

public class ProtocolSupplier implements Supplier<BidiMessagingProtocol<Message>> {

    private Inventory inventory;

    public ProtocolSupplier(Inventory inventory){
        this.inventory=inventory;
    }

    @Override
    public BGS get() {
        return new BGS(inventory);
    }
}

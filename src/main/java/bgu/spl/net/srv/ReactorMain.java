package bgu.spl.net.srv;

import bgu.spl.net.api.Inventory;
import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.bidi.BGS;

import java.util.function.Supplier;

public class ReactorMain {
    private static Reactor reactor;
    private static int port,numOfTrheads;
    private static Supplier<BGS> protocolFactory;
    private static Supplier<MessageEncoderDecoderImpl> readerFactory;
    private static Inventory inventory;

    public static void main(String[]args){
        port=Integer.parseInt(args[0]);
        numOfTrheads=Integer.parseInt(args[1]);
        inventory=new Inventory();
        protocolFactory=new ProtocolSupplier(inventory);
        readerFactory=new EncodeDecodeSupplier();
        reactor=new Reactor(numOfTrheads,port,protocolFactory,readerFactory);
        reactor.serve();
    }

}

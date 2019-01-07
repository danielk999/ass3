package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.impl.BGSProtocol.Inventory;
import bgu.spl.net.api.bidi.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.impl.BGSProtocol.Messages.Message;
import bgu.spl.net.srv.EncodeDecodeSupplier;
import bgu.spl.net.srv.ProtocolSupplier;
import bgu.spl.net.srv.Server;

import java.util.function.Supplier;

public class ReactorMain {
    private static Server reactor;
    private static int port,numOfTrheads;
    private static Supplier<BidiMessagingProtocol<Message>> protocolFactory;
    private static Supplier<MessageEncoderDecoder<Message>> readerFactory;
    private static Inventory inventory;

    public static void main(String[]args){
        port=Integer.parseInt(args[0]);
        numOfTrheads=Integer.parseInt(args[1]);
        inventory=new Inventory();
        protocolFactory=new ProtocolSupplier(inventory);
        readerFactory=new EncodeDecodeSupplier();
        reactor=Server.reactor(numOfTrheads,port,protocolFactory,readerFactory);
        reactor.serve();
    }

}

package bgu.spl.net.srv;

import bgu.spl.net.api.Inventory;
import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.bidi.BGS;

import java.util.function.Supplier;

public class TPCMain {

    private static ThreadPerClientServer tpc;
    private static int port;
    private static Supplier<BGS> protocolFactory;
    private static Supplier<MessageEncoderDecoderImpl> readerFactory;
    private static Inventory inventory;

    public static void main(String[]args){
        port=Integer.parseInt(args[0]);
        inventory=new Inventory();
        protocolFactory=new ProtocolSupplier(inventory);
        readerFactory=new EncodeDecodeSupplier();
        tpc=new ThreadPerClientServer(port,protocolFactory,readerFactory);
        tpc.serve();
    }
}

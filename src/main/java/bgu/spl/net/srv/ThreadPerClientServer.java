package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;

import java.util.function.Supplier;

public class ThreadPerClientServer<T> extends BaseServer {

    public ThreadPerClientServer(
            int port,
            Supplier<BidiMessagingProtocol<T>> protocolFactory,
            Supplier<MessageEncoderDecoder<T>> encoderDecoderFactory) {

        super(port, protocolFactory, encoderDecoderFactory);
    }


    @Override
    protected void execute(BlockingConnectionHandler handler) {
        new Thread(handler).start();
    }
}
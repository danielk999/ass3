package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.MessageEncoderDecoder;
import bgu.spl.net.impl.BGSProtocol.MessageEncoderDecoderImpl;
import bgu.spl.net.impl.BGSProtocol.Messages.Message;

import java.util.function.Supplier;

public class EncodeDecodeSupplier implements Supplier<MessageEncoderDecoder<Message>> {
    @Override
    public MessageEncoderDecoderImpl get() {
        return new MessageEncoderDecoderImpl();
    }
}

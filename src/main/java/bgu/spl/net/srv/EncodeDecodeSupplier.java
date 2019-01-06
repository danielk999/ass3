package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoderImpl;

import java.util.function.Supplier;

public class EncodeDecodeSupplier implements Supplier<MessageEncoderDecoderImpl> {
    @Override
    public MessageEncoderDecoderImpl get() {
        return new MessageEncoderDecoderImpl();
    }
}

package bgu.spl.net.srv;

import bgu.spl.net.api.Inventory;
import bgu.spl.net.api.bidi.BGS;
import java.util.function.Supplier;

public class ProtocolSupplier implements Supplier<BGS> {

    private Inventory inventory;

    public ProtocolSupplier(Inventory inventory){
        this.inventory=inventory;
    }

    @Override
    public BGS get() {
        return new BGS(inventory);
    }
}

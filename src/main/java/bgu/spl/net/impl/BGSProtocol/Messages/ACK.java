package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Inventory;
import bgu.spl.net.impl.BGSProtocol.Messages.Message;

public class ACK implements Message {
    private final int opcode = 10;
    private short MsOpcode;
    private String[] Optional;

    public ACK(short msOpcode, String[] Optional) {
        MsOpcode = msOpcode;
        this.Optional = Optional;
    }

    public short getMsOpcode() {
        return MsOpcode;
    }

    public void setMsOpcode(short msOpcode) {
        MsOpcode = msOpcode;
    }

    public String[] getOptional() {
        return Optional;
    }

    public void setOptional(String[] Optional) {
        this.Optional = Optional;
    }

    @Override
    public byte[] encode() {
        byte[] ACopcode = shortToBytes((short) 10);
        byte[] MGopcode = shortToBytes(MsOpcode);
        byte[][] optional = new byte[0][];
        switch (MsOpcode) {
            case 8:
                optional = new byte[3][];
                optional[0] = shortToBytes(Short.parseShort(Optional[0]));
                optional[1] = shortToBytes(Short.parseShort(Optional[1]));
                optional[2] = shortToBytes(Short.parseShort(Optional[2]));
                break;
            case 7:
            case 4:
                optional = new byte[Optional.length][];
                optional[0] = shortToBytes(Short.parseShort(Optional[0]));
                for (int i = 1; i < Optional.length; i++) {
                    optional[i] = (Optional[i] + '\0').getBytes();
                }
        }
        int length=0;
        for(int i=0;i<optional.length;i++){
            length+=optional[i].length;
        }
        byte[] toReturn = new byte[ACopcode.length + MGopcode.length + length];
        int poss = 0;
        for (int i = 0; i < ACopcode.length; i++) {
            toReturn[poss++] = ACopcode[i];

        }
        for (int i = 0; i < MGopcode.length; i++) {
            toReturn[poss++] = MGopcode[i];

        }
        for (int i = 0; i < optional.length; i++) {
            for (int j = 0; j < optional[i].length; j++)
                toReturn[poss++] = optional[i][j];

        }
        return toReturn;
    }


    private byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }

    @Override
    public String toString() {
        String s = "ACK" + " " + MsOpcode + " ";
        if (Optional != null) {
            //s += Optional.length;
            for (int i = 0; i < Optional.length; i++) {
                s += (Optional[i] + " ");
            }
        }
        return s.substring(0, s.length() - 1);
    }

    @Override
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        return false;
    }
}

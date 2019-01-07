package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Inventory;
import bgu.spl.net.impl.BGSProtocol.Messages.Message;

public class Error implements Message {
    private final int opcode = 11;
    private short MsOpcode;

    public Error(short msOpcode) {
        MsOpcode = msOpcode;
    }

    public int getOpcode() {
        return opcode;
    }

    public short getMsOpcode() {
        return MsOpcode;
    }

    public void setMsOpcode(short msOpcode) {
        MsOpcode = msOpcode;
    }

    private byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }

    @Override
    public byte[] encode() {
        byte[] ERopcode = shortToBytes((short) 10);
        byte[] MGopcode = shortToBytes(MsOpcode);
        byte[] toReturn = new byte[ERopcode.length + MGopcode.length];
        int poss = 0;
        for (int i = 0; i < ERopcode.length; i++) {
            toReturn[poss++] = ERopcode[i];

        }
        for (int i = 0; i < MGopcode.length; i++) {
            toReturn[poss++] = MGopcode[i];

        }
        return toReturn;
    }

    @Override
    public String toString() {
        return "ERROR" + " " + MsOpcode;
    }

    @Override
    public void procses(int connectionId, Connections connections, Inventory inventory) {
    }
}

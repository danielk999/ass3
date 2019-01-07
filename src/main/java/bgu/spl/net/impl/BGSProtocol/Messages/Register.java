package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.impl.BGSProtocol.Client;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Inventory;

public class Register implements Message {
    private final int opcode = 1;
    private String userName;
    private String password;

    public Register(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public String toString() {
        return "REGISTER" + " " + userName + " " + password;
    }

    @Override
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        Client c=inventory.exists(connectionId);
        if(c!=null){
            connections.send(connectionId, new Error((short) 1));
            return false;
        }else {
            boolean b = inventory.Add(userName, password);
            if (b) {
                connections.send(connectionId, new ACK((short) 1, null));
                return true;
            } else {
                connections.send(connectionId, new Error((short) 1));
                return false;
            }
        }
    }
}

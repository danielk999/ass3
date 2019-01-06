package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Inventory;

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
    public void procses(int connectionId, Connections connections, Inventory inventory) {
        boolean b = inventory.Add(new Client(userName, password));
        if (b) {
            connections.send(connectionId, new ACK((short) 1, null));
        } else {
            connections.send(connectionId, new Error((short) 1));
        }
    }
}

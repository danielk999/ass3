package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.impl.BGSProtocol.Client;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Inventory;

public class Login implements Message {
    private final int opcode = 2;
    private String userName;
    private String password;

    public Login(String userName, String password) {
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
        return "LOGIN" + " " + userName + " " + password;
    }

    @Override
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        boolean reualt = inventory.Login(userName, password,connectionId);
        if (reualt) {
            connections.send(connectionId, new ACK((short) 2, null));
            Client c=inventory.exists(connectionId);
            Triplet<String, String, Integer> message=c.getFirstMessages();
            while(message!=null){
                boolean b;
                if(!(b=connections.send(connectionId, new Notification(message.getThird(),message.getFirst(),message.getSecond())))){
                    break;
                }
                c.removeFirstMessages();
                message=c.getFirstMessages();
            }
            return true;
        }
        else {
            connections.send(connectionId, new Error((short) 2));
            return false;
        }
    }
}

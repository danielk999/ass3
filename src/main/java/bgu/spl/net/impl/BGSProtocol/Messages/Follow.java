package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Inventory;
import bgu.spl.net.impl.BGSProtocol.Messages.ACK;
import bgu.spl.net.impl.BGSProtocol.Messages.Error;
import bgu.spl.net.impl.BGSProtocol.Messages.Message;

import java.util.Arrays;
import java.util.List;

public class Follow implements Message {
    private final int opcode = 4;
    private int follow;
    private String[] users;

    public Follow(int follow, String[] users) {
        this.follow = follow;
        this.users = users;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public String toString() {
        String s = "FOLLOW" + " " + follow + " "+users.length+" "+Arrays.toString(users);
        return s;
    }

    @Override
    public boolean procses(int connectionId, Connections connections, Inventory inventory) {
        if (follow == 0) {
            List<String> temp = inventory.follow(connectionId, users);
            String[] usersToFollow=new String[temp.size()+1];
            usersToFollow[0]=""+temp.size();
            for(int i=1;i<usersToFollow.length;i++){
              usersToFollow[i]=temp.get(i-1);
            }
            if (temp.size() == 0) {
                connections.send(connectionId, new Error((short) 4));
                return false;
            } else {
                connections.send(connectionId, new ACK((short) 4, usersToFollow));
                return true;
            }
        } else {
            List<String> temp = inventory.unfollow(connectionId, users);
            String[] usersToUnfollow=new String[temp.size()+1];
            usersToUnfollow[0]=""+temp.size();
            for(int i=1;i<usersToUnfollow.length;i++){
                usersToUnfollow[i]=temp.get(i-1);
            }
            if (temp.size() == 0) {
                connections.send(connectionId, new Error((short) 4));
                return false;
            } else {
                connections.send(connectionId, new ACK((short) 4, usersToUnfollow));
                return true;
            }
        }
    }
}

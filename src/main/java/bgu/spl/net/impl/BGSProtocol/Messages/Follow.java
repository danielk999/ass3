package bgu.spl.net.impl.BGSProtocol.Messages;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.impl.BGSProtocol.Inventory;
import bgu.spl.net.impl.BGSProtocol.Messages.ACK;
import bgu.spl.net.impl.BGSProtocol.Messages.Error;
import bgu.spl.net.impl.BGSProtocol.Messages.Message;

import java.util.Arrays;

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
    public void procses(int connectionId, Connections connections, Inventory inventory) {
        if (follow == 0) {
            String[] usersToFollow = inventory.follow(connectionId, users);
            if (usersToFollow.length == 0) {
                connections.send(connectionId, new Error((short) 4));
            } else {
                connections.send(connectionId, new ACK((short) 4, usersToFollow));
            }
        } else {
            String[] usersToUnfollow = inventory.unfollow(connectionId, users);
            if (usersToUnfollow.length == 0) {
                connections.send(connectionId, new Error((short) 4));
            } else {
                connections.send(connectionId, new ACK((short) 4, usersToUnfollow));
            }
        }
    }
}

package bgu.spl.net.impl.BGSProtocol;

import bgu.spl.net.impl.BGSProtocol.Messages.Triplet;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Client {
    private String userName, password;
    private List<String> following;
    private Queue<Triplet<String, String, Integer>> messages;
    private List<String> postedMessages;


    public Client(String userName, String password) {
        this.userName = userName;
        this.password = password;
        following = new LinkedList<>();
        messages = new LinkedList<>();
        postedMessages = new LinkedList<>();
    }

    public String getUserName() {
        return userName;
    }

    public boolean isFollowing(String name) {
        return following.contains(name);
    }

    public List<String> getPostedMessages() {
        return postedMessages;
    }

    public void addPost(String s) {
        postedMessages.add(s);
    }

    public List<String> getFollowing() {
        return following;
    }

    public boolean equals(Object C) {
        if (!(C instanceof Client)) {
            return false;
        }
        Client other = (Client) C;
        if (this.password != other.password | this.userName != other.userName) {
            return false;
        }
        return true;
    }

    public String getPassword() {
        return password;
    }

    public List<String> addFollowers(String[] users) {
        List<String> toReturn = new LinkedList<>();
        synchronized (following) {
            for (int i = 0; i < users.length; i++) {
                if (!following.contains(users[i])) {
                    following.add(users[i]);
                    toReturn.add(users[i]);
                }
            }
        }
        return toReturn;
    }

    public List<String> removeFollowers(String[] users) {
        List<String> toReturn = new LinkedList<>();
        synchronized (following) {
            for (int i = 0; i < users.length; i++) {
                if (following.contains(users[i])) {
                    following.remove(users[i]);
                    toReturn.add(users[i]);
                }
            }
        }
        return toReturn;
    }

    public void addMessage(String userName, String content, int NotificationType) {
        synchronized (messages) {
            messages.add(new Triplet<>(userName, content, NotificationType));
        }
    }

    public Triplet<String, String, Integer> getFirstMessages() {
        if(messages.isEmpty()){
            return null;
        }
        Triplet<String, String, Integer> toReturn = messages.peek();
        messages = new LinkedList<>();
        return toReturn;
    }

    public void removeFirstMessages() {
        if(!messages.isEmpty()){
            messages.remove();
        }
    }

}

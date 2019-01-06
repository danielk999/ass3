package bgu.spl.net.api;

import bgu.spl.net.api.bidi.Client;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Inventory {
    private List<Client> users;
    private ConcurrentHashMap<Integer, Client> connectedUsers;

    public Inventory() {
        users = new LinkedList<>();
        connectedUsers = new ConcurrentHashMap<>();
    }

    public boolean Add(Client c) {
        if (!users.contains(c)) {
            synchronized (users) {
                users.add(c);
            }
            return true;
        }
        return false;
    }

    public boolean Login(String userName, String pass,int connectionId) {
        Client c = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName() == userName & users.get(i).getPassword() == pass)
                c = users.get(i);
        }
        if (c == null) {
            return false;
        } else {
            if (connectedUsers.contains(c))
                return false;
        }
        connectedUsers.put(connectionId,c);
        return true;
    }

    public boolean Logout(int connectionid) {
        Client c = null;
        c = connectedUsers.get(connectionid);
        if (c != null) {
            return false;
        }
        connectedUsers.remove(connectionid);
        return true;
    }

    public String[] follow(int connectionid, String[] users) {
        Client c = null;
        c = connectedUsers.get(connectionid);
        List<String> usersToFollow = c.addFollowers(users);
        return (String[]) usersToFollow.toArray();
    }

    public String[] unfollow(int connectionid, String[] users) {
        Client c = null;
        c = connectedUsers.get(connectionid);
        List<String> usersToFollow = c.removeFollowers(users);
        return (String[]) usersToFollow.toArray();
    }


    public boolean isConnected(String userName) {
        Client c = find(userName);
        if (c != null && connectedUsers.contains(c)) {
            return true;
        }
        return false;
    }

    public boolean isConnected(Client c) {
        if (c != null && connectedUsers.contains(c)) {
            return true;
        }
        return false;
    }

    public int ConnectionId(String userName) {
        Client c = find(userName);
        Iterator it = connectedUsers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (c.equals((Client) entry.getValue())) {
                return (int) entry.getKey();
            }
        }
        return 0;
    }

    public Client exists(int connectionid) {
        Client c = null;
        c = connectedUsers.get(connectionid);
        if (c != null) {
            return c;
        }
        return null;
    }

    public List<Client> getUsers() {
        return users;
    }

    private Client find(String userName) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName() == userName) {
                    return users.get(i);
                }
            }
        return null;
    }

}

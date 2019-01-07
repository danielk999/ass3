package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.ConnectionHandler;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImpl<T> implements Connections<T> {

    private ConcurrentHashMap<Integer, ConnectionHandler<T>> idmap = new ConcurrentHashMap<>();
    private int id = 1;
    private Object lock;

    public ConnectionsImpl() {
    }

    @Override
    public boolean send(int connectionId, T msg) {
        ConnectionHandler<T> handler = find(connectionId);
        if (handler != null) {
            synchronized (handler) {
                handler.send(msg);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void broadcast(T msg) {
            Iterator it = idmap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                ConnectionHandler<T> handler = (ConnectionHandler<T>) entry.getKey();
                synchronized (handler) {
                    handler.send(msg);
                }
            }
    }

    @Override
    public void disconnect(int connectionId) {
        ConnectionHandler<T> handler = find(connectionId);
        idmap.remove(connectionId);
        try {
            handler.close();
        }catch (IOException e){}
    }

    public int connect(ConnectionHandler<T> handler) {
        synchronized (lock) {
            idmap.put(id, handler);
            id++;
            return id - 1;
        }
    }

    private ConnectionHandler<T> find(int connectionId) {
            return idmap.get(connectionId);
    }
}

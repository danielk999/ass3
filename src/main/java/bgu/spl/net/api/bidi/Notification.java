package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Inventory;

public class Notification implements Message {
    private final int opcode = 9;
    private int NotificationType;
    private String PostingUser;
    private String Content;

    public Notification(int notificationType, String postingUser, String content) {
        NotificationType = notificationType;
        PostingUser = postingUser;
        Content = content;
    }

    public int getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(int notificationType) {
        NotificationType = notificationType;
    }

    public String getPostingUser() {
        return PostingUser;
    }

    public void setPostingUser(String postingUser) {
        PostingUser = postingUser;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public byte[] encode() {
        byte[] opcode = shortToBytes((short) 9);
        byte Notification;
        if (NotificationType == 0) {
            Notification = 0;
        } else {
            Notification = 1;
        }
        byte[] Posting = (PostingUser + '\0').getBytes();
        byte[] Contents = (Content + '\0').getBytes();
        byte[] toReturn = new byte[opcode.length + 1 + Posting.length + Contents.length];
        int poss = 0;
        for (int i = 0; i < opcode.length; i++) {
            toReturn[poss++] = opcode[i];
        }
        toReturn[poss++] = Notification;
        for (int i = 0; i < Posting.length; i++) {
            toReturn[poss++] = Posting[i];
        }
        for (int i = 0; i < Contents.length; i++) {
            toReturn[poss++] = Contents[i];
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
        return "NOTIFICATION" + " " + NotificationType + " " + PostingUser + " " + Content;
    }

    @Override
    public void procses(int connectionId, Connections connections, Inventory inventory) {
    }
}

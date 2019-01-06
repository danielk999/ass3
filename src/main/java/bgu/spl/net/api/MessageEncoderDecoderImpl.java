package bgu.spl.net.api;

import bgu.spl.net.api.bidi.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<Message> {

    private byte[] bytes = new byte[1 << 10];
    private int len = 0;

    @Override
    public Message decodeNextByte(byte nextByte) {
        if (nextByte == '\0') {
            return CreateMessage();
        } else if (len == 1) {
            pushByte(nextByte);
            byte[] opbyte = {bytes[0], bytes[1]};
            short opcode = bytesToShort(opbyte);
            if (opcode == 3) {
                len=0;
                return new Logout();
            }
            if (opcode == 7) {
                len=0;
                return new UserList();
            }
        }
        else {
            pushByte(nextByte);
            return null;
        }
        return null;
    }

    private Message CreateMessage() {
        byte[] opbyte = {bytes[0], bytes[1]};
        short opcode = bytesToShort(opbyte);
        if(len==0){
            bytes[len++]=bytes[0];
        }
        else {
            switch (opcode) {
                case 1:
                    return Register();
                case 2:
                    return Login();
                case 4:
                    return Folllow();
                case 5:
                    return Post();
                case 6:
                    return PM();
                case 8:
                    return Stat();
            }
        }
        return null;
    }

    private String TwoStrings() {
        boolean zerobyte = false;
        for (int i = 2;i<len; i++) {
            if (bytes[i] == '\0') {
                zerobyte = true;
                break;
            }
        }
        if (!zerobyte) {
            pushByte((byte) '\0');
            return null;
        } else {
            byte[] first = new byte[len], second = new byte[len];
            String toReturn = "";
            boolean zero = false;
            int firstLength = 0, secondLength = 0;
            for (int i = 2; i < len; i++) {
                if (bytes[i] == '\0') {
                    zero = true;
                } else {
                    if (!zero) {
                        first[firstLength++] = bytes[i];
                    } else {
                        second[secondLength++] = bytes[i];
                    }
                }
            }
            String s1 = new String(first, 0, firstLength, StandardCharsets.UTF_8);
            String s2 = new String(second, 0, secondLength, StandardCharsets.UTF_8);
            return s1 + '\0' + s2;
        }
    }

    private Register Register() {
        String s = TwoStrings();
        if (s != null) {
            String[] pair = s.split("\0");
            Register toReturn = new Register(pair[0], pair[1]);
            len=0;
            return toReturn;
        }
        return null;
    }

    private Login Login() {
        String s = TwoStrings();
        if (s != null) {
            String[] pair = s.split("\0");
            Login toReturn = new Login(pair[0], pair[1]);
            len=0;
            return toReturn;
        }
        return null;
    }

    private Follow Folllow() {
        int NumOfUsers = 0;
        for (int i = 3; i<len; i++) {
            if (bytes[i] == '\0' & i!=3) {
                NumOfUsers++;
            }
        }
        if (NumOfUsers != bytesToShort(Arrays.copyOfRange(bytes, 3, 5))-1 | len<5) {
            pushByte((byte) '\0');
            return null;
        } else {
            byte[] usernames = Arrays.copyOfRange(bytes, 5, len);
            int startname = 0;
            String[] users = new String[NumOfUsers+1];
            int length = 0;
            for (int i = 0; i < usernames.length; i++) {
                if (usernames[i] == '\0') {
                    users[length++] = new String(usernames, startname, i - startname, StandardCharsets.UTF_8);
                    startname = i + 1;
                }
            }
            users[length++] = new String(usernames, startname, usernames.length - startname, StandardCharsets.UTF_8);
            Short follow = bytesToShort(new byte[]{'\0',bytes[2]});
            len=0;
            return new Follow(follow, users);
        }
    }

    private Post Post() {
        String s = new String(bytes, 2, len - 2, StandardCharsets.UTF_8);
        len=0;
        return new Post(s);
    }

    private PM PM() {
        String s = TwoStrings();
        if (s != null) {
            String[] pair = s.split("\0");
            PM toReturn = new PM(pair[0], pair[1]);
            len=0;
            return toReturn;
        }
        return null;
    }

    private Stat Stat() {
        String s = new String(bytes, 2, len - 2, StandardCharsets.UTF_8);
        len=0;
        return new Stat(s);
    }

    @Override
    public byte[] encode(Message message) {
        return message.encode();
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }


}

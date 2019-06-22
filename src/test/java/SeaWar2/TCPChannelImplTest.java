package SeaWar2;

import org.junit.Assert;
import org.junit.Test;
import seaWar2.communication.TCPChannel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TCPChannelImplTest {

    @Test
    public void establishConnection_GoodTest01() {
        new Thread(() -> {
            try {
                TCPChannel channelA = TCPChannel.createTCPChannel(8080, true, "channelA");
                new Thread(channelA).start();
                channelA.waitForConnection();
                Assert.assertTrue(channelA.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            TCPChannel channelB = TCPChannel.createTCPChannel(8080, false, "channelB");
            channelB.run();
            channelB.waitForConnection();
            Assert.assertTrue(channelB.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void establishConnection_GoodTest02() {
        new Thread(() -> {
            TCPChannel channelA = TCPChannel.createTCPChannel(8080, true, "channelA");
            channelA.run();
            try {
                channelA.waitForConnection();
                DataOutputStream out = new DataOutputStream(channelA.getOutputStream());
                out.writeUTF("hello?");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        TCPChannel channelB = TCPChannel.createTCPChannel(8080, false, "channelB");
        channelB.run();
        Assert.assertTrue(channelB.isConnected());
        try {
            channelB.waitForConnection();
            DataInputStream in = new DataInputStream(channelB.getInputStream());
            String echo = in.readUTF();
            System.out.println("From channelB inputStream: " + echo);
            Assert.assertEquals("hello?", echo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
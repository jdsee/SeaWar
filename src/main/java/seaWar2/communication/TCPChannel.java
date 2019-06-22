package seaWar2.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface creates sockets for TCP connection.
 * It must be specified in the constructor, wether the connection should be established as server or as client
 *
 * @author thsc
 */
public interface TCPChannel extends Runnable {

    static TCPChannel createTCPChannel(int port, boolean asServer, String name) {
        return new TCPChannelImpl(port, asServer, name);
    }

    void close() throws IOException;

    void waitForConnection() throws IOException;

    void checkConnected() throws IOException;

    boolean isConnected();

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;
}

package seaWar2.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author thsc
 */
public class TCPChannelImpl implements TCPChannel {

    private final int port;
    private final boolean asServer;
    private final String name;
    private Socket socket = null;

    private boolean fatalError = false;
    private boolean threadRunning = false;

    private final static int WAIT_LOOP_IN_MILIS = 1000;

    public TCPChannelImpl(int port, boolean asServer, String name) {
        this.port = port;
        this.asServer = asServer;
        this.name = name;
    }

    @Override
    public void run() {
        this.threadRunning = true;
        try {
            if (this.asServer) {
                this.socket = new TCPServer().getSocket();
            } else {
                this.socket = new TCPClient().getSocket();
            }
        } catch (IOException ex) {
            //<<<<<<<<<<<<<<<<<<debug
            String s = "couldn't esatblish connection";
            System.out.println(s + this.name);
            this.fatalError = true;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.socket != null) {
            this.socket.close();
            System.out.println("socket closed");
        }
    }

    @Override
    public void waitForConnection() throws IOException {
        if (!this.threadRunning) {
            try {
                Thread.sleep(WAIT_LOOP_IN_MILIS);
            } catch (InterruptedException e) {
                //ignore
            }
            if (!this.threadRunning) {
                throw new IOException("must start TCPChannel first - start TCPChannel by calling run()");
            }
        }
        while (!this.fatalError && this.socket == null) {
            try {
                Thread.sleep(WAIT_LOOP_IN_MILIS);
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }

    @Override
    public void checkConnected() throws IOException {
        if (this.socket == null) {
            //<<<<<<<<<<<<<<<<<<<<<<< debug
            String s = "not yet connected - call connect()";
            System.out.println(s);
            //>>>>>>>>>>>>>>>>>>>>>>> debug
            throw new IOException(s);
        }
    }

    @Override
    public boolean isConnected() {
        return this.socket != null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        this.checkConnected();
        return this.socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        this.checkConnected();
        return this.socket.getOutputStream();
    }

    private class TCPServer {
        Socket getSocket() throws IOException {
            ServerSocket serverSocket = new ServerSocket(port);

            //<<<<<<<<<<<<<<<<<<<<<<< debug
            StringBuilder b = new StringBuilder();
            b.append("TCPChannel (");
            b.append(name);
            b.append("): ");
            b.append("opened port ");
            b.append(port);
            b.append(" on localhost and wait");
            System.out.println(b.toString());
            //>>>>>>>>>>>>>>>>>>>>>>> debug

            Socket socket = serverSocket.accept();

            //<<<<<<<<<<<<<<<<<<<<<<< debug
            b = new StringBuilder();
            b.append("TCPChannel (");
            b.append(name);
            b.append("): ");
            b.append("connected");
            System.out.println(b.toString());
            //>>>>>>>>>>>>>>>>>>>>>>> debug

            return socket;
        }
    }

    private class TCPClient {
        Socket getSocket() throws IOException {
            while (true) {
                try {
                    //<<<<<<<<<<<<<<<<<<<<<<< debug
                    StringBuilder b = new StringBuilder();
                    b.append("TCPChannel (");
                    b.append(name);
                    b.append("): ");
                    b.append("try to connect localhost port ");
                    b.append(port);
                    System.out.println(b.toString());
                    //>>>>>>>>>>>>>>>>>>>>>>> debug

                    Socket socket = new Socket("localhost", port);

                    b = new StringBuilder();
                    b.append("TCPChannel (");
                    b.append(name);
                    b.append("): ");
                    b.append("connected");
                    System.out.println(b.toString());

                    return socket;
                } catch (IOException e) {
                    //<<<<<<<<<<<<<<<<<<<<<<< debug
                    StringBuilder b = new StringBuilder();
                    b.append("TCPChannel (");
                    b.append(name);
                    b.append("): ");
                    b.append("failed / wait and re-try (");
                    b.append(port);
                    b.append(")");
                    System.out.println(b.toString());
                    //>>>>>>>>>>>>>>>>>>>>>>> debug
                    try {
                        Thread.sleep(WAIT_LOOP_IN_MILIS);
                    } catch (InterruptedException ex) {
                        //ignore
                    }
                }
            }
        }
    }
}

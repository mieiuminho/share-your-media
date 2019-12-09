package server;

import util.BoundedBuffer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public final class Server {

    @SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:StaticVariableName"})
    private static int REQUEST_MAX_SIZE = 100;
    @SuppressWarnings({"checkstyle:StaticVariableName"})
    private static int PORT = Integer.parseInt(System.getenv("SYM_SERVER_PORT"));
    private ServerSocket socket;
    private BoundedBuffer<String> requests;
    private Map<Integer, PrintWriter> replies;

    public Server() {
        this.requests = new BoundedBuffer<>(Server.REQUEST_MAX_SIZE);
        this.replies = new HashMap<>();
    }

    public static void main(final String[] ars) {
        new Server().startUp();
    }

    public void startUp() {

        try {
            this.socket = new ServerSocket(Server.PORT);
            System.out.println("Server is up at " + this.socket.getLocalSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int id = 0;
        while (true) {

            try {
                Socket clientServer = this.socket.accept();
                new Thread(new Connection(id, clientServer, this.requests, this.replies)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            id++;

        }
    }
}

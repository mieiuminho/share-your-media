package server;

import util.BoundedBuffer;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;

public final class Connection implements Runnable {

    private int id;
    private final Socket socket;
    private BoundedBuffer<String> requests;
    private Map<Integer, PrintWriter> replies;

    public Connection(final int id, final Socket socket, final BoundedBuffer<String> requests,
            final Map<Integer, PrintWriter> replies) {
        this.id = id;
        this.socket = socket;
        this.requests = requests;
        this.replies = replies;
    }

    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            synchronized (this.replies) {
                this.replies.put(this.id, new PrintWriter(this.socket.getOutputStream()));
            }

            String operation;

            while ((operation = in.readLine()) != null) {

                String[] split = operation.split("\\s+");

                if (split[0].toLowerCase().equals("download")) {

                    PrintWriter p = this.replies.get(this.id);
                    byte[] r = Files.readAllBytes(new File(operation).toPath());
                    p.println(Base64.getEncoder().encodeToString(r));
                    p.flush();

                }

                if (split[0].toLowerCase().equals("upload")) {

                    // split[0] -> "upload"
                    // split[1] -> nome
                    // split[2] -> ficheiro

                    byte[] r = Base64.getDecoder().decode(split[2]);

                    FileOutputStream fos = new FileOutputStream(System.getenv("SYS_SERVER_DATA_DIR") + split[1]);

                    fos.write(r);

                }

            }

            synchronized (this.replies) {
                this.replies.remove(this.id);
            }

            this.socket.shutdownOutput();
            this.socket.shutdownInput();
            this.socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

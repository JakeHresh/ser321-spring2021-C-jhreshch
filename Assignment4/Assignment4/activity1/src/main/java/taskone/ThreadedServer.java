/**
  File: Server.java
  Author: Student in Fall 2020B
  Description: Server class in package taskone.
*/

package taskone;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

/**
 * Class: Server
 * Description: Server tasks.
 */
class ThreadedServer extends Thread {

    private static StringList strings = new StringList();
    private Socket sock;
    private int id;

    public ThreadedServer(Socket socket, int idNum) {
        this.sock = socket;
        this.id = idNum;
    }

    public void run() {
        Performer performer = new Performer(sock, strings);// These two lines
        performer.doPerform();                             // belong in run.
    }

    public static void main(String[] args) throws Exception {
        int port;
        int idNum = 0;
        Socket sockT = null;
        //strings = new StringList(); // defined strings earlier

        if (args.length != 1) {
            // gradle runServer -Pport=9099 -q --console=plain
            System.out.println("Usage: gradle runServer -Pport=9099 -q --console=plain");
            System.exit(1);
        }
        port = -1;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
        }
        try{
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server Started...");
            while (true) {
                System.out.println("Accepting a Request...");
                sockT = server.accept(); // defined sock earlier

                //Performer performer = new Performer(sock, strings);// These two lines
                //performer.doPerform();                             // belong in run.
                ThreadedServer newServerThread = new ThreadedServer(sockT, idNum++);
                newServerThread.start();
            }
            //try {
            //System.out.println("close socket of client ");
            //sockT.close();
        } catch (Exception e) {
            if (sockT != null) sockT.close();
            //e.printStackTrace();
        } finally {
            if (sockT != null) sockT.close();
        }
        
    }
}

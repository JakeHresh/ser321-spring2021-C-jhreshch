import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;
/**
 * A class to demonstrate a simple client-server connection using sockets.
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version April 2020
 * 
 * @modified-by David Clements <dacleme1@asu.edu> September 2020
 * @modified-by Jacob Hreshchyshyn <jhreshch@asu.edu> March 2021
 */
class SockClient {
  public static void main (String args[]) throws Exception {
    Socket sock = null;
    String host = "localhost";
    String message = "Successfully Connected With Server!";
    Integer number = 1;
    int port = 8080;
    Scanner scan = new Scanner(System.in);
    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    host = args[0];
    message = args[1];
    if(pattern.matcher(args[2]).matches())
       number = Integer.valueOf(args[2]);//error handling needed to parse int
    else
       number = 1;
    if(pattern.matcher(args[3]).matches())
       port = Integer.parseInt(args[3]);//error handling needed to parse int
    else
       port = 8080;
    try {
      // open the connection // port was originally 8080
      sock = new Socket(host, port); // connect to host and socket on port 8888
      // get output channel
      OutputStream out = sock.getOutputStream();
      // create an object output writer (Java only)
      ObjectOutputStream os = new ObjectOutputStream(out);
      // write the whole message
      os.writeObject( message);
      os.writeObject( number);
      // make sure it wrote and doesn't get cached in a buffer
      os.flush();

      ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

      String i = "";
      while(!i.equals("stop")){
        i = (String) in.readObject();
        System.out.println(i);
        if(i.equals("stop"))
           break;
        String s = scan.next();
        System.out.println(s);
        os.writeObject(s);
        os.flush();
      }
      sock.close(); // close socked after sending
    } catch (Exception e) {
        System.out.println("There's been a connection error with the Server. Printing stack trace...");
        e.printStackTrace();
    }
  }
}

package fauxSolution.udp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.*;

import java.util.regex.Pattern;

public class Client {
	  /*
	   * request: { "selected": <int: 1=joke, 2=quote, 3=image, 4=random>,
	   * (optional)"min": <int>, (optional)"max":<int> }
	   * 
	   * response: {"datatype": <int: 1-string, 2-byte array>, "type": <"joke", "quote", "image"> "data": <thing to
	   * return> }
	   * 
	   * error response: {"error": <error string> }
	   */
static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
public static JSONObject joke() {
  JSONObject request = new JSONObject();
  request.put("selected", 1);
  return request;
}

public static JSONObject quote() {
  JSONObject request = new JSONObject();
  request.put("selected", 2);
  return request;
}

public static JSONObject image() {
  JSONObject request = new JSONObject();
  request.put("selected", 3);
  return request;
}

public static JSONObject random() {
  JSONObject request = new JSONObject();
  request.put("selected", 4);
  return request;
}
public static JSONObject numericAnswer(int a){
  JSONObject request = new JSONObject();
  request.put("selected", a);
  return request;
}
public static JSONObject stringAnswer(String s){
  JSONObject request = new JSONObject();
  request.put("selected", s);
  return request;
}

  public static void main(String[] args) throws IOException {
    DatagramSocket sock;
    String host = "localhost";
    int port = 8080;
    boolean gameStart = false;
    host = args[0];
    if(pattern.matcher(args[1]).matches())
      port = Integer.parseInt(args[1]);
    try {
      InetAddress address = InetAddress.getByName(host);
      sock = new DatagramSocket();

      Scanner input = new Scanner(System.in);
      int choice;
      String in = "";
      System.out.println("Please type 1 to reach out to the server.");
      do {
        in = input.next();
        if(pattern.matcher(in).matches())
          choice = Integer.parseInt(in); // what if not int? .. should error handle this
        else
          choice = -1;
        JSONObject request = null;
        if(!gameStart)
        {
          switch (choice) {
          case (1):
            request = joke();
            gameStart = true;
            break;
          default:
            System.out.println("Please type 1.");
            break;
          }
        }
        else
        {
          request = stringAnswer(in);
        }

        if (request != null) {
          NetworkUtils.Send(sock, address, port, JsonUtils.toByteArray(request));
          NetworkUtils.Tuple responseTuple = NetworkUtils.Receive(sock);
          JSONObject response = JsonUtils.fromByteArray(responseTuple.Payload);
          if (response.has("error")) {
              System.out.println(response.getString("error"));
            } else {
              switch (response.getInt("datatype")) {
              case (1):
              	  System.out.println("Your " + response.getString("type"));
                System.out.println(response.getString("data"));
                break;
              case (2): {
              	  System.out.println("Your image");
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] bytes = decoder.decode(response.getString("data"));
                ImageIcon icon = null;
                try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
                  BufferedImage image = ImageIO.read(bais);
                  icon = new ImageIcon(image);
                }
                if (icon != null) {
                  JFrame frame = new JFrame();
                  JLabel label = new JLabel();
                  label.setIcon(icon);
                  frame.add(label);
                  frame.setSize(icon.getIconWidth(), icon.getIconHeight());
                  frame.show();
                }
              }
              break;
            }
          }
        }
      } while (true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

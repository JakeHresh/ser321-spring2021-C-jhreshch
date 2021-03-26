/**
  File: Performer.java
  Author: Student in Fall 2020B
  Description: Performer class in package taskone.
*/

package taskone;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Class: Performer 
 * Description: Threaded Performer for server tasks.
 */
class Performer {

    private StringList state;
    private Socket conn;

    public Performer(Socket sock, StringList strings) {
        this.conn = sock;
        this.state = strings;
    }

    public synchronized JSONObject add(String str) {
        JSONObject json = new JSONObject();
        json.put("datatype", 1);
        json.put("type", "add");
        state.add(str);
        json.put("data", state.toString());
        return json;
    }

    public synchronized JSONObject pop() {
        JSONObject json = new JSONObject();
        json.put("datatype", 2);
        json.put("type", "pop");
        if (state.size() > 0) {
            json.put("data", state.strings.get(0));
            state.strings.remove(0);
        } else {
            json.put("data", "null");
        }
        return json;
    }

    public synchronized JSONObject display() {
        JSONObject json = new JSONObject();
        json.put("datatype", 3);
        json.put("type", "display");
        json.put("data", state.toString()); // this should adequately display the list contents
        return json;
    }

    public synchronized JSONObject count() {
        JSONObject json = new JSONObject();
        json.put("datatype", 4);
        json.put("type", "count");
        json.put("data", "" + state.size());
        return json;
    }

    public synchronized JSONObject swap(String indices) {
        JSONObject json = new JSONObject();
        json.put("datatype", 5);
        json.put("type", "switch");
        String[] separatedIndices = indices.split(" ");
        int index1 = Integer.parseInt(separatedIndices[0]);
        int index2 = Integer.parseInt(separatedIndices[1]);
        if(state.size() > 0) {
            if (index1 < 0) {
                index1 = 0;
            } else if (index1 > state.size() - 1) {
               index1 = state.size() - 1;
            }
            if (index2 < 0) {
                index2 = 0;
            } else if (index2 > state.size() - 1) {
                index2 = state.size() - 1;
            }
            String temp = state.strings.get(index1);
            state.strings.set(index1, state.strings.get(index2));
            state.strings.set(index2, temp);
        }
        json.put("data", state.toString());
        return json;
    }

    public synchronized JSONObject quit() {
        JSONObject json = new JSONObject();
        json.put("datatype", 6);
        json.put("type", "quit");
        json.put("data", state.toString());
        return json;
    }

    public static JSONObject error(String err) {
        JSONObject json = new JSONObject();
        json.put("error", err);
        return json;
    }

    public void doPerform() {
        boolean quit = false;
        OutputStream out = null;
        InputStream in = null;
        try {
            out = conn.getOutputStream();
            in = conn.getInputStream();
            System.out.println("Server connected to client:");
            while (!quit) {
                byte[] messageBytes = NetworkUtils.receive(in);
                JSONObject message = JsonUtils.fromByteArray(messageBytes);
                JSONObject returnMessage = new JSONObject();
   
                int choice = message.getInt("selected");
                    switch (choice) {
                        case (1):
                            String inStr = (String) message.get("data");
                            returnMessage = add(inStr);
                            break;
                        case (2):
                            returnMessage = pop();
                            break;
                        case (3):
                            returnMessage = display();
                            break;
                        case (4):
                            returnMessage = count();
                            break;
                        case (5):
                            //need to parse out the two strings coming in
                            returnMessage = swap((String) message.get("data"));
                            break;
                        case (0):
                            quit = true;
                            returnMessage = quit();
                            break;
                        default:
                            returnMessage = error("Invalid selection: " + choice 
                                    + " is not an option");
                            break;
                    }
                // we are converting the JSON object we have to a byte[]
                byte[] output = JsonUtils.toByteArray(returnMessage);
                NetworkUtils.send(out, output);
            }
            // close the resource
            System.out.println("close the resources of client ");
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("close the resources of client ");
            //out.close();
            //in.close();
        }
    }
}

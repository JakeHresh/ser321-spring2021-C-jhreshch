package client;

import java.net.*;
import java.io.*;

import org.json.*;

import buffers.RequestProtos.Request;
import buffers.ResponseProtos.Response;
import buffers.ResponseProtos.Entry;

import java.util.*;
import java.util.stream.Collectors;

class SockBaseClient {

    public static void main (String args[]) throws Exception {
        Socket serverSock = null;
        OutputStream out = null;
        InputStream in = null;
        int i1=0, i2=0;
        int port = 9099; // default port

        // Make sure two arguments are given
        if (args.length != 2) {
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }
        String host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be integer");
            System.exit(2);
        }

        // Ask user for username
        System.out.println("Please provide your name for the server. ( ͡❛ ͜ʖ ͡❛)");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String strToSend = stdin.readLine();

        // Build the first request object just including the name
        Request op = Request.newBuilder()
                .setOperationType(Request.OperationType.NAME)
                .setName(strToSend).build();
        Response response;
        try {
            // connect to the server
            serverSock = new Socket(host, port);
            String serverState = "going";
            // write to the server
            out = serverSock.getOutputStream();
            in = serverSock.getInputStream();

            op.writeDelimitedTo(out);

            // read from the server
            response = Response.parseDelimitedFrom(in);

            // print the server response. 
            System.out.println(response.getGreeting());
            while(true)
            {
                try{
                    strToSend = stdin.readLine();
                while(!strToSend.equals("1") && !strToSend.equals("2") && !strToSend.equals("3"))
                {
                    strToSend = stdin.readLine();
                }
                if(strToSend.equals("1"))
                {
                    // send request to view leaderboard
                    // receive response with leaderboard
                    // receive response with greeting
                }
                if(strToSend.equals("2"))
                {
                    // send request to start game
                    op = Request.newBuilder()
                            .setOperationType(Request.OperationType.NEW).build();
                    op.writeDelimitedTo(out);
                    // initiate loop with its own set of requests and responses
                    while(true)
                    {
                        response = Response.parseDelimitedFrom(in);
                        if(response.getImage().equals("YOU WON!"))
                        {
                            System.out.println("YOU WON!");
                            break;
                        }
                        if(response.getEval())
                        {
                            System.out.println("CORRECT");
                        }
                        System.out.println(response.getImage());
                        System.out.println(response.getTask());
                        strToSend = stdin.readLine();
                        op = Request.newBuilder()
                            .setOperationType(Request.OperationType.ANSWER)
                            .setAnswer(strToSend).build();
                        op.writeDelimitedTo(out);
                    }
                    // when game ends, receive win response and break
                    response = Response.parseDelimitedFrom(in);

                    // print the server response. 
                    System.out.println(response.getGreeting());
                }
                if(strToSend.equals("3"))
                {
                    // send request to end game
                    op = Request.newBuilder()
                            .setOperationType(Request.OperationType.QUIT).build();
                    op.writeDelimitedTo(out);
                    // receive bye response
                    response = Response.parseDelimitedFrom(in);
                    System.out.println(response.getMessage());
                    // close client
                    break;
                }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null)   in.close();
            if (out != null)  out.close();
            if (serverSock != null) serverSock.close();
        }
    }
}



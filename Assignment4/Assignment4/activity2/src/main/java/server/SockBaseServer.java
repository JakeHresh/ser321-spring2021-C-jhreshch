package server;

import java.net.*;
import java.io.*;
import java.util.*;
import org.json.*;
import java.lang.*;

import buffers.RequestProtos.Request;
import buffers.RequestProtos.Logs;
import buffers.RequestProtos.Message;
import buffers.ResponseProtos.Response;
import buffers.ResponseProtos.Entry;

class SockBaseServer {
    static String logFilename = "logs.txt";

    ServerSocket serv = null;
    InputStream in = null;
    OutputStream out = null;
    Socket clientSocket = null;
    int port = 9099; // default port
    Game game;


    public SockBaseServer(Socket sock, Game game){
        this.clientSocket = sock;
        this.game = game;
        try {
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        } catch (Exception e){
            System.out.println("Error in constructor: " + e);
        }
    }
    public String[] pickTask()
    {
        Random rand = new Random();
        int taskSelection = rand.nextInt(10);
        String task[] = new String[2];
        if(taskSelection == 0)
        {
            task[0] = "Fill in the blank: Prince of ____ is the title of the videogame that appeared on Discovery Channel\'s How It\'s Made TV show.";
            task[1] = "persia";
            return task;
        }
        if(taskSelection == 1)
        {
            task[0] = "What is the last name of the whip-wielding warrior who wound up in the NES classic, Castlevania?";
            task[1] = "belmont";
            return task;
        }
        if(taskSelection == 2)
        {
            task[0] = "What is the last name of the famously overworked director of the Super Smash Bros. series?";
            task[1] = "sakurai";
            return task;
        }
        if(taskSelection == 3)
        {
            task[0] = "What is the last name of the attorney who defended Nintendo against Universal Studios over the copyright of Donkey Kong?";
            task[1] = "kirby";
            return task;
        }
        if(taskSelection == 4)
        {
            task[0] = "What is the Japanese name of the super fighting robot that battles Dr. Wily and his robot masters?";
            task[1] = "rockman";
            return task;
        }
        if(taskSelection == 5)
        {
            task[0] = "Fill in the blank: Stranded on an alien planet, Captain Olimar enlists the help of ____ to recover his missing ship parts within 30 days.";
            task[1] = "pikmin";
            return task;
        }
        if(taskSelection == 6)
        {
            task[0] = "What villain of the Super Mario Bros. series shares a name with the current president of Nintendo of America?";
            task[1] = "bowser";
            return task;
        }
        if(taskSelection == 7)
        {
            task[0] = "Finish this slogan: Genesis does what ____.";
            task[1] = "nintendon\'t";
            return task;
        }
        if(taskSelection == 8)
        {
            task[0] = "What is the American name of the cult-classic RPG Mother 2?";
            task[1] = "earthbound";
            return task;
        }
        task[0] = "While Super Mario has had many jobs, what professional occupation is he known for?";
        task[1] = "plumber";
        return task;
    }
    // Handles the communication right now it just accepts one input and then is done you should make sure the server stays open
    // can handle multiple requests and does not crash when the server crashes
    // you can use this server as based or start a new one if you prefer. 
    public void start() throws IOException {
        String name = "";


        System.out.println("Ready...");
        try {
                // read the proto object and put into new objct
                Request op = Request.parseDelimitedFrom(in);
                String result = null;

            

                // if the operation is NAME (so the beginning then say there is a commention and greet the client)
                if (op.getOperationType() == Request.OperationType.NAME) {
                    // get name from proto object
                    name = op.getName();

                    // writing a connect message to the log with name and CONNENCT
                    writeToLog(name, Message.CONNECT);
                    System.out.println("Got a connection and a name: " + name);
                    Response response = Response.newBuilder()
                            .setResponseType(Response.ResponseType.GREETING)
                            .setGreeting("Hello " + name + " and welcome. \nWhat would you like to do? \n 1 - to see the leader board \n 2 - to enter a game \n 3 - to quit")
                            .build();
                    response.writeDelimitedTo(out);
                }
                while(true)
                {
                    boolean answeredCorrectly = false;
                    String qAndA[] = pickTask();
                    op = Request.parseDelimitedFrom(in);
                    if(op.getOperationType() == Request.OperationType.LEADER) // view leaderboard
                    {

                    }
                    if(op.getOperationType() == Request.OperationType.NEW) // enter game
                    {
                        game.newGame(); // starting a new game
                        while(game.getIdx() < game.getIdxMax())
                        {
                            Response response2;
                            //String qAndA[] = pickTask();
                            // adding the String of the game to 
                            if(answeredCorrectly)
                            {
                                response2 = Response.newBuilder()
                                    .setResponseType(Response.ResponseType.TASK)
                                    .setImage(game.replaceOneCharacter())
                                    .setTask(qAndA[0])
                                    .setEval(true)
                                    .build();
                                answeredCorrectly = false;
                            }
                            else
                            {
                                response2 = Response.newBuilder()
                                    .setResponseType(Response.ResponseType.TASK)
                                    .setImage(game.getImage())
                                    .setTask(qAndA[0])
                                    .setEval(false)
                                    .build();
                            }
                            
                            response2.writeDelimitedTo(out);
                            op = Request.parseDelimitedFrom(in);
                            String answer = op.getAnswer().toLowerCase();
                            System.out.println(answer);
                            if(answer.equals(qAndA[1]))
                            {
                                answeredCorrectly = true;
                            }
                            qAndA = pickTask();
                        }
                        game.setWon();
                        Response response = Response.newBuilder()
                                    .setResponseType(Response.ResponseType.WON)
                                    .setImage("YOU WON!")
                                    .build();
                        response.writeDelimitedTo(out);
                        response = Response.newBuilder()
                            .setResponseType(Response.ResponseType.GREETING)
                            .setGreeting("Hello " + name + " and welcome. \nWhat would you like to do? \n 1 - to see the leader board \n 2 - to enter a game \n 3 - to quit")
                            .build();
                        response.writeDelimitedTo(out);
                    }
                    if(op.getOperationType() == Request.OperationType.QUIT) {
                        System.out.println("Client quitting");
                        Response response = Response.newBuilder()
                            .setResponseType(Response.ResponseType.BYE)
                            .setMessage("Thanks for playing! See you next time!")
                            .build();
                        response.writeDelimitedTo(out);
                        break;
                    }
                }
                /*
                // Example how to start a new game and how to build a response with the image which you could then send to the server
                // LINE 67-108 are just an example for Protobuf and how to work with the differnt types. They DO NOT
                // belong into this code. 
                game.newGame(); // starting a new game

                // adding the String of the game to 
                Response response2 = Response.newBuilder()
                    .setResponseType(Response.ResponseType.TASK)
                    .setImage(game.getImage())
                    .setTask("Great task goes here")
                    .build();

                // On the client side you would receive a Response object which is the same as the one in line 70, so now you could read the fields
                System.out.println("Task: " + response2.getResponseType());
                System.out.println("Image: \n" + response2.getImage());
                System.out.println("Task: \n" + response2.getTask());

                // Creating Entry and Leader response
                Response.Builder res = Response.newBuilder()
                    .setResponseType(Response.ResponseType.LEADER);

                // building and Entry
                Entry leader = Entry.newBuilder()
                    .setName("name")
                    .setWins(0)
                    .setLogins(0)
                    .build();

                // building and Entry
                Entry leader2 = Entry.newBuilder()
                    .setName("name2")
                    .setWins(1)
                    .setLogins(1)
                    .build();

                res.addLeader(leader);
                res.addLeader(leader2);

                Response response3 = res.build();

                for (Entry lead: response3.getLeaderList()){
                    System.out.println(lead.getName() + ": " + lead.getWins());
                }*/

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (out != null)  out.close();
                if (in != null)   in.close();
                if (clientSocket != null) clientSocket.close();
            }
    }

    /**
     * Replaces num characters in the image. I used it to turn more than one x when the task is fulfilled
     * @param num -- number of x to be turned
     * @return String of the new hidden image
     */
    public String replace(int num){
        for (int i = 0; i < num; i++){
            if (game.getIdx()< game.getIdxMax())
                game.replaceOneCharacter();
        }
        return game.getImage();
    }


    /**
     * Writing a new entry to our log
     * @param name - Name of the person logging in
     * @param message - type Message from Protobuf which is the message to be written in the log (e.g. Connect) 
     * @return String of the new hidden image
     */
    public static void writeToLog(String name, Message message){
        try {
            // read old log file 
            Logs.Builder logs = readLogFile();

            // get current time and data
            Date date = java.util.Calendar.getInstance().getTime();

            // we are writing a new log entry to our log
            // add a new log entry to the log list of the Protobuf object
            logs.addLog(date.toString() + ": " +  name + " - " + message);

            // open log file
            FileOutputStream output = new FileOutputStream(logFilename);
            Logs logsObj = logs.build();

            // This is only to show how you can iterate through a Logs object which is a protobuf object
            // which has a repeated field "log"

            for (String log: logsObj.getLogList()){

                System.out.println(log);
            }

            // write to log file
            logsObj.writeTo(output);
        }catch(Exception e){
            System.out.println("Issue while trying to save");
        }
    }

    /**
     * Reading the current log file
     * @return Logs.Builder a builder of a logs entry from protobuf
     */
    public static Logs.Builder readLogFile() throws Exception{
        Logs.Builder logs = Logs.newBuilder();

        try {
            // just read the file and put what is in it into the logs object
            return logs.mergeFrom(new FileInputStream(logFilename));
        } catch (FileNotFoundException e) {
            System.out.println(logFilename + ": File not found.  Creating a new file.");
            return logs;
        }
    }


    public static void main (String args[]) throws Exception {
        Game game = new Game();

        if (args.length != 2) {
            System.out.println("Expected arguments: <port(int)> <delay(int)>");
            System.exit(1);
        }
        int port = 9099; // default port
        int sleepDelay = 10000; // default delay
        Socket clientSocket = null;
        ServerSocket serv = null;

        try {
            port = Integer.parseInt(args[0]);
            sleepDelay = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port|sleepDelay] must be an integer");
            System.exit(2);
        }
        try {
            serv = new ServerSocket(port);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

        clientSocket = serv.accept();
        SockBaseServer server = new SockBaseServer(clientSocket, game);
        server.start();

    }
}


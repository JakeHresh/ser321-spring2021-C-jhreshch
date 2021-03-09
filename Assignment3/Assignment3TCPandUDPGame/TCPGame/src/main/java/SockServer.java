import java.net.*;
import java.io.*;
import java.util.regex.Pattern;
/**
 * A class to demonstrate a simple client-server connection using sockets.
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version August 2020
 * 
 * @modified-by David Clements <dacleme1@asu.edu> September 2020
 * @modified-by Jacob Hreshchyshyn <jhreshch@asu.edu> March 2021
 */
public class SockServer {
  public static void main (String args[]) {
    Socket sock;
    int port = 8080;
    String serverState = "going";
    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    long timeLimit = 0;
    if (args.length >= 1){ // host, if provided
      port = Integer.parseInt(args[0]);
    }
    String previousGameResult = "";
    String loser = "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@                                                             @\n@  @@&        @@@@@@@@@@*   @@@@@@@@   @@@@@@@@   @@@@@@@@@   @\n@  @@&       @@@      #@@%  @@@        @@@        @@&   @@@   @\n@  @@&       @@@       @@@    @@@@@    @@@@@@@#   @@@@@@@@    @\n@  @@&       @@@      @@@.        @@&  @@@        @@&  @@@    @\n@  @@@@@@@@   *@@@@@@@@@    @@@@@@@@   @@@@@@@@   @@&   &@@/  @\n@                                                             @\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n";
    String winner = "(@@@         @@@@        @@@@@@@@@@@@@        @@@.            &@@@\n(@@@         @@@@    /(((,,,,,,,,,,,,,(((,    @@@.            &@@@\n(@@@         @@@@    #@@@             @@@*    @@@.            &@@@\n    @@@   /@@/       #@@@             @@@*    @@@.            &@@@\n    ..,@@@(...       #@@@             @@@*    @@@.            &@@@\n      .@@@/          #@@@             @@@*    @@@.            &@@@      .@@@/          #@@@             @@@*    @@@.            &@@@\n      .@@@/          #@@@             @@@*    @@@.            &@@@\n      .@@@/              @@@@@@@@@@@@@           #@@@@@@@@@@@@.   \n                                                                   \n/@@@   @@@@  .@@@.             @@@            ,@@@@@@          &@@(\n/@@@   @@@@  .@@@.             @@@            ,@@@@@@          &@@(\n/@@@   @@@@  .@@@.             @@@            ,@@&***(##*      &@@(\n/@@@   @@@@  .@@@.             @@@            ,@@%   @@@(      &@@(\n/@@@   @@@@  .@@@.             @@@            ,@@%      *@@@   &@@(\n/@@@   @@@@  .@@@.             @@@            ,@@%          @@@@@@(\n    @@@    @@&                 @@@            ,@@%          @@@@@@(\n    &&&    */*                 &&@            *@@%          &&&&&&(\n";
    boolean didWin = true;
    try {
      //open socket                      //port was originally 8080
      ServerSocket serv = new ServerSocket(port); // create server socket on port 8888
      //System.out.println("Server ready for 3 connections");
      // only does three connections then closes
      // NOTE: SINGLE-THREADED, only one connection at a time
      //for (int rep = 0; rep < 3; rep++){
      didWin = true;
      System.out.println("Server waiting for a connection");
      sock = serv.accept(); // blocking wait
        // setup the object reading channel
      ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
      // get output channel
      OutputStream out = sock.getOutputStream();
      // create an object output writer (Java only)
      ObjectOutputStream os = new ObjectOutputStream(out);
      while(serverState.equals("going"))
      {
        //System.out.println("Server waiting for a connection");
        //sock = serv.accept(); // blocking wait
        // setup the object reading channel
        //ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        
        // read in one object, the message. we know a string was written only by knowing what the client sent. 
        // must cast the object from Object to desired type to be useful
        String s = (String) in.readObject();
        System.out.println("Received the String "+s);
        // read in the number, we know it's an integer because that's the second thing sent by the client.
        Integer i = (Integer) in.readObject();
        System.out.println("Received the Integer "+ i);

        // generate an output
        // get output channel
        //OutputStream out = sock.getOutputStream();
        // create an object output writer (Java only)
        //ObjectOutputStream os = new ObjectOutputStream(out);
        while(true)
        {
            // write the whole message
            os.writeObject(previousGameResult + "Hello! What's your name? (type stop to stop playing)");
            // make sure it wrote and doesn't get cached in a buffer
            os.flush();
            // possibly have another loop inside to do the question stuff
            // that way, there can still be the option of entering a name to
            // start a new game
	    s = (String) in.readObject();
            System.out.println(s);
            String stopCheck = s.toLowerCase();
            if(stopCheck.equals("stop"))
            {
                serverState = "stopped";
                break;
            }
            os.writeObject("Hi " + s + "! How many questions would you like me to ask?");
            os.flush();
            s = (String) in.readObject();
            System.out.println(s);
            while(!pattern.matcher(s).matches())
            {
                os.writeObject("Please type in a valid number.");
                os.flush();
                s = (String) in.readObject();
                System.out.println(s);
            }
            int numQuestions = Integer.parseInt(s);
            timeLimit = 5000 * numQuestions;//5000 miliseconds is 5 seconds
            if(numQuestions <= 0)
            {
                os.writeObject("Number of questions can't be less than or equal to 0. Setting number of questions to 5. Type anything to continue.");
                os.flush();
                numQuestions = 5;
                in.readObject();
            }
            else if(numQuestions > 10)
            {
                os.writeObject("Too many questions. Capping the number of questions at 10. Type anything to continue.");
                os.flush();
                numQuestions = 10;
                in.readObject();
            }
            os.writeObject("Alright then! Please type start to begin the game and answer the first question out of " + numQuestions );
            os.flush();
            s = (String) in.readObject();
            System.out.println(s);
            s = s.toLowerCase();
            while(!s.equals("start"))
            {
                os.writeObject("Please type start to begin the game.");
                os.flush();
                s = (String) in.readObject();
                System.out.println(s);
                s = s.toLowerCase();
            }
	    long currentTime = System.currentTimeMillis();
            int questionIndex = 0;
            String question = "";
            String answer = "";
            String response = "";
            for(int j = 0; j < numQuestions; j++)
            {
                if(questionIndex == 0)
                {
                    question = response + "Fill in the blank: Prince of ____ is the title of the videogame that appeared on Discovery Channel\'s How It\'s Made TV show.";
            	    answer = "persia";
	        }
	        else if(questionIndex == 1)
                {
		    question = response + "What is the last name of the whip-wielding warrior who wound up in the NES classic, Castlevania?";
		    answer = "belmont";
                }
                else if(questionIndex == 2)
                {
		    question = response + "What is the last name of the famously overworked director of the Super Smash Bros. series?";
		    answer = "sakurai";
                }
                else if(questionIndex == 3)
                {
		    question = response + "What is the last name of the attorney who defended Nintendo against Universal Studios over the copyright of Donkey Kong?";
                    answer = "kirby";
                }
                else if(questionIndex == 4)
                {
   		    question = response + "What is the Japanese name of the super fighting robot that battles Dr. Wily and his robot masters?";
		    answer = "rockman";
                }
                else if(questionIndex == 5)
                {
		    question = response + "Fill in the blank: Stranded on an alien planet, Captain Olimar enlists the help of ____ to recover his missing ship parts within 30 days.";
		    answer = "pikmin";
                }
                else if(questionIndex == 6)
                {
		    question = response + "What villain of the Super Mario Bros. series shares a name with the current president of Nintendo of America?";
		    answer = "bowser";
                }
                else if(questionIndex == 7)
                {
		    question = response + "Finish this slogan: Genesis does what ____.";
		    answer = "nintendon\'t";
                }
                else if(questionIndex == 8)
                {
		    question = response + "What is the American name of the cult-classic RPG Mother 2?";
		    answer = "earthbound";
                }
                else if(questionIndex == 9)
                {
		    question = response + "While Super Mario has had many jobs, what professional occupation is he known for?";
		    answer = "plumber";
                }
                os.writeObject("" + question);
	        os.flush();
                System.out.println("Answer: " + answer);
	        s = (String) in.readObject();
                long difference = System.currentTimeMillis() - currentTime;
                System.out.println(s);
                s = s.toLowerCase();
                if(difference >= timeLimit)
                {
                    System.out.println("Out of time");
                    previousGameResult = loser;
                    didWin = false;
                    break;
                }
                if(s.equals(answer))
                {
		    response = "Correct!\n";
                    if(questionIndex < 9)
                    {
                        questionIndex++;
                    }
                    else
                    {
                        questionIndex = 0;
                    }
                }
                else if(s.equals("next"))
                {
		    response = "Try this question!\n";
                    j--;
                    if(questionIndex < 9)
                    {
                        questionIndex++;
                    }
                    else
                    {
                        questionIndex = 0;
                    }
                }
                else if(!s.equals(answer))
                {
                    response = "WRONG!!! :P :P :P :P :P :P :P :P\n";
                    j--;
                }
            }
            if(didWin)
                previousGameResult = winner;
         }
      }
      os.writeObject("stop");
      os.flush();
      sock.close();
    } catch(Exception e) {
	System.out.println("There's been a connection error with the Client. Printing stack trace...");
        e.printStackTrace();

    }
  }
}

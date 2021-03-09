package fauxSolution.udp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

import org.json.*;
import java.util.regex.Pattern;

public class Server {
  /*
   * request: { "selected": <int: 1=joke, 2=quote, 3=image, 4=random>,
   * (optional)"min": <int>, (optional)"max":<int> }
   * 
   * response: {"datatype": <int: 1-string, 2-byte array>, "type": <"joke",
   * "quote", "image"> "data": <thing to return> }
   * 
   * error response: {"error": <error string> }
   */
  static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
  public static JSONObject joke() {
    JSONObject json = new JSONObject();
    json.put("datatype", 1);
    json.put("type", "joke");
    json.put("data", "What does a baby computer call its father? Data.");
    return json;
  }

  public static JSONObject quote() {
    JSONObject json = new JSONObject();
    json.put("datatype", 1);
    json.put("type", "quote");
    json.put("data",
        "A good programmer is someone who always looks both ways before crossing a one-way street. (Doug Linder)");
    return json;
  }

  public static JSONObject image() throws IOException {
    JSONObject json = new JSONObject();
    json.put("datatype", 2);

    json.put("type", "image");

    File file = new File("img/To-Funny-For-Words1.png");
    if (!file.exists()) {
      System.err.println("Cannot find file: " + file.getAbsolutePath());
      System.exit(-1);
    }
    // Read in image
    BufferedImage img = ImageIO.read(file);
    byte[] bytes = null;
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      ImageIO.write(img, "png", out);
      bytes = out.toByteArray();
    }
    if (bytes != null) {
      Base64.Encoder encoder = Base64.getEncoder();
      json.put("data", encoder.encodeToString(bytes));
      return json;
    }
    return error("Unable to save image to byte array");
  }

  public static JSONObject random() throws IOException {
    Random rand = new Random();
    int random = rand.nextInt(3);
    JSONObject json = new JSONObject();
    if (random == 0) {
      json = joke();
    } else if (random == 1) {
      json = quote();
    } else if (random == 2) {
      json = image();
    }
    return json;
  }

  public static JSONObject error(String err) {
    JSONObject json = new JSONObject();
    json.put("error", err);
    return json;
  }
  public static JSONObject greet() {
    JSONObject json = new JSONObject();
    json.put("datatype", 1);
    json.put("type", "greeting");
    json.put("data", "Hello! What's your name?");
    return json;
  }
  public static void main(String[] args) throws IOException {
    DatagramSocket sock = null;
    int port = 8080;
    boolean haveName = false;
    boolean haveNumQuestions = false;
    boolean firstAnswer = false;
    if(pattern.matcher(args[0]).matches())
      port = Integer.parseInt(args[0]);
    int numQuestions = 1;
    int questionCount = 0;
    try {
      sock = new DatagramSocket(port);
      // NOTE: SINGLE-THREADED, only one connection at a time
      while (true) {
        try {
          while (true) {
            NetworkUtils.Tuple messageTuple = NetworkUtils.Receive(sock);
            JSONObject message = JsonUtils.fromByteArray(messageTuple.Payload);
            JSONObject returnMessage;
            if (message.has("selected")) {
              if ((message.get("selected") instanceof Long || message.get("selected") instanceof Integer) && !haveName) {
                int choice = message.getInt("selected");
                switch (choice) {
                case (1):
                  returnMessage = greet();
                  break;
                case (2):
                  returnMessage = quote();
                  break;
                case (3):
                  returnMessage = image();
                  break;
                case (4):
                  returnMessage = random();
                  break;
                default:
                  returnMessage = error("Invalid selection: " + choice + " is not an option");
                }
              } else {
                returnMessage = error("Game Over");
                String response = "Incorrect. ";
                //THIS IS THE SECTION WHERE WE WORK
                if(haveName && !haveNumQuestions)
                {
                  String numQuestionsString = message.getString("selected");
                  if(pattern.matcher(numQuestionsString).matches())
                    numQuestions = Integer.parseInt(numQuestionsString);
                  if(numQuestions <= 0)
                    numQuestions = 1;
                  if(numQuestions > 10)
                    numQuestions = 10;
                  returnMessage = error("The minimum number of questions is 1 and the maximum is 10. We will play for " + numQuestions + " question(s). Type anything to begin.");
                  haveNumQuestions = true;
                }
                else if(!haveName)
                {
                  String name = message.getString("selected");
                  System.out.println(name);
                  returnMessage = error("Hi " + name + "! How many questions would you like me to ask?");
                  haveName = true;
                }
                else
                {
                  if(!firstAnswer)// don't evaluate answer
                  {
                    firstAnswer = true;
                    questionCount++;
                    returnMessage = error("Fill in the blank: Prince of ____ is the title of the videogame that appeared on Discovery Channel\'s How It\'s Made TV show.");
                  }
                  else if(questionCount < numQuestions)// evaluate answer
                  {
                    String question = "";
                    String receivedAnswer = message.getString("selected");
                    receivedAnswer = receivedAnswer.toLowerCase();
                    if(questionCount == 1)
                    {
                      System.out.println("persia");
                      if(receivedAnswer.equals("persia"))
                        response = "Correct! ";
                      question = response + "What is the last name of the whip-wielding warrior who wound up in the NES classic, Castlevania?";
                    }
                    if(questionCount == 2)
                    {
                      System.out.println("belmont");
                      if(receivedAnswer.equals("belmont"))
                        response = "Correct! ";
                      question = response + "What is the last name of the famously overworked director of the Super Smash Bros. series?";
                    }
                    if(questionCount == 3)
                    {
                      System.out.println("sakurai");
                      if(receivedAnswer.equals("sakurai"))
                        response = "Correct! "; 
                      question = response + "What is the last name of the attorney who defended Nintendo against Universal Studios over the copyright of Donkey Kong?";
                    }
                    if(questionCount == 4)
                    {
                      System.out.println("kirby");
                      if(receivedAnswer.equals("kirby"))
                        response = "Correct! ";
                      question = response + "What is the Japanese name of the super fighting robot that battles Dr. Wily and his robot masters?";
                    }
                    if(questionCount == 5)
                    {
                      System.out.println("rockman");
                      if(receivedAnswer.equals("rockman"))
                        response = "Correct! ";
                      question = response + "Fill in the blank: Stranded on an alien planet, Captain Olimar enlists the help of ____ to recover his missing ship parts within 30 days.";
                    }
                    if(questionCount == 6)
                    {
                      System.out.println("pikmin");
                      if(receivedAnswer.equals("pikmin"))
                        response = "Correct! ";
                      question = response + "What villain of the Super Mario Bros. series shares a name with the current president of Nintendo of America?";
                    }
                    if(questionCount == 7)
                    {
                      System.out.println("bowser");
                      if(receivedAnswer.equals("bowser"))
                        response = "Correct! ";
                      question = response + "Finish this slogan: Genesis does what ____.";
                    }
                    if(questionCount == 8)
                    {
                      System.out.println("nintendon\'t");
                      if(receivedAnswer.equals("nintendon\'t"))
                        response = "Correct! ";
                      question = response + "What is the American name of the cult-classic RPG Mother 2?";
                    }
                    if(questionCount == 9)
                    {
                      System.out.println("earthbound");
                      if(receivedAnswer.equals("earthbound"))
                        response = "Correct! ";
                      question = response + "While Super Mario has had many jobs, what professional occupation is he known for?";
                    }
                    if(questionCount == 10)
                    {
                      System.out.println("plumber");
                      if(receivedAnswer.equals("plumber"))
                        response = "Correct! ";
                    }
                    questionCount++;
                    returnMessage = error(question);
                  }
                }
              }
            } else {
              returnMessage = error("Invalid message received");
            }

            byte[] output = JsonUtils.toByteArray(returnMessage);
            NetworkUtils.Send(sock, messageTuple.Address, messageTuple.Port, output);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (sock != null) {
        sock.close();
      }
    }
  }
}

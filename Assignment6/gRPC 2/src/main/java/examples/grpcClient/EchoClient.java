package example.grpcclient;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import service.*;
import test.TestProtobuf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class EchoClient {
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
  //HAVE BLOCKING STUB FOR CALC SERVICE
  private final CalcGrpc.CalcBlockingStub blockingStub4;
  //HAVE BLOCKING STUB FOR TIPS SERVICE
  private final TipsGrpc.TipsBlockingStub blockingStub5;
  //HAVE BLOCKING STUB FOR PEOPLE SERVICE
  private final PeopleGrpc.PeopleBlockingStub blockingStub6;

  /** Construct client for accessing server using the existing channel. */
  public EchoClient(Channel channel, Channel regChannel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
    blockingStub4 = CalcGrpc.newBlockingStub(channel);
    blockingStub5 = TipsGrpc.newBlockingStub(channel);
    blockingStub6 = PeopleGrpc.newBlockingStub(channel);
  }

  public void askServerToParrot(String message) {
    ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
    ServerResponse response;
    try {
      response = blockingStub.parrot(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e.getMessage());
      return;
    }
    System.out.println("Received from server: " + response.getMessage());
  }

  public void askForJokes(int num) {
    JokeReq request = JokeReq.newBuilder().setNumber(num).build();
    JokeRes response;

    try {
      response = blockingStub2.getJoke(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your jokes: ");
    for (String joke : response.getJokeList()) {
      System.out.println("--- " + joke);
    }
  }

  public void setJoke(String joke) {
    JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
    JokeSetRes response;

    try {
      response = blockingStub2.setJoke(request);
      System.out.println(response.getOk());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub3.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServer(String name) {
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    try {
      response = blockingStub3.findServer(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServers(String name) {
    FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
    ServerListRes response;
    try {
      response = blockingStub3.findServers(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }
  // Calc Wrapper Functions
  public void requestAdd(ArrayList<Double> nums) {
    CalcRequest.Builder request = CalcRequest.newBuilder();
    for (int i=0; i < nums.size(); i++){
        request.addNum(nums.get(i).doubleValue());
    }
    CalcRequest req = request.build();
    CalcResponse response;
    try {
      response = blockingStub4.add(req);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Result of adding: " + response.getSolution());
  }

  public void requestSubtract(ArrayList<Double> nums) {
    CalcRequest.Builder request = CalcRequest.newBuilder();
    for (int i=0; i < nums.size(); i++){
        request.addNum(nums.get(i).doubleValue());
    }
    CalcRequest req = request.build();
    CalcResponse response;
    try {
      response = blockingStub4.subtract(req);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Result of subtracting: " + response.getSolution());
  }

  public void requestMultiply(ArrayList<Double> nums) {
    CalcRequest.Builder request = CalcRequest.newBuilder();
    for (int i=0; i < nums.size(); i++){
        request.addNum(nums.get(i).doubleValue());
    }
    CalcRequest req = request.build();
    CalcResponse response;
    try {
      response = blockingStub4.multiply(req);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Result of multiplying: " + response.getSolution());
  }

  public void requestDivide(ArrayList<Double> nums) {
    CalcRequest.Builder request = CalcRequest.newBuilder();
    for (int i=0; i < nums.size(); i++){
        request.addNum(nums.get(i).doubleValue());
    }
    CalcRequest req = request.build();
    CalcResponse response;
    try {
      response = blockingStub4.divide(req);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    if(!response.getIsSuccess())
      System.out.println("Denominator sum is 0. Cannot divide.");
    else
      System.out.println("Result of dividing: " + response.getSolution());
  }

  public void requestReadTips() {
    Tip.Builder request = Tip.newBuilder();
    /*for (int i=0; i < nums.size(); i++){
        request.addNum(nums.get(i).doubleValue());
    }*/
    //Tip req = request.build();
    TipsReadResponse response;
    try {
      response = blockingStub5.read(null);//was req
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your tips: ");
    for (Tip t : response.getTipsList()) {
      System.out.print(t.getName() + ": ");
      System.out.println(t.getTip());
    }
  }

  public synchronized void requestWriteTips(String name, String tip) {
    TipsWriteRequest.Builder request = TipsWriteRequest.newBuilder();
    Tip.Builder tipContainer = Tip.newBuilder();
    tipContainer.setName(name);
    tipContainer.setTip(tip);
    /*for (int i=0; i < nums.size(); i++){
        request.addNum(nums.get(i).doubleValue());
    }*/
    Tip t = tipContainer.build();
    request.setTip(t);
    TipsWriteRequest req = request.build();
    TipsWriteResponse response;
    try {
      response = blockingStub5.write(req);//was req
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Success!");
  }

  public void requestReadPeopleById(String id) {
    PeopleReadByIdRequest.Builder request = PeopleReadByIdRequest.newBuilder();
    request.setId(id);
    PeopleReadByIdRequest req = request.build();
    PeopleResponse response;
    try {
      response = blockingStub6.readbyid(req);//was req
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your people: ");
    /*for (String s : response.getNameList()) {
      System.out.print(t.getName() + ": ");
      System.out.println(t.getTip());
    }*/
    for(int i = 0; i < response.getNameCount(); i++)
    {
      System.out.println("Name: " + response.getName(i));
      System.out.println("ID: " + response.getId(i));
    }
  }

  public void requestReadPeopleByName(String id) {
    PeopleReadByNameRequest.Builder request = PeopleReadByNameRequest.newBuilder();
    request.setName(id);
    PeopleReadByNameRequest req = request.build();
    PeopleResponse response;
    try {
      response = blockingStub6.readbyname(req);//was req
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your people: ");
    /*for (String s : response.getNameList()) {
      System.out.print(t.getName() + ": ");
      System.out.println(t.getTip());
    }*/
    for(int i = 0; i < response.getNameCount(); i++)
    {
      System.out.println("Name: " + response.getName(i));
      System.out.println("ID: " + response.getId(i));
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 6) {
      System.out
          .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)> <pauto(String)>");
      System.exit(1);
    }
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
    String auto = "0";
    auto = args[5];
    try {
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port] must be an integer");
      System.exit(2);
    }

    // Create a communication channel to the server, known as a Channel. Channels
    // are thread-safe
    // and reusable. It is common to create channels at the beginning of your
    // application and reuse
    // them until the application shuts down.
    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS
        // to avoid
        // needing certificates.
        .usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
    try {

      // ##############################################################################
      // ## Assume we know the port here from the service node it is basically set through Gradle
      // here.
      // In your version you should first contact the registry to check which services
      // are available and what the port
      // etc is.

      /**
       * Your client should start off with 
       * 1. contacting the Registry to check for the available services
       * 2. List the services in the terminal and the client can
       *    choose one (preferably through numbering) 
       * 3. Based on what the client chooses
       *    the terminal should ask for input, eg. a new sentence, a sorting array or
       *    whatever the request needs 
       * 4. The request should be sent to one of the
       *    available services (client should call the registry again and ask for a
       *    Server providing the chosen service) should send the request to this service and
       *    return the response in a good way to the client
       * 
       * You should make sure your client does not crash in case the service node
       * crashes or went offline.
       */

      // Just doing some hard coded calls to the service node without using the
      // registry
      // create client
      EchoClient client = new EchoClient(channel, regChannel);

      // call the parrot service on the server
      /*client.askServerToParrot(message);

      // ask the user for input how many jokes the user wants
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      // Reading data using readLine
      System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
      String num = reader.readLine();

      // calling the joked service from the server with num from user input
      client.askForJokes(Integer.valueOf(num));

      // adding a joke to the server
      client.setJoke("I made a pencil with two erasers. It was pointless.");

      // showing 6 joked
      client.askForJokes(Integer.valueOf(6));*/

      client.requestReadPeopleById("1");
      client.requestReadPeopleByName("");

      if(!auto.equals("1"))
      {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello! Please select a service.");
        System.out.println("1. Ask server to parrot");
        System.out.println("2. Ask for jokes");
        System.out.println("3. Set joke");
        System.out.println("4. Add numbers");
        System.out.println("5. Subtract numbers");
        System.out.println("6. Multiply numbers");
        System.out.println("7. Divide numbers");
        System.out.println("8. Get tips");
        System.out.println("9. Add tip");
        System.out.println("10. Request People By ID");
        System.out.println("11. Request People By Name");
        System.out.println("0. Quit");
        String input = reader.readLine();
        while(!input.equals("0"))
        {
          System.out.println("1. Ask server to parrot");
          System.out.println("2. Ask for jokes");
          System.out.println("3. Set joke");
          System.out.println("4. Add numbers");
          System.out.println("5. Subtract numbers");
          System.out.println("6. Multiply numbers");
          System.out.println("7. Divide numbers");
          System.out.println("8. Get tips");
          System.out.println("9. Add tip");
          System.out.println("10. Request People By ID");
          System.out.println("11. Request People By Name");
          System.out.println("0. Quit");
          //input = reader.readLine();
          if(input.equals("1"))
          {
            client.askServerToParrot(message);
          }
          else if(input.equals("2"))
          {
            // Reading data using readLine
            System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            String num = reader.readLine();
            while(!pattern.matcher(num).matches())
            {
              System.out.println("Invalid input. How many jokes would you like?");
              num = reader.readLine();
            }
            // calling the joked service from the server with num from user input
            client.askForJokes(Integer.valueOf(num));
          }
          else if(input.equals("3"))
          {
            System.out.println("Enter your joke.");
            client.setJoke(reader.readLine());
          }
          else if(input.equals("4"))
          {
            System.out.println("Please enter numbers to add. When done, type anything.");
            ArrayList<Double> arr = new ArrayList<Double>();
            int count = 0;
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            String num = reader.readLine();
            while(pattern.matcher(num).matches())
            {
              System.out.println("Place another number to include another number in the calculation.");
              arr.add(Double.valueOf(num));
              num = reader.readLine();
              count++;
            }
            if(count == 0)
            {
              arr.add(Double.valueOf(0.0));
              arr.add(Double.valueOf(0.0));
            }
            else if(count == 1)
            {
              arr.add(Double.valueOf(0.0));
            }
            client.requestAdd(arr);
          }
          else if(input.equals("5"))
          {
            System.out.println("Please enter numbers to subtract. When done, type anything.");
            ArrayList<Double> arr = new ArrayList<Double>();
            int count = 0;
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            String num = reader.readLine();
            while(pattern.matcher(num).matches())
            {
              System.out.println("Place another number to include another number in the calculation.");
              arr.add(Double.valueOf(num));
              num = reader.readLine();
              count++;
            }
            if(count == 0)
            {
              arr.add(Double.valueOf(0.0));
              arr.add(Double.valueOf(0.0));
            }
            else if(count == 1)
            {
              arr.add(Double.valueOf(0.0));
            }
            client.requestSubtract(arr);
          }
          else if(input.equals("6"))
          {
            System.out.println("Please enter numbers to multiply. When done, type anything.");
            ArrayList<Double> arr = new ArrayList<Double>();
            int count = 0;
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            String num = reader.readLine();
            while(pattern.matcher(num).matches())
            {
              System.out.println("Place another number to include another number in the calculation.");
              arr.add(Double.valueOf(num));
              num = reader.readLine();
              count++;
            }
            if(count == 0)
            {
              arr.add(Double.valueOf(0.0));
              arr.add(Double.valueOf(0.0));
            }
            else if(count == 1)
            {
              arr.add(Double.valueOf(0.0));
            }
            client.requestMultiply(arr);
          }
          else if(input.equals("7"))
          {
            System.out.println("Please enter numbers to divide. When done, type anything.");
            ArrayList<Double> arr = new ArrayList<Double>();
            int count = 0;
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            String num = reader.readLine();
            while(pattern.matcher(num).matches())
            {
              System.out.println("Place another number to include another number in the calculation.");
              arr.add(Double.valueOf(num));
              num = reader.readLine();
              count++;
            }
            if(count == 0)
            {
              arr.add(Double.valueOf(0.0));
              arr.add(Double.valueOf(0.0));
            }
            else if(count == 1)
            {
              arr.add(Double.valueOf(0.0));
            }
            client.requestDivide(arr);
          }
          else if(input.equals("8"))
          {
            client.requestReadTips();
          }
          else if(input.equals("9"))
          {
            System.out.println("Who is giving the tip?");
            String n = reader.readLine();
            System.out.println("What is the tip?");
            String t = reader.readLine();
            client.requestWriteTips(n, t);
          }
          else if(input.equals("10"))
          {
            System.out.println("Please enter an ID.");
            String id = reader.readLine();
            client.requestReadPeopleById(id);
          }
          else if(input.equals("11"))
          {
            System.out.println("Please enter a name.");
            String n = reader.readLine();
            client.requestReadPeopleByName(n);
          }
          else if(!input.equals("0"))
          {
            System.out.println("Invalid input.");
          }
          input = reader.readLine();
        }
      }
      else
      {
        System.out.println("Parroting");
        client.askServerToParrot(message);
        System.out.println("Asking for 2 jokes");
        client.askForJokes(Integer.valueOf(2));
        System.out.println("Setting joke");
        client.setJoke("A new joke");
        System.out.println("Adding 1, 2, and 3");
        ArrayList<Double> arr = new ArrayList<Double>();
        arr.add(Double.valueOf(1.0));
        arr.add(Double.valueOf(2.0));
        arr.add(Double.valueOf(3.0));
        client.requestAdd(arr);
        System.out.println("Subtracting 1, 2, and 3");
        client.requestSubtract(arr);
        System.out.println("Multiplying 1, 2, and 3");
        client.requestMultiply(arr);
        System.out.println("Dividing 1, 2, and 3");
        client.requestDivide(arr);
        System.out.println("Adding tip with name Mary");
        client.requestWriteTips("Mary", "My tip to you is this...");
        System.out.println("Getting tips");
        client.requestReadTips();
        System.out.println("Searching people by id 1");
        client.requestReadPeopleById("1");
        System.out.println("Searching people by name Mary");
        client.requestReadPeopleByName("Mary");
      }
      System.out.println("Client Shutting Down...");
      // ############### Contacting the registry just so you see how it can be done

      // Comment these last Service calls while in Activity 1 Task 1, they are not needed and wil throw issues without the Registry running
      // get thread's services
      //client.getServices();

      // get parrot
      //client.findServer("services.Echo/parrot");
      
      // get all setJoke
      //client.findServers("services.Joke/setJoke");

      // get getJoke
      //client.findServer("services.Joke/getJoke");

      /*ArrayList<Double> arr = new ArrayList<Double>();
      arr.add(Double.valueOf(3.0));
      arr.add(Double.valueOf(2.0));
      arr.add(Double.valueOf(-2.0));
      System.out.println("Requesting add");
      client.requestAdd(arr);
      client.requestSubtract(arr);
      client.requestMultiply(arr);
      client.requestDivide(arr);
      client.requestReadTips();
      client.requestWriteTips("Name3", "I have a new tip for you!");
      client.requestReadTips();*/
      //System.out.println(dub.doubleValue());

      // does not exist
      //client.findServer("random");


    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent
      // leaking these
      // resources the channel should be shut down when it will no longer be used. If
      // it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}

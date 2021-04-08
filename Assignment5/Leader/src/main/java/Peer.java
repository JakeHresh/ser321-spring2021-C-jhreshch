import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.nio.charset.Charset;
import java.io.PrintWriter;
import org.json.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.*;

/**
 * This is the main class for the peer2peer program.
 * It starts a client with a username and host:port for the peer and host:port of the initial leader
 * This Peer is basically the client of the application, while the server (the one listening and waiting for requests)
 * is in a separate thread ServerThread
 * In here you should handle the user input and then send it to the server of annother peer or anything that needs to be done on the client side
 * YOU CAN MAKE ANY CHANGES YOU LIKE: this is a very basic implementation you can use to get started
 * 
 */

public class Peer {
	private String username;
	private BufferedReader bufferedReader;
	private ServerThread serverThread;

	private Set<SocketInfo> peers = new HashSet<SocketInfo>();
	private ArrayList<String> jokes = new ArrayList<String>();
	private ArrayList<String> finalJokes = new ArrayList<String>();
	private boolean leader = false;
	private SocketInfo leaderSocket;
	private int consentNum = 0;
	private int consentNeeded = -1;
	private boolean detectedMissingLeader = false;
	private boolean informed = false;
	private static String hostInfo = "";
	private static String portInfo = "";
	private int leaderCount = 0;
	
	public Peer(BufferedReader bufReader, String username,ServerThread serverThread){
		this.username = username;
		this.bufferedReader = bufReader;
		this.serverThread = serverThread;
	}

	public void setLeader(boolean leader, SocketInfo leaderSocket){
		this.leader = leader;
		this.leaderSocket = leaderSocket;
	}

	public SocketInfo getLeaderSocket(){
		return leaderSocket;
	}

	public boolean isLeader(){
		return leader;
	}

	public void addPeer(SocketInfo si){
		peers.add(si);
	}
	
	// get a string of all peers that this peer knows
	public String getPeers(){
		String s = "";
		for (SocketInfo p: peers){
			s = s +  p.getHost() + ":" + p.getPort() + " ";
		}
		return s; 
	}

	public boolean checkDuplicatePeer(String ipStr, int portStr){
		for (SocketInfo p: peers){//s.getHost(), s.getPort()
			if(p.getHost().equals(ipStr) && p.getPort() == portStr)
			{
				//System.out.println("Duplicate entry exists. Wait until timeout to accept peer.");
				return true;
			}
		}
		return false;
	}

	public int getPeersSize(){
		return peers.size();
	}

	public synchronized void addJoke(String j){
		jokes.add(j);
		grantConsent("{'type': 'consent', 'username': '"+ username +"','message':'" + j + "'}");//send a message indicating consent
	}

	public String getJokes(){
		String s = "";
		for(String j: jokes){
			s = s + j + "`"; 
		}
		return s;
	}
	public void clearJokes(){
		jokes.clear();
	}
	public void clearFinalJokes(){
		finalJokes.clear();
	}
	public void addFinalJoke(String j){
		finalJokes.add(j);
	}
	public String getFinalJokes(){
		String s = "";
		for(String j: finalJokes){
			s = s + j + "`";
		}
		return s;
	}
	public synchronized void addConsent(){
		consentNum++;
	}
	public synchronized int getConsent(){
		return consentNum;// use this to compare to the current number of nodes in the list. If it's equal to the size of the socket info list, consent is granted and the joke is added.
	}
	public synchronized void resetConsent(){
		consentNum = 0;
	}
	public synchronized void setConsentNeeded(int c){
		consentNeeded = c;
	}
	public synchronized void resetConsentNeeded(){
		consentNeeded = -1;
	}
	public synchronized int getConsentNeeded(){
		return consentNeeded;
	}

	public synchronized int getLeaderCount(){
		return leaderCount;
	}
	public synchronized void setLeaderCount(int i){
		leaderCount = i;
	}

	public synchronized boolean getDetectedMissingLeader(){
		return detectedMissingLeader;
	}
	public synchronized void setDetectedMissingLeader(boolean b){
		detectedMissingLeader = b;
	}
	public synchronized boolean getInformed(){
		return informed;
	}
	public synchronized void setInformed(boolean b){
		informed = b;
	}
	/**
	 * Adds all the peers in the list to the peers list
	 * Only adds it if it is not the currect peer (self)
	 *
	 * @param list String of peers in the format "host1:port1 host2:port2"
	 */
	public synchronized void updateListenToPeers(String list) throws Exception {
		String[] peerList = list.split(" ");
		for (String p: peerList){
			String[] hostPort = p.split(":");

			// basic check to not add ourself, since then we would send every message to ourself as well (but maybe you want that, then you can remove this)
			if ((hostPort[0].equals("localhost") || hostPort[0].equals(serverThread.getHost())) && Integer.valueOf(hostPort[1]) == serverThread.getPort()){
				continue;
			}
			SocketInfo s = new SocketInfo(hostPort[0], Integer.valueOf(hostPort[1]));
			if(!checkDuplicatePeer(hostPort[0], Integer.valueOf(hostPort[1])))
				peers.add(s);
		}
	}

	public synchronized void updateJokeList(String list) throws Exception {
		String[] jokeList = list.split("*");
		for(String j: jokeList){
			jokes.add(j); 
		}
	}
	public synchronized void updateFinalJokeList(String list) throws Exception{
		String[] finalJokeList = list.split("`");
		for(String j: finalJokeList){
			finalJokes.add(j);
		}
	}
	
	/**
	 * Client waits for user to input can either exit or send a message
	 */
	public void askForInput() throws Exception {
		try {
			Thread currentThread = Thread.currentThread();
			Thread pulseThread = new Thread(() -> { // This extra pulse thread helps detect unavailable nodes.
				try{
					while(true)
					{
						pushMessage(""/*"{'type': 'message', 'username': '"+ username +"','message':'Still alive'}"*/);
						Thread.sleep(6000);
						//currentThread.stop();
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			});
			pulseThread.start();
			System.out.println("> You can now start chatting (exit to exit)");
			while(true) {
				//String message = bufferedReader.readLine();
				Scanner scan = new Scanner(System.in);
				String message = scan.nextLine();
				if(message.equals("joke"))
				{
					if(isLeader())
					{
						System.out.println("The leader does not enter jokes. The leader only processes the jokes of peers.");
						continue;
					}
					System.out.println("Please enter your joke");
					String joke = scan.nextLine();
					joke = joke.replaceAll("'", "’");
					jokes.add(joke);
					//addJoke(joke);
					pushMessage("{'type': 'joke', 'username': '"+ username +"','message':'" + joke + "'}");
					continue;
				}
				if(message.equals("display jokes"))
				{
					//System.out.println(jokes.getJokes());
					for(String j: jokes)
					{
						System.out.println(j);
					}
					continue;
				}
				if(message.equals("display final jokes"))
				{
					for(String j: finalJokes)
					{
						System.out.println(j);
					}
					continue;
				}
				message = message.replaceAll("'", "’");
				//System.out.println(message);
				if (message.equals("exit") && !message.equals("joke")) {
					System.out.println("bye, see you next time");
					break;
				} else {
					pushMessage("{'type': 'message', 'username': '"+ username +"','message':'" + message + "'}");
				}	
			}
			System.exit(0);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

// ####### You can consider moving the two methods below into a separate class to handle communication
	// if you like (they would need to be adapted some of course)


	/**
	 * Send a message only to the leader 
	 *
	 * @param message String that peer wants to send to the leader node
	 * this might be an interesting point to check if one cannot connect that a leader election is needed
	 */
	public void commLeader(String message) {
		try {
			BufferedReader reader = null; 
				Socket socket = null;
				try {
					socket = new Socket(leaderSocket.getHost(), leaderSocket.getPort());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
				} catch (Exception c) {
					if (socket != null) {
						socket.close();
					} else {
						System.out.println("Could not connect to " + leaderSocket.getHost() + ":" + leaderSocket.getPort());
					}
					return; // returning since we cannot connect or something goes wrong the rest will not work. 
				}
				/*Thread pulseThread = new Thread(() -> { // This extra pulse thread helps detect unavailable nodes.
					try{
						Thread.sleep(6000);
						System.out.println("Could not connect to leader since peer with your host and port already exits. Try again later.");
						System.exit(0);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				});
				pulseThread.start();*/
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(message);
				//pulseThread.stop();

				JSONObject json = new JSONObject(reader.readLine());
				System.out.println("     Received from server " + json);
				String list = json.getString("list");
				updateListenToPeers(list); // when we get a list of all other peers that the leader knows we update them
				String finaljokes = json.getString("jokes");
				updateFinalJokeList(finaljokes);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void grantConsent(String message){
		try {
			System.out.println("pushing final joke");
			BufferedReader reader = null; 
				Socket socket = null;
				try {
					socket = new Socket(leaderSocket.getHost(), leaderSocket.getPort());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
				} catch (Exception c) {
					if (socket != null) {
						socket.close();
					} else {
						//System.out.println("Could not connect to " + leaderSocket.getHost() + ":" + leaderSocket.getPort());
					}
					return; // returning since we cannot connect or something goes wrong the rest will not work. 
				}

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(message);

				//JSONObject json = new JSONObject(reader.readLine());
				//System.out.println("     Received from server " + json);
				//String list = json.getString("list");
				//updateListenToPeers(list); // when we get a list of all other peers that the leader knows we update them

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

/**
	 * Send a message to every peer in the peers list, if a peer cannot be reached remove it from list
	 *
	 * @param message String that peer wants to send to other peers
	 */
	public synchronized void pushMessage(String message) {
		try {
			if(!message.equals(""))
				System.out.println("     Trying to send to peers: " + peers.size());

			Set<SocketInfo> toRemove = new HashSet<SocketInfo>();
			BufferedReader reader = null; 
			int counter = 0;
			for (SocketInfo s : peers) {
				//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				Socket socket = null;
				try {
					socket = new Socket(s.getHost(), s.getPort());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (Exception c) {
					if (socket != null) {
						socket.close();
					} else {

						if(s == leaderSocket && !detectedMissingLeader)
						{
							//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
							//out.println(("{'type': 'detectedmissingleader', 'foundleader': 'true'"));
							//do needed stuff to become leader
							//System.out.println("I first detected the missing leader. I will become the new leader.");
							//SocketInfo sn = new SocketInfo(hostInfo, Integer.valueOf(portInfo));
							//setLeader(true, sn);
							//tell other peers who is the new leader
							setDetectedMissingLeader(true);
							System.out.println("Missing leader detected");
							//push message containing socket and port info.
							//pushMessage("{'type': 'tellemwhosboss', 'username': '"+ username +"','ip':'" + hostInfo + "','port':'" + portInfo + "'}");
							//once message is received, they will execute a commLeader command with the sent info
						}
						//else if(s == leaderSocket)
						//{
						//	setDetectedMissingLeader(false);
						//}
						System.out.println("  Could not connect to " + s.getHost() + ":" + s.getPort());
						System.out.println("  Removing that socketInfo from list");
						toRemove.add(s);
						continue;
					}
					System.out.println("     Issue: " + c);
				}
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				
				if(detectedMissingLeader)
				{
					/*String stringtoinsert = ", 'foundleader': 'true'";
					StringBuffer newString = new StringBuffer(message);
					int index = message.length();
					newString.insert(index - 2, stringtoinsert);
					message = newString.toString();
					System.out.println(message);*/
					//SocketInfo sn = new SocketInfo("localhost", 9999);
					//setLeader(true, sn);
					if(message.equals(""))
					{
						message = "{'type': 'tellemwhosboss', 'username': '"+ username +"','ip':'" + hostInfo + "','port':'" + portInfo + "'}";
						//System.out.println(message);
					}
					//System.out.println("Did detect missing leader");
					//System.out.println(message);
					//out.println(message);
					//out.println(("{'type': 'detectedmissingleader', 'foundleader': 'true'"));
					//out.println("{'type': 'tellemwhosboss', 'username': '"+ username +"','ip':'" + hostInfo + "','port':'" + portInfo + "'}");
					//out.flush();
					//out.close();
					//out.flush();
					//PrintWriter out2 = new PrintWriter(socket.getOutputStream(), true);
					out.println(message);
				}
				else
				{
					//System.out.println("Did NOT detect missing leader");
					//System.out.println(message);
					/*String stringtoinsert = ", 'foundleader': 'true'";
					StringBuffer newString = new StringBuffer(message);
					int index = message.length();
					newString.insert(index - 2, stringtoinsert);
					message = newString.toString();
					System.out.println(message);*/
					//out.println(message);
					//out.println(("{'type': 'detectedmissingleader', 'foundleader': 'false'"));
					//out.flush();
					//out.close();
					//out.flush();
					//PrintWriter out2 = new PrintWriter(socket.getOutputStream(), true);
					out.println(message);
				}
				setDetectedMissingLeader(false);
				//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				//out.println(message);
				//Test section to update peer joke lists
				/*if(message.contains("{'type': 'joke',")){
					JSONObject json = new JSONObject(reader.readLine());
					if(json.getString("type").equals("joke")){
						System.out.println("     Received from server " + json);
						String list = json.getString("list");
						updateJokeList(list);
					}
				}*/

				counter++;
				socket.close();
		     }
		    for (SocketInfo s: toRemove){
		    	peers.remove(s);
		    }
		    if(!message.equals(""))
		    	System.out.println("     Message was sent to " + counter + " peers");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void leaderSetter(String message) {
		try {
			if(!message.equals(""))
				System.out.println("     Trying to send to peers: " + peers.size());

			Set<SocketInfo> toRemove = new HashSet<SocketInfo>();
			BufferedReader reader = null; 
			int counter = 0;
			for (SocketInfo s : peers) {
				//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				Socket socket = null;
				try {
					socket = new Socket(s.getHost(), s.getPort());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (Exception c) {
					if (socket != null) {
						socket.close();
					} else {
						//else if(s == leaderSocket)
						//{
						//	setDetectedMissingLeader(false);
						//}
						System.out.println("  Could not connect to " + s.getHost() + ":" + s.getPort());
						System.out.println("  Removing that socketInfo from list");
						toRemove.add(s);
						continue;
					}
					System.out.println("     Issue: " + c);
				}
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				
				//if(detectedMissingLeader)
				//{
					/*String stringtoinsert = ", 'foundleader': 'true'";
					StringBuffer newString = new StringBuffer(message);
					int index = message.length();
					newString.insert(index - 2, stringtoinsert);
					message = newString.toString();
					System.out.println(message);*/
					SocketInfo sn = new SocketInfo("localhost", 9999);
					setLeader(true, sn);
					//if(message.equals(""))
					//{
						message = "{'type': 'tellemwhosboss', 'username': '"+ username +"','ip':'" + hostInfo + "','port':'" + portInfo + "'}";
						//System.out.println(message);
					//}
					//System.out.println("Did detect missing leader");
					//System.out.println(message);
					//out.println(message);
					//out.println(("{'type': 'detectedmissingleader', 'foundleader': 'true'"));
					//out.println("{'type': 'tellemwhosboss', 'username': '"+ username +"','ip':'" + hostInfo + "','port':'" + portInfo + "'}");
					//out.flush();
					//out.close();
					//out.flush();
					//PrintWriter out2 = new PrintWriter(socket.getOutputStream(), true);
					out.println(message);
				//}
				//else
				//{
				//	System.out.println("Did NOT detect missing leader");
				//	System.out.println(message);
					/*String stringtoinsert = ", 'foundleader': 'true'";
					StringBuffer newString = new StringBuffer(message);
					int index = message.length();
					newString.insert(index - 2, stringtoinsert);
					message = newString.toString();
					System.out.println(message);*/
					//out.println(message);
					//out.println(("{'type': 'detectedmissingleader', 'foundleader': 'false'"));
					//out.flush();
					//out.close();
					//out.flush();
					//PrintWriter out2 = new PrintWriter(socket.getOutputStream(), true);
				//	out.println(message);
				//}
				//setDetectedMissingLeader(false);
				//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				//out.println(message);
				//Test section to update peer joke lists
				/*if(message.contains("{'type': 'joke',")){
					JSONObject json = new JSONObject(reader.readLine());
					if(json.getString("type").equals("joke")){
						System.out.println("     Received from server " + json);
						String list = json.getString("list");
						updateJokeList(list);
					}
				}*/

				//counter++;
				//socket.close();
				detectedMissingLeader = false;
		     }
		    //for (SocketInfo s: toRemove){
		    //	peers.remove(s);
		    //}
		    if(!message.equals(""))
		    	System.out.println("     Message was sent to " + counter + " peers");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method saying hi and also starting the Server thread where other peers can subscribe to listen
	 *
	 * @param args[0] username
	 * @param args[1] port for server
	 */
	public static void main (String[] args) throws Exception {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
		String username = args[0];
		System.out.println("Hello " + username + " and welcome! Your port will be " + args[1]);

		int size = args.length;
		System.out.println(size);
		if (size == 4) {
			System.out.println("Started peer");
        } else {
            System.out.println("Expected: <name(String)> <peer(String)> <leader(String)> <isLeader(bool-String)>");
            System.exit(0);
        }

        System.out.println(args[0] + " " + args[1]);
        ServerThread serverThread = new ServerThread(args[1]);
        Peer peer = new Peer(bufferedReader, username, serverThread);

        String[] hostPort = args[2].split(":");
        SocketInfo s = new SocketInfo(hostPort[0], Integer.valueOf(hostPort[1]));
        String[] hostPort2 = args[1].split(":");
        SocketInfo sn = new SocketInfo(hostPort2[0], Integer.valueOf(hostPort2[1]));
        String h = new String(hostPort2[0]);
        String p = new String(hostPort2[1]);
        hostInfo = h;
        portInfo = p;
        System.out.println(args[3]);
        if (args[3].equals("true")){
			System.out.println("Is leader");
			peer.setLeader(true, s);
		} else {
			System.out.println("Pawn");

			// add leader to list 
			peer.addPeer(s);
			peer.setLeader(false, s);

			// send message to leader that we want to join
			peer.commLeader("{'type': 'join', 'username': '"+ username +"','ip':'" + serverThread.getHost() + "','port':'" + serverThread.getPort() + "'}");

		}
		serverThread.setPeer(peer);
		serverThread.start();
		peer.askForInput();

	}
	
}

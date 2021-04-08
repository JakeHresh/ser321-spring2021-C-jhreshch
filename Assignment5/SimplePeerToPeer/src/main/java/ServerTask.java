import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;

import org.json.*;

/**
 * This is the class that handles communication with a peer/client that has connected to use
 * and wants something from us
 * 
 */

public class ServerTask extends Thread {
	private BufferedReader bufferedReader;
	private Peer peer = null; // so we have access to the peer that belongs to that thread
	private PrintWriter out = null;
	private Socket socket = null;
	
	// Init with socket that is opened and the peer
	public ServerTask(Socket socket, Peer peer) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		this.peer = peer;
		this.socket = socket;
	}
	
	// basically wait for an input, right now we can only handle a join request
	// and a message
	// More requests will be needed to make everything work
	// You can enhance this or totally change it, up to you. 
	// I used simple JSON here, you can use your own protocol, use protobuf, anything you want
	// in here this is not done especially pretty, I just use a PrintWriter and BufferedReader for simplicity
	public void run() {
		while (true) {
			try {
			    JSONObject json = new JSONObject(bufferedReader.readLine());

			    if (json.getString("type").equals("join")){
			    	System.out.println("     " + json); // just to show the json

			    	System.out.println("     " + json.getString("username") + " wants to join the network");
			    	peer.updateListenToPeers(json.getString("ip") + ":" + json.getInt("port"));
			    	out.println(("{'type': 'join', 'list': '"+ peer.getPeers() +"', 'jokes': '" + peer.getFinalJokes() + "'}"));
			    	//String finaljokes = peer.getFinalJokes();
			    	//System.out.println(finaljokes);
			    	SocketInfo sn = new SocketInfo(json.getString("ip"), json.getInt("port"));
			    	if (peer.getLeaderSocket().getPort() != json.getInt("port") && !peer.getLeaderSocket().getHost().equals(json.getString("ip")))
			    	{
			    		peer.commLeader("{'type': 'join', 'username': '"+ json.getString("username") +"','ip':'" + json.getString("ip") + "','port':'" + json.getInt("port") + "'}");
			    	}
			    	if (peer.isLeader()){
			    		peer.pushMessage(json.toString());
			    	}

			    	//peer.clearFinalJokes();
			    	//peer.updateFinalJokeList(finaljokes);

			    	//This section needs a way to create a string concatenation of jokes, send that to the joining peer, and
			    	//have the peer parse it out to update its own joke list.

			    	// TODO: should make sure that all peers that the leader knows about also get the info about the new peer joining
			    	// so they can add that peer to the list
			    } else if(json.getString("type").equals("joke")){
			    	/*peer.updateJokeList(json.getString("message"));
			    	out.println(("{'type': 'joke', 'list': '"+ peer.getJokes() +"'}"));
			    	if (peer.isLeader()){
			    		peer.pushMessage(json.toString());
			    	}*/
			    	if (peer.isLeader()){
			    		peer.setConsentNeeded(peer.getPeersSize());
			    		peer.addJoke(json.getString("message"));
			    		peer.pushMessage("{'type': 'tellothersjoke', 'username': '"+ json.getString("username") +"','message':'" + json.getString("message") + "'}");
			    	}
			    } else if(json.getString("type").equals("tellothersjoke")){
			    	if(!peer.isLeader()){
			    		peer.addJoke(json.getString("message"));
			    	}
			    } else if(json.getString("type").equals("detectedmissingleader")){
			    	if(json.getString("foundleader").equals("true"))
			    		peer.setDetectedMissingLeader(true);
			    } else if(json.getString("type").equals("consent")){
			    	if(peer.isLeader()){
			    		peer.addConsent();
			    		if(peer.getConsent() == peer.getPeersSize() && peer.getPeersSize() == peer.getConsentNeeded()){
			    			peer.resetConsent();
			    			peer.resetConsentNeeded();
			    			peer.addFinalJoke(json.getString("message"));
			    			System.out.println("pushing final joke");
			    			peer.pushMessage("{'type': 'finaljoke', 'username': '"+ json.getString("username") +"','message':'" + json.getString("message") + "'}");//push message to send joke to final joke list and empty potential joke list
			    			peer.clearJokes();
			    		}
			    		else if(peer.getPeersSize() != peer.getConsentNeeded())
			    		{
			    			peer.pushMessage("{'type': 'consensusfail', 'username': '"+ json.getString("username") +"','message':'The number of peers has changed. Consensus cannot be reached.'}");
			    			peer.resetConsent();
			    			peer.resetConsentNeeded();
			    			peer.clearJokes();
			    		}
			    		else
			    		{
			    			System.out.println("Consensus not yet reached.");
			    		}
			    	}
			    } else if(json.getString("type").equals("tellemwhosboss")){
			    	//System.out.println("     " + json); // just to show the json

			    	//System.out.println("     " + json.getString("username") + " wants to join the network");
			    	//peer.updateListenToPeers(json.getString("ip") + ":" + json.getInt("port"));
			    	//out.println(("{'type': 'join', 'list': '"+ peer.getPeers() +"'}"));
			    	SocketInfo sn = new SocketInfo(json.getString("ip"), json.getInt("port"));
			    	//System.out.println(json.getString("ip") + ":" + json.getInt("port"));
			    	//System.out.println("hi");

			    	peer.resetConsent();
			    	peer.resetConsentNeeded();
			    	peer.clearJokes();

			    	peer.setLeader(true, sn);
			    	peer.setDetectedMissingLeader(true);
			    	if (peer.isLeader() && !peer.getInformed()){
			    		peer.setInformed(true);
			    		peer.leaderSetter(json.toString());
			    		//peer.setDetectedMissingLeader(false);
			    	}
			    } else if(json.getString("type").equals("consensusfail'")){
			    	peer.resetConsent();
			    	peer.resetConsentNeeded();
			    	peer.clearJokes();
			    	System.out.println(json.getString("message"));
			    } else if(json.getString("type").equals("finaljoke")){
			    	peer.addFinalJoke(json.getString("message"));
			    	peer.resetConsent();
			    	peer.resetConsentNeeded();
			    	peer.clearJokes();
			    } else {
			    	System.out.println("[" + json.getString("username")+"]: " + json.getString("message")/*.replaceAll("*", "'")*/);
			    }
			    
			    
			} catch (Exception e) {
				interrupt();
				break;
			}
		}
	}

}

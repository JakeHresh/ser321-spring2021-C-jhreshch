## Purpose:
Very basic peer-2-peer for a chat. All peers can communicate with each other. 

Each peer is client and server at the same time. 
When started the peer has a ServerThread in which the peer listens for potential other peers to connect.

You want to first start the leader who is the one in charge of the network

### Running the leader
This will start the leader on a default port and use localhost
	gradle runPeer -PisLeader=true -q --console=plain

If you want to change the leader settings
	gradle runPeer -PpeerName=Hans -Ppeer="localhost:8080" -Pleader="localhost:8080" -PisLeader=true -q --console=plain

You can of course replace localhost with the IP of your AWS, Pi etc. 

### Running a Pawn

So just a peer who is not the leader, minimal with the "default" leader from above
	gradle runPeer -PpeerName=Anna -Ppeer="localhost:9000" -q --console=plain

If you want to change settings
	gradle runPeer -PpeerName=Elsa -Ppeer="localhost:9000" -Pleader="localhost:8080" -q --console=plain

- isLeader is default false so you do not need to set it
- leader: needs to be the same in ALL started peers no matter if leader or pawn

You can start as many pawns (non leaders) as you like they should all connect. 

Watch the video for some more details about the code. 
This code is a basic code that does not include a lot of error handling yet and might need adjustments depending on how you implement your leader election. You can change this code any way you like. 
Some things that it does not do:
- check inputs from Gradle (would be good to include that)
- most requests to the server are not acknowledged, e.g. when a message is send we just send it and the server will never respond to us that they actually go it

### My Section

Link to this screencast: 

The above sections describe how the assignment needs to be run. There are no changes to this.

Below are the requirements I believe I fulfilled:

- [X] 1.
- [X] 2.
- [X] 3.
- [X] 4.
- [X] 5a. The rule I use is that a node becomes a leader when it detects that its leader node becomes unresponsive. This means that a sort of democratic state emerges where, if there is more than one pawn peer that has the same leader, all of those pawn peers become separate leaders. Joining peers can then choose between the new leaders.
- [X] 5b.
- [X] 5c.
- [X] 5d.
- [X] 5e. There is a partial implementation of this. However, the implementation is not fully functional.
- [X] 6.

The protocol involves the use of the following JSON structures:

Below is used to handle peers joining the network.

{'type': 'join', 'username': <username>, 'ip': <ip>, 'port': <port>}
	
Below is used to handle the introduction of new jokes sent by peers. This results in the use of the following structure in this list.	

{'type': 'joke', 'username': <username>, 'message': <message>}
	
Once a joke is introduced, the below structure is used to inform the other peers of the joke so that it is stored temporarily.
	
{'type': 'tellothersjoke', 'username': <username>, 'message': <message>}
	
As jokes are added, existing nodes grant their consent to add the joke. If the number of nodes that consent is less than the number of nodes that initially existed, the consent fails, which is handled by the structure of type 'consensulfail'
	
{'type': 'consent', 'username': <username>, 'message': <message>}
	
When the below structure is used, the temporary jokes are removed and the consensus count is reset.	

{'type': 'consensusfail', 'username': <username>, 'message': 'The number of peers has changed. Consensus cannot be reached.'}
	
When consensus is reached, the below structure is used to inform the other peers, including the leader, to add the joke to the final list of jokes.
	
{'type': 'finaljoke', 'username': <username>, 'message': <message>}

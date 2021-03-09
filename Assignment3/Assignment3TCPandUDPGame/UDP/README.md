##### UDP Readme
* This project represents a simple UDP socket program where a client answers questions sent over by a server. The client must attempt to answer the questions correctly. Instead of using a TCP connection that allows Java to handle the sending of string data, the UDP connection utilizes other classes that breaks up a message into a collection of packets, each with simple headers and payloads. These classes are NetworkUtils.java and JsonUtils.java.
##### Running the Program
* Use gradle UDPServer to run the server. Optionally include -Pport=port where port represents a port number.
* Use gradle UDPClient to run the client. Optionally include -Pport=port where port represents a port number and -Phost=hostIP where hostIP represents the IP address of the host.
##### Fulfilled Requirements (Only 4 requirements are fulfilled per the assignment instructions)
- [X] 1.
- [X] 2.
- [X] 3. Not extra credit
- [X] 4.
#### Protocol
* Below is what is used when the client sends messages to the server, whether it be numbers or string answers to questions such as "What is your name?"
* {"selected": <int: 1=start, string: word answers to questions>}
* Server sends the below to ask questions of the client.
* {"error": <string: messages from the server>}
* Server uses the below primarily to greet the client.
```
{
   "datatype": <int: 1-string, 2-byte array>, 
   "type": <"joke", "quote", "image">,
   "data": <thing to return> 
}
```
#### Program Robustness
* Because messages are primarily sent between client and server as strings, all answers can be simply represented, allowing for parsing ints from strings and for alphabetic answers. 
* When parsing for ints, checks are made as to whether the string can be parsed at all before parsing.
* Like the TCP gradle file, the UDP gradle file searches for specific parameters passed in with commands and organizes them when sending those arguments to the main method. If arguments are presented incorrectly or do not exist, default values are used. 
* Because the UDP connection is asynchronous, the client or server programs do not terminate when the other terminates unexpectedly. However, handling is in place should a client attempt to send something over the wire to a server that is not running.

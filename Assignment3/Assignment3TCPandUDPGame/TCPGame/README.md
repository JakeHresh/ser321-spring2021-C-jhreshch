##### TCP Readme
* This project represents a simple TCP socket program where a client answers questions sent over by a server. The client must attempt to answer the questions correctly within the timer, the duration of which is determined by the number of questions the client requested.
##### Running the Program
* Use gradle runServer to run the server. Optionally include -Pport=port where port represents a port number.
* Use gradle runClient to run the client. Optionally include -Pport=port where port represents a port number and -Phost=hostIP where hostIP represents the IP address of the host.
##### Fulfilled Requirements
- [X] 1.
- [X] 2.
- [X] 3. Not extra credit
- [X] 4.
- [X] 5.
- [X] 6.
- [X] 7.
- [X] 8.
- [X] 9.
- [X] 10. The loser image is displayed in the Terminal Frame
- [X] 11. The winner image is displayed in the Terminal Frame
- [ ] 12.
- [X] 13. Responses dictating who is the winner and loser is stored only on the server
- [X] 14.
- [X] 15. The robustness of the protocol is present in the use of sending string objects over the wire. This allows for easy evaluation of correct/incorrect answers as well as integer parsing. Additionally, the gradle build file handles incorrect commands by searching for specific key words and organizing the values obtained by those key words in order in the main function's argument collection. If the arguments can't be parsed or don't exist, default values are instead passed as arguments.
- [X] 16. The robustness of the program is accomplished through the use of exception handling to allow for graceful termination of either the client or server when one suddenly disconnects from the other.
- [X] 17.

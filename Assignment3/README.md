##### Main Readme
* Link to screencast: https://drive.google.com/file/d/1GWDACQpwVTiOg0JJKLAkcMltfeTYam31/view?usp=sharing
* This assignment contains the TCP and UDP versions of the socket quiz game. 
* Both versions are based off of existing sample code. The TCP game is built off of the simple sockets program while the UDP game is built off of the advanced custom protocol program.
* The UDP program requires additional utility classes for formatting messages in the JSON format while keeping the package sizes relatively small.
* The TCP program simply relies on the use of sockets and object output and input streams for sending information to and from the server.
* Both gradle files include measures to ensure correct parameterization of commands.
* Both programs include measures to handle exceptions that occur while running.
* Should the TCP client suddenly stop communicating with the server, or vice versa, a handled exception will result.
* No exceptions occur immediately when a UDP client suddenly loses its connection with the server.

The assignment is a demonstration of working with GRPC and nodes in a distributed system. This implementation demonstrates the direct communication between client and server through GRPC to work with different services.

The requirements fulfilled are all of Task 1 and all of Task 2, which will net me 80 points from this assignment.

To work with the program, the user should first start by running the node by running gradle runNode. The user can then execute gradle runClientJava on a separate terminal to connect with the node and choose from the different available services. 

To see the program work through different services automatically, run the node as before and run the client using the following instruction: gradle runClientJava -PserviceHost='localhost' -PservicePort=8000 -PregistryHost='localhost' -PgrpcPort=9002 -Pmessage='Hello' -Pauto=1

For my own service implementation, I created a system JSON database system where users can query information from a People.json file to see the different people populated in the file. The two available requests are PeopleReadByNameRequest, which takes a string name as input and PeopleReadByIdRequest, which takes a string id as input. Due to the varied content in the JSON file, different data will be returned based on the provided input requests. Additionally, since multiple instances of names may need to be returned, responses return repeated fields of names. Finally, because of the interaction between server and JSON file, the data will remain persistent even if the server crashes.

Screencast: https://drive.google.com/file/d/1Qe92XWR9AHIxx3ayHlsnKtAD2Akg9vnb/view?usp=sharing

UPDATE: Additions have been made to further complete task 3.1 of the assignment, bumping the score I believe I ought to earn to 95. To run these changes, the user must be in the gRPC 3 directory. These updates allow the client to contact the registry for available services, list all registered services, have terminal ask for input, and call the desired request. This requires running the registry using gradleRunRegistryServer, running the node using gradleRegisterServiceNode, and running the client using gradle runClient2.

Screencast 2: https://drive.google.com/file/d/1QhBhadKPuxnuu19Jr2pu2gI3gH6N0jpy/view?usp=sharing

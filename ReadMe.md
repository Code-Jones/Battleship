#Battleship Game
This Battleship game made up of two components, Server.jar and Clients.jar 

##Server
The Server jar handles all the traffic between clients. Also, has a basic GUI to show messages and data being transferred. The Server will pair two players together and put them on a single thread. 

##Client
The Client jar creates the game interface and the game logic. This handles all the user interaction and transmits it to the server to be shared with the players opponent. 

> Currently in development and not finished

## To Run
Start with Server first
```sh
cd out/artifacts/Server_jar.jar
java -jar Server.jar
```
Then start Client 
```sh
cd out/artifacts/Client_jar
java -jar Client.jar
```
If jars don't run please move them to another file and try again. 
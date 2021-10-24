This project has 
    Networking tools
        DNS and Reverse DNS
        Ping Test ( tells you whether the host is reachable to you or not )
        Ping Sweeper ( shows you all the available host IP addresses active in given(user given input) range )
        Port Scanner ( shows you all the open ports of a specified(user given input) target IP )
        Basic Chat ( Works on Client-Server model , where you run server first and it will listen for incoming connection requests and then the host is connected to server )

These are basic things which are useful to monitor while working in a local area network 

Have a look at the screenshot for more understanding on how this app works .

There are 2 files named LANServ.java and LANClie.java ,
you need to run LANServ.java first only if you need to chat with other program(LANClie.java)
Apart from the chat system , both files do the same , you can just run a single file 


To run LANServ.java , 
    first compile it    
        javac LANServ.java
    then a 'LANServ.class' file will be created and run with passing one argument(port number on which the server should be running) 
        sudo java LANServ 12345

Note :
    LINUX users , sudo (root or administrator) permissions needed if you did encounter any problem like scaning ports or pinging a host
    WINDOWS users , just open the command prompt as an administrator and then run the program

To run LANClie.java ,
    first compile it
        javac LANClie.java
    then a 'LANClie.class' file will be created and run with passing two arguments(server ip address ,  port number on which the service is running on the server) 
        java LANClie 127.0.0.1 12345
      
Note :
    LINUX users , sudo (root or administrator) permissions needed if you did encounter any problem like scaning ports or pinging a host
    WINDOWS users , just open the command prompt as an administrator and then run the program

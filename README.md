# HELPoffLINE

It becomes troublesome to connect to people during natural disasters since the cellular signal goes down. It will be of great help if we connect the stranded people without cellular connectivity, by using their smartphones. Our idea is to use the smartphones to create an ad-hoc network to broadcasts messages and location. A smartphone will act as both server and client. Any message will be sent to immediate neighbors, which in turn will send the messages to their neighbours. In this way, the messages reach every phone in the network. Once a phone with cellular signal finds these messages, it will send the messages to the rescue crew.

### Use cases:

1. Victim can easily notify others of his location
2. The rescue crew can easily locate the victims
3. The rescue crew can broadcast important information like “Food at place XYZ”, “First aid at place ABC”

### How does it work?

We will be using Bluetooth and Wifi-direct to create the network. As every node is a server and a client, it can receive and send messages/location. 

- When the app is open, it will start looking for any nearby devices using the app. Once found, it will connect to it. Once the connection is established, message transfer can take place. Eventually, a network of smartphones is created. 
- When a phone receives a message from a smartphone, it sends the same message to other smartphones connected to it. This receiving and sending of messages continues.
- When a phone with cellular signal is found, we send the received messages to a server, which reports the messages to the rescue crew. 

### Microsoft Azure

A server dedicated to receive messages from the victims is required. We will deploy our server using Azure services.



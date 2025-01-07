# Live Chat

A real-time chat application built with Java that allows multiple users to communicate over a network. This project demonstrates the use of Java sockets for client-server communication and includes a simple graphical user interface (GUI) for ease of use.

## Features
- Real-time messaging between users.
- Support for multiple clients connected to a single server.
- Simple and intuitive GUI for both the client and server.
- Notifications for user connections and disconnections.

## Prerequisites
- Java Development Kit (JDK) installed.
- An IDE or text editor (e.g., IntelliJ IDEA, Eclipse, VS Code).

## Setup and Installation
- Clone the Repository.
~~~bash
git clone https://github.com/pranaymakkena/livechat.git
cd livechat
cd src
~~~

- Compile the Code. Open a terminal in the src folder and run:
~~~bash
javac ChatServer.java
javac ChatClient.java
~~~

- Run the Server.
Start the server by running:
~~~bash
java ChatServer.java
~~~

- Run the Client. In a new terminal, start the client by running:
~~~bash
cd src
java ChatClient.java
~~~

## Usage
1. Start the server.
2. Launch one or more clients.
3. Clients can send messages, and all connected clients will receive them in real-time.

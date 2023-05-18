# The Big Bang Server

[![CodeQL](https://github.com/theadambyrne/thebigbangserver/actions/workflows/codeql.yml/badge.svg)](https://github.com/theadambyrne/thebigbangserver/actions/workflows/codeql.yml)
[![Java CI with Maven](https://github.com/theadambyrne/thebigbangserver/actions/workflows/maven.yml/badge.svg)](https://github.com/theadambyrne/thebigbangserver/actions/workflows/maven.yml)
[![SonarCloud](https://github.com/theadambyrne/thebigbangserver/actions/workflows/build.yml/badge.svg)](https://github.com/theadambyrne/thebigbangserver/actions/workflows/build.yml)

## Description
This project is a command based groupchat application that uses Sockets, Threads and Pipes. It includes many small feature such as /joke and /panic. 


## Features
- Clients are free to join server, provided they know the server's IP address and port number.
- Clients can send messages to the server, which will be broadcasted to all other clients.
- Clients are identified by a nickname associated with their socket.
- Clients can send commands to the server, which will be executed by the server.

## Commands
- /joke - The server will send a random joke to the client using an API.
- /panic - The server will clear all client's output.
- /list - The server will send a list of all the clients connected to the server.
- /clear - The server will clear the client's output.
- /quit - The client will disconnect from the server.
- /help - The server will send a list of all the commands to the client.

## Installation
```bash
git clone https://github.com/theadambyrne/thebigbangserver.git
cd thebigbangserver

# install dependencies
mvn clean install

# run tests
mvn test
```

## Usage
```bash
# generate executable jar files
mvn clean package

# run server and client
java -jar target/TBBS-server-1.jar
java -jar target/TBBS-client-1.jar
```

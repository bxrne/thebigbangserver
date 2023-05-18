# The Big Bang Server

[![CodeQL](https://github.com/theadambyrne/thebigbangserver/actions/workflows/codeql.yml/badge.svg)](https://github.com/theadambyrne/thebigbangserver/actions/workflows/codeql.yml)
[![Java CI with Maven](https://github.com/theadambyrne/thebigbangserver/actions/workflows/maven.yml/badge.svg)](https://github.com/theadambyrne/thebigbangserver/actions/workflows/maven.yml)
[![SonarCloud](https://github.com/theadambyrne/thebigbangserver/actions/workflows/build.yml/badge.svg)](https://github.com/theadambyrne/thebigbangserver/actions/workflows/build.yml)


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

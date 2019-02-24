# Scala Database Streaming


## Prerequisites
JDK 11
Scala 2.12.x
npm 6.7.0+

Please set up your JDK the way you prefer. To install Scala and npm on Mac, use:
```
brew install scala
brew install npm
```

## Frontend libs installation
```
cd ScalaDatabaseStreaming
npm install
bower install 
```

## Run
Build the app
```
mvn clean package
```

Start MySQL with Docker compose
```
docker-compose up
```

Start the app using Maven, or use your favorite IDE
```
mvn spring-boot:run
```

#Links

Slides: [https://docs.google.com/presentation/d/1GUly63HEMIa5gYC9CQ-ejIVqlimlmA6IHKVbBXyQN74/edit?usp=sharing]

Reactive Streams initiative: [http://www.reactive-streams.org/]
Akka Streams: [https://doc.akka.io/docs/akka/2.5/stream/]
Slick: [http://slick.lightbend.com/docs/]

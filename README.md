# Context

Basic project where two processes (provider and user) can communicate with each other using OSGi Remote Services.

It uses Apache Aries as the OSGi RSA implementation. The user process uses configuration to discover the provider process statically. 

The provider exports two services (`PrimaryService` and `SecondaryService`) of which the user only wants to use the `PrimaryService`.

# Building

`mvn clean install` 

# Running

Provider: `java -jar provider/target/provider-launch.jar`

User: `java -jar user/target/user-launch.jar`

# Issue

The user process fail as the `EndpointDescription` (manually configured in `user/src/main/resources/OSGI-INF/configurator/configuration.json`) 
lists two Java interfaces, whilst the user process only knows about one (the one it uses).
# Context

Basic project where two processes (provider and user) can communicate with each other using OSGi Remote Services.

It uses Apache Aries as the OSGi RSA implementation. The user process uses configuration to discover the provider process statically. 

The provider exports two services (`PrimaryService` and `SecondaryService` from two separate bundles) of which the user only wants to use the `PrimaryService`.
As user only uses one of the exposed services, it has no package import of the other interface.

# Building

`mvn clean install` 

# Running

Provider: `java -jar provider/target/provider-launch.jar`

User: `java -jar user/target/user-launch.jar`

# Issue

The user process fail as the `EndpointDescription` (manually configured in `user/src/main/resources/OSGI-INF/configurator/configuration.json`) 
lists two Java interfaces, whilst the user process only knows about one (the one it uses).

The following stacktrace is then raised:

```
23:20:01.475 WARN  [          pool-2-thread-1]               org.apache.aries.rsa.core.ClientServiceFactory - Problem creating a remote proxy for [org.glimmerveen.osgi.rsa.api.primary.PrimaryService, org.glimmerveen.osgi.rsa.api.secondary.SecondaryService]
 java.lang.ClassNotFoundException: org.glimmerveen.osgi.rsa.api.secondary.SecondaryService not found by org.glimmerveen.osgi.rsa.user [15]
 	at org.apache.felix.framework.BundleWiringImpl.findClassOrResourceByDelegation(BundleWiringImpl.java:1597)
 	at org.apache.felix.framework.BundleWiringImpl.access$300(BundleWiringImpl.java:79)
 	at org.apache.felix.framework.BundleWiringImpl$BundleClassLoader.loadClass(BundleWiringImpl.java:1982)
 	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:521)
 	at org.apache.aries.rsa.core.ClientServiceFactory.getService(ClientServiceFactory.java:64)
 	at org.apache.felix.framework.ServiceRegistrationImpl.getFactoryUnchecked(ServiceRegistrationImpl.java:348)
 	at org.apache.felix.framework.ServiceRegistrationImpl.getService(ServiceRegistrationImpl.java:248)
 	at org.apache.felix.framework.ServiceRegistry.getService(ServiceRegistry.java:350)
 	at org.apache.felix.framework.Felix.getService(Felix.java:3954)
 	at org.apache.felix.framework.BundleContextImpl.getService(BundleContextImpl.java:450)
 	at org.apache.felix.scr.impl.manager.SingleRefPair.getServiceObject(SingleRefPair.java:86)
 	at org.apache.felix.scr.impl.inject.ComponentConstructor.newInstance(ComponentConstructor.java:281)
 	at org.apache.felix.scr.impl.manager.SingleComponentManager.createImplementationObject(SingleComponentManager.java:277)
 	at org.apache.felix.scr.impl.manager.SingleComponentManager.createComponent(SingleComponentManager.java:114)
 	at org.apache.felix.scr.impl.manager.SingleComponentManager.getService(SingleComponentManager.java:982)
 	at org.apache.felix.scr.impl.manager.SingleComponentManager.getServiceInternal(SingleComponentManager.java:955)
 	at org.apache.felix.scr.impl.manager.AbstractComponentManager.activateInternal(AbstractComponentManager.java:765)
 	at org.apache.felix.scr.impl.manager.DependencyManager$SingleStaticCustomizer.addedService(DependencyManager.java:1045)
 	at org.apache.felix.scr.impl.manager.DependencyManager$SingleStaticCustomizer.addedService(DependencyManager.java:999)
 	at org.apache.felix.scr.impl.manager.ServiceTracker$Tracked.customizerAdded(ServiceTracker.java:1216)
 	at org.apache.felix.scr.impl.manager.ServiceTracker$Tracked.customizerAdded(ServiceTracker.java:1137)
 	at org.apache.felix.scr.impl.manager.ServiceTracker$AbstractTracked.trackAdding(ServiceTracker.java:944)
 	at org.apache.felix.scr.impl.manager.ServiceTracker$AbstractTracked.track(ServiceTracker.java:880)
 	at org.apache.felix.scr.impl.manager.ServiceTracker$Tracked.serviceChanged(ServiceTracker.java:1168)
 	at org.apache.felix.scr.impl.BundleComponentActivator$ListenerInfo.serviceChanged(BundleComponentActivator.java:125)
 	at org.apache.felix.framework.EventDispatcher.invokeServiceListenerCallback(EventDispatcher.java:990)
 	at org.apache.felix.framework.EventDispatcher.fireEventImmediately(EventDispatcher.java:838)
 	at org.apache.felix.framework.EventDispatcher.fireServiceEvent(EventDispatcher.java:545)
 	at org.apache.felix.framework.Felix.fireServiceEvent(Felix.java:4833)
 	at org.apache.felix.framework.Felix.registerService(Felix.java:3804)
 	at org.apache.felix.framework.BundleContextImpl.registerService(BundleContextImpl.java:328)
 	at org.apache.aries.rsa.core.RemoteServiceAdminCore.exposeServiceFactory(RemoteServiceAdminCore.java:467)
 	at org.apache.aries.rsa.core.RemoteServiceAdminCore.importService(RemoteServiceAdminCore.java:421)
 	at org.apache.aries.rsa.core.RemoteServiceAdminInstance$1.run(RemoteServiceAdminInstance.java:76)
 	at org.apache.aries.rsa.core.RemoteServiceAdminInstance$1.run(RemoteServiceAdminInstance.java:74)
 	at java.base/java.security.AccessController.doPrivileged(Native Method)
 	at org.apache.aries.rsa.core.RemoteServiceAdminInstance.importService(RemoteServiceAdminInstance.java:74)
 	at org.apache.aries.rsa.topologymanager.importer.TopologyManagerImport.importService(TopologyManagerImport.java:167)
 	at org.apache.aries.rsa.topologymanager.importer.TopologyManagerImport.importServices(TopologyManagerImport.java:141)
 	at org.apache.aries.rsa.topologymanager.importer.TopologyManagerImport.syncronizeImports(TopologyManagerImport.java:128)
 	at org.apache.aries.rsa.topologymanager.importer.TopologyManagerImport.access$000(TopologyManagerImport.java:47)
 	at org.apache.aries.rsa.topologymanager.importer.TopologyManagerImport$1.run(TopologyManagerImport.java:119)
 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
 	at java.base/java.lang.Thread.run(Thread.java:834)
 ERROR: bundle org.glimmerveen.osgi.rsa.user:1.0.0.201904302118 (15)[org.glimmerveen.osgi.rsa.user.internal.User(0)] :  Error during instantiation of the implementation object: Unable to get service for reference $000
 ```

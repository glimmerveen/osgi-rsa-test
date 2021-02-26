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
23:11:50.192 WARN  [  TopologyManagerImport-0]               org.apache.aries.rsa.core.ClientServiceFactory - Problem creating a remote proxy for [org.glimmerveen.osgi.rsa.api.primary.PrimaryService, org.glimmerveen.osgi.rsa.api.secondary.SecondaryService]
java.lang.ClassNotFoundException: org.glimmerveen.osgi.rsa.api.secondary.SecondaryService not found by org.glimmerveen.osgi.rsa.user [21]
	at org.apache.felix.framework.BundleWiringImpl.findClassOrResourceByDelegation(BundleWiringImpl.java:1585)
	at org.apache.felix.framework.BundleWiringImpl.access$300(BundleWiringImpl.java:79)
	at org.apache.felix.framework.BundleWiringImpl$BundleClassLoader.loadClass(BundleWiringImpl.java:1970)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:521)
	at org.apache.aries.rsa.core.ClientServiceFactory.getService(ClientServiceFactory.java:64)
	at org.apache.felix.framework.ServiceRegistrationImpl.getFactoryUnchecked(ServiceRegistrationImpl.java:349)
	at org.apache.felix.framework.ServiceRegistrationImpl.getService(ServiceRegistrationImpl.java:249)
	at org.apache.felix.framework.ServiceRegistry.getService(ServiceRegistry.java:362)
	at org.apache.felix.framework.Felix.getService(Felix.java:3984)
	at org.apache.felix.framework.BundleContextImpl.getService(BundleContextImpl.java:450)
	at org.apache.felix.scr.impl.manager.SingleRefPair.getServiceObject(SingleRefPair.java:88)
	at org.apache.felix.scr.impl.inject.internal.ComponentConstructorImpl.newInstance(ComponentConstructorImpl.java:284)
	at org.apache.felix.scr.impl.manager.SingleComponentManager.createImplementationObject(SingleComponentManager.java:286)
	at org.apache.felix.scr.impl.manager.SingleComponentManager.createComponent(SingleComponentManager.java:115)
	at org.apache.felix.scr.impl.manager.SingleComponentManager.getService(SingleComponentManager.java:1000)
	at org.apache.felix.scr.impl.manager.SingleComponentManager.getServiceInternal(SingleComponentManager.java:973)
	at org.apache.felix.scr.impl.manager.AbstractComponentManager.activateInternal(AbstractComponentManager.java:785)
	at org.apache.felix.scr.impl.manager.DependencyManager$SingleStaticCustomizer.addedService(DependencyManager.java:1271)
	at org.apache.felix.scr.impl.manager.DependencyManager$SingleStaticCustomizer.addedService(DependencyManager.java:1222)
	at org.apache.felix.scr.impl.manager.ServiceTracker$Tracked.customizerAdded(ServiceTracker.java:1200)
	at org.apache.felix.scr.impl.manager.ServiceTracker$Tracked.customizerAdded(ServiceTracker.java:1121)
	at org.apache.felix.scr.impl.manager.ServiceTracker$AbstractTracked.trackAdding(ServiceTracker.java:928)
	at org.apache.felix.scr.impl.manager.ServiceTracker$AbstractTracked.track(ServiceTracker.java:864)
	at org.apache.felix.scr.impl.manager.ServiceTracker$Tracked.serviceChanged(ServiceTracker.java:1152)
	at org.apache.felix.scr.impl.BundleComponentActivator$ListenerInfo.serviceChanged(BundleComponentActivator.java:114)
	at org.apache.felix.framework.EventDispatcher.invokeServiceListenerCallback(EventDispatcher.java:990)
	at org.apache.felix.framework.EventDispatcher.fireEventImmediately(EventDispatcher.java:838)
	at org.apache.felix.framework.EventDispatcher.fireServiceEvent(EventDispatcher.java:545)
	at org.apache.felix.framework.Felix.fireServiceEvent(Felix.java:4863)
	at org.apache.felix.framework.Felix.registerService(Felix.java:3834)
	at org.apache.felix.framework.BundleContextImpl.registerService(BundleContextImpl.java:328)
	at org.apache.aries.rsa.core.RemoteServiceAdminCore.exposeServiceFactory(RemoteServiceAdminCore.java:483)
	at org.apache.aries.rsa.core.RemoteServiceAdminCore.importService(RemoteServiceAdminCore.java:437)
	at org.apache.aries.rsa.core.RemoteServiceAdminInstance$1.run(RemoteServiceAdminInstance.java:77)
	at org.apache.aries.rsa.core.RemoteServiceAdminInstance$1.run(RemoteServiceAdminInstance.java:75)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at org.apache.aries.rsa.core.RemoteServiceAdminInstance.importService(RemoteServiceAdminInstance.java:75)
	at org.apache.aries.rsa.topologymanager.importer.TopologyManagerImport.importService(TopologyManagerImport.java:149)
	at java.base/java.util.stream.ReferencePipeline$7$1.accept(ReferencePipeline.java:271)
	at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:177)
	at java.base/java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
	at org.apache.aries.rsa.topologymanager.importer.TopologyManagerImport.synchronizeImports(TopologyManagerImport.java:133)
	at org.apache.aries.rsa.topologymanager.importer.TopologyManagerImport.lambda$synchronizeImportsAsync$1(TopologyManagerImport.java:112)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:834)
ERROR : bundle org.glimmerveen.osgi.rsa.user:1.0.0.202102262211 (21)[org.glimmerveen.osgi.rsa.user.internal.User(1)] : Error during instantiation of the implementation object: Unable to get service for reference $000
 ```

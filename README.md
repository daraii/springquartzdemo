# spring-boot-quartz-demo

Goal of this demo is to run quartz in a spring-boot application using HikariCP connection provider and JobStoreTX.

Problems encountered:

When specifically setting the jobStore in properties, it still switches to JobStoreCMT during runtime if datasource is set

    //Java
    schedulerFactory.setDataSource(dataSource);
    
    //Properties
    org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
    
When datasource is not set, it switches to C3P0 by default and the application will fail with

    java.lang.ClassNotFoundException: com.mchange.v2.c3p0.ComboPooledDataSource

since I don't have it as a dependency. By default quartz runs using C3P0 connection provider which has some problems with it's dependencies, see https://stackoverflow.com/questions/49022204/system-cant-find-mchange-commons-java-0-2-11

In order to use JobStoreTX and HikariCP as a connection provider one needs to dive into the source code of quartz since there is little to no documentation on this matter. Regardless, the solutions turned out to be quite simple.

* Remove the `schedulerFactory.setDataSource(dataSource);` from java code
* Define a separate datasource for quartz
    
        spring.quartz.properties.org.quartz.jobStore.dataSource=myDS
        spring.quartz.properties.org.quartz.dataSource.quartzDS.driver=org.h2.Driver
        spring.quartz.properties.org.quartz.dataSource.quartzDS.URL=jdbc:h2:mem:test
        spring.quartz.properties.org.quartz.dataSource.quartzDS.user=sa
        spring.quartz.properties.org.quartz.dataSource.quartzDS.password=
        spring.quartz.properties.org.quartz.dataSource.quartzDS.maxConnections=1
        
* Define HikariCP as the datasource provider

        spring.quartz.properties.org.quartz.dataSource.quartzDS.provider=hikaricp
        
This will trigger a condition in org.quartz.impl.StdSchedulerFactory that initializes org.quartz.utils.HikariCpPoolingConnectionProvider as a connection provider

When the application is running you can access H2 console via

    http://localhost:8080/h2-console
# javaeesamplewebapp
A sample enterprise web application based on JavaEE to demonstrate JSF, EJB, JPA, CDI. Based on wildfly quickstart greeter project. All credits to these guys!

**-Tested environment-**

AS: `wildfly-10.1.0.Final`

DB: `mysql-5.7.20-win32`

**-Instructions-**

This project is the result of reforming the greeter project into an independent one that can use external non H2 datasources.
H2 datasource descriptor (`*-ds.xml`) file was deleted. Persistence.xml was updated to point to an external mysql database (jndi url, provider, dialect).

The project has its own dependencies to support all the fancy acronyms except for Hibernate which must be provided (otherwise an exception is thrown specific to Wildfly about org.dom4j.DocumentFactory).
Hibernate version was defined based on my testing environment (to find your wildfly hibernate core version, check `modules/../hibernate-core-{version}.jar`). 

Make sure that your wildfly application server (AS) supports mysql datasources. If not, seek for instructions on the web.
In short, you need to download the mysql jdbc jar driver and configure it as a wildfly module. Then, create a datasource.

It's important to remove the default datasource option in your `standalone.xml` (usually points to the `ExampleDS`). Wildfly assigns a default datasource to any deployed application, which is not desired here.

Deploy your app by running:

`mvn clean package wildlfy:deploy`

To enable the remote debug mode (default port `8787`), edit `standalone.bat` set `DEBUG=true`.


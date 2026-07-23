package com.barbighaiya;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Hello World!
 */
public class App {

    public static void main(String[] args) throws LifecycleException {

        // Printing a message on the console to verify that the main method is executed.
        System.out.println("Hello World!");

        /*
         * Creating an object of the Embedded Tomcat Server.
         *
         * Tomcat is a Servlet Container.
         * It is responsible for:
         * 1. Starting the web server.
         * 2. Loading Servlets.
         * 3. Handling client requests.
         * 4. Sending responses back to the client.
         *
         * Here we are using Embedded Tomcat instead of installing Tomcat separately.
         */
        Tomcat tomcat = new Tomcat();

        /*
         * setPort(int portNumber)
         *
         * This method is used to specify the port number on which
         * the Tomcat server should listen.
         *
         * Example:
         * tomcat.setPort(8080);
         *
         * If this method is not called, Embedded Tomcat uses its
         * default port (8080).
         *
         * Since 8080 is the default port, this statement is optional.
         */
        // tomcat.setPort(8080);

        /*
         * System.getProperty("user.dir")
         *
         * "user.dir" is a predefined Java System Property.
         *
         * It returns the absolute path of the current working directory
         * (i.e., the root folder of the current project).
         *
         * Example Output:
         * C:\Users\barbi\OneDrive\Desktop\Development Workspace\ServletBasics
         *
         * Tomcat needs this directory to create the Web Application Context.
         */
        String webappDir = System.getProperty("user.dir");

        /*
         * addContext(String contextPath, String docBase)
         *
         * This method creates a Web Application Context.
         *
         * It accepts two parameters:
         *
         * 1. contextPath
         *    - Represents the application name in the URL.
         *    - "" means Root Application.
         *
         *      Example:
         *      ""  -> http://localhost:8080/hello
         *      "/myapp" -> http://localhost:8080/myapp/hello
         *
         * 2. docBase
         *    - Represents the physical directory of the web application.
         *    - Here we are passing the current project directory.
         *
         * This method returns a Context object.
         */
        Context context = tomcat.addContext("", webappDir);

        /*
         * Tomcat.addServlet(Context context,
         *                   String servletName,
         *                   Servlet servlet)
         *
         * This is a static method of the Tomcat class.
         *
         * It is used to register a Servlet with the Tomcat Server.
         *
         * Parameters:
         *
         * 1. context
         *    -> The Context object in which the Servlet should be registered.
         *
         * 2. servletName
         *    -> A unique name given to the Servlet.
         *       It can be any valid name.
         *
         * 3. new HelloServlet()
         *    -> Object of our Servlet class.
         *
         * Registering the Servlet does NOT make it accessible through the browser.
         * URL mapping is still required.
         */
        Tomcat.addServlet(context, "HelloServlet", new HelloServlet());

        /*
         * addServletMappingDecoded(String urlPattern,
         *                          String servletName)
         *
         * This method maps a URL to a registered Servlet.
         *
         * Parameters:
         *
         * 1. urlPattern
         *    -> URL entered by the client.
         *
         *       Example:
         *       /hello
         *
         * 2. servletName
         *    -> Name of the Servlet that was registered using addServlet().
         *
         * IMPORTANT:
         * The servlet name must be exactly the same as used
         * in Tomcat.addServlet().
         *
         * Flow:
         *
         * Browser
         *     ↓
         * http://localhost:8080/hello
         *     ↓
         * URL Mapping
         *     ↓
         * HelloServlet
         */
        context.addServletMappingDecoded("/hello", "HelloServlet");

        /*
         * getConnector()
         *
         * Connector is responsible for:
         *
         * - Opening the server port.
         * - Listening for HTTP requests.
         * - Accepting client connections.
         *
         * If this method is not called,
         * Tomcat may initialize but it will not start listening
         * on port 8080.
         *
         * Therefore this method is mandatory while using
         * Embedded Tomcat.
         */
        tomcat.getConnector();

        /*
         * start()
         *
         * Starts the Embedded Tomcat Server.
         *
         * During startup Tomcat:
         *
         * 1. Initializes the server.
         * 2. Creates the Context.
         * 3. Loads all registered Servlets.
         * 4. Opens the configured port.
         * 5. Starts accepting client requests.
         *
         * This method can throw LifecycleException.
         * Hence we handle it using the throws declaration.
         */
        tomcat.start();

        /*
         * Printing a confirmation message after the server starts.
         */
        System.out.println("Tomcat started on http://localhost:8080/hello");

        /*
         * getServer()
         *
         * Returns the internal Server object of Tomcat.
         */

        /*
         * await()
         *
         * Keeps the server running indefinitely.
         *
         * Without this method,
         * the main() method would finish execution,
         * the JVM would terminate,
         * and Tomcat would stop immediately.
         *
         * Because of await(), the server keeps listening
         * for incoming client requests until it is stopped manually.
         */
        tomcat.getServer().await();
    }
}
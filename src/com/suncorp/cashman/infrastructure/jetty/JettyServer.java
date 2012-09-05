package com.suncorp.cashman.infrastructure.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;

public class JettyServer {

    public static final int PORT = 8181;

    private static JettyServer thisServer;

    private Server server;

    public static JettyServer getInstance() {
        if(thisServer==null) thisServer = new JettyServer();
        return thisServer;
    }

    private JettyServer() {
        this.server = new Server();
        this.server.addConnector(createConnector());
        this.server.setHandler(createWebApp());
    }

    public void start() throws Exception {
        System.out.println("Will start server");
        this.server.start();
        System.out.println("Server started");
    }

    public void stop() throws Exception {
        this.server.stop();
    }

    private WebAppContext createWebApp() {
        WebAppContext webApp = new WebAppContext();
        webApp.setResourceBase(getDistributionPath());
        webApp.setDescriptor("web.xml");
        webApp.setContextPath("/cashman");
        webApp.setParentLoaderPriority(true);
        return webApp;
    }

    private Connector createConnector() {
        Connector connector = new SelectChannelConnector();
        connector.setPort (PORT);
        connector.setHost ("127.0.0.1");
        return connector;
    }

    private String getDistributionPath() {
        Class thisClass = this.getClass();
        URL url = thisClass.getResource(getShortName(thisClass) + ".class");
        return url.getPath().split("WEB-INF")[0];
    }

    private static String getShortName(Class clazz) {
        String[] classNameElements = clazz.getName().split("\\.");
        return classNameElements[classNameElements.length-1];
    }

    public static void main(String[] args) throws Exception {
        JettyServer server = JettyServer.getInstance();
        server.start();
    }

}

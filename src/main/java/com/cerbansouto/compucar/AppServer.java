package com.cerbansouto.compucar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppServer {

    //TODO: set via config
    private int port = 8081;
    private String contextPath;
    private Class<?> springApplicationContextClass;
    private Server server;

    public AppServer(String path, Class<?> applicationContext) {
        contextPath = path;
        springApplicationContextClass = applicationContext;
    }

    public void run() throws Exception {
        server = new Server(port);
        server.setHandler(buildJettyContext());
        server.setStopAtShutdown(true);
        server.start();
        server.join();
    }

    private ServletContextHandler buildJettyContext() {
        ServletContextHandler jettyContext = new ServletContextHandler();
        jettyContext.setContextPath(contextPath);

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(springApplicationContextClass);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletHolder dispatcherServletHolder = new ServletHolder(dispatcherServlet);
        jettyContext.addServlet(dispatcherServletHolder, "/");

        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(applicationContext);
        jettyContext.addEventListener(contextLoaderListener);
        applicationContext.close();

        return jettyContext;
    }
}

package com.cerbansouto.compucar;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppServer {

    private int port = 8081;
    private String contextPath;
    private Class<?> springApplicationContextClass;
    private Server server;

    private ServletContextHandler servletContextHandler;
    private WebAppContext webAppContext;

    public AppServer(String path, Class<?> applicationContext) {
        contextPath = path;
        springApplicationContextClass = applicationContext;
    }

    public void run() throws Exception {
        server = new Server(port);
        server.setHandler(this.applicationHandlers());
        server.setStopAtShutdown(true);
        server.start();
        server.join();
    }

    private HandlerCollection applicationHandlers() throws Exception {
        HandlerCollection handlerCollection = new HandlerCollection();

        servletContextHandler = new ServletContextHandler();
        webAppContext = new WebAppContext();

        this.prepareJettyContexts();
        this.configureJspForWebContext();

        handlerCollection.setHandlers(new Handler[] { servletContextHandler, webAppContext });

        return handlerCollection;
    }

    private void prepareJettyContexts() {
        servletContextHandler.setContextPath(contextPath);
        webAppContext.setContextPath("/");

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(springApplicationContextClass);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);

        servletContextHandler.addServlet(servletHolder, "/");
        webAppContext.addServlet(servletHolder, "/");

        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(applicationContext);
        servletContextHandler.addEventListener(contextLoaderListener);
        webAppContext.addEventListener(contextLoaderListener);
        applicationContext.close();
    }

    private void configureJspForWebContext() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("/webapp");
        String resourceBasePath = classPathResource.getURI().toString();
        webAppContext.setResourceBase(resourceBasePath);
    }
}

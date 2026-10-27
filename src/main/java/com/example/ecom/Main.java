package com.example.ecom;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {

    private static final int PORT = 8081;
    private static final String WEBAPP_DIR = "src/main/webapp";
    private static final String CLASSES_DIR = "target/classes";

    public static void main(String[] args) throws Exception {
        File webappDir = new File(WEBAPP_DIR).getAbsoluteFile();
        File classesDir = new File(CLASSES_DIR).getAbsoluteFile();

        if (!webappDir.isDirectory()) {
            throw new IllegalStateException(
                    "Webapp directory not found: " + webappDir
                            + ". Make sure you are running from the project root.");
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.getConnector();

        Context context = tomcat.addWebapp("", webappDir.getAbsolutePath());

        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(
                resources, "/WEB-INF/classes", classesDir.getAbsolutePath(), "/"));
        context.setResources(resources);

        context.setParentClassLoader(Thread.currentThread().getContextClassLoader());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Stopping Tomcat...");
                tomcat.stop();
                tomcat.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        tomcat.start();
        System.out.println("==================================================");
        System.out.println(" Embedded Tomcat started");
        System.out.println(" Visit: http://localhost:" + PORT + "/");
        System.out.println(" Press Ctrl+C to stop");
        System.out.println("==================================================");
        tomcat.getServer().await();
    }
}
package com.stark.exam;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class ServerRunner {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        String webappDirLocation = "src/main/webapp/";
        File webappDir = new File(webappDirLocation);
        if (!webappDir.exists()) {
            webappDirLocation = "target/build/";
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector(); // Initialize connector

        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        File additionWebInfClasses = new File("target/build/WEB-INF/classes");
        if (!additionWebInfClasses.exists()) {
            additionWebInfClasses = new File("target/classes");
        }

        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        // Configure JSP compiler to use JDT compiler
        ctx.setJspConfigDescriptor(null);
        System.setProperty("org.apache.jasper.compiler.JsrJavaCompiler", "true");
        System.setProperty("tomcat.util.scan.DefaultJarScanner.jarsToSkip", "*.jar");

        System.out.println("=================================================");
        System.out.println("🚀 Online Examination System is running!");
        System.out.println("🌐 Local URL: http://localhost:" + port + "/");
        System.out.println("=================================================");

        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}

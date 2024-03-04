package com.redhat.hokuda;

import org.drools.core.common.ProjectClassLoader;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;

public class Listener implements ServletContextListener{
    
    @Override
    public void contextInitialized(ServletContextEvent event){
        ServletContext context=event.getServletContext();

        //ClassLoader loader = this.getClass().getClassLoader();
        //ClassLoader loader = ClassLoader.getSystemClassLoader();
        //ClassLoader loader = ClassLoader.getPlatformClassLoader();
        ClassLoader loader = ProjectClassLoader.createProjectClassLoader();
        System.out.println(loader);
        try {
            loader.loadClass("com.sun.crypto.provider.DESKey");
            //loader.loadClass("javax.crypto.spec.DESKeySpec");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event){
        // do nothing
    }
}

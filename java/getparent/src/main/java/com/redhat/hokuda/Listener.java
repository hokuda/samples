package com.redhat.hokuda;

import org.drools.core.common.ProjectClassLoader;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import java.lang.reflect.Method;

import java.util.*;


public class Listener implements ServletContextListener{
    
    @Override
    public void contextInitialized(ServletContextEvent event){
        ServletContext context=event.getServletContext();
        
        List<Integer> list = Arrays.asList(1, 3, 2);
        Collections.sort(list, (o1, o2) -> Integer.compare(o1, o2));
        System.err.println(list);


        

        String targetClassName  = "example.TestClass";
        String targetMethodName = "getMessage";
	
        try {
            Class c = Class.forName(targetClassName);
            Object service = (Object)c.newInstance();
            Class[] classArgs = new Class[]{String.class};
            Method mthod = Class.forName(targetClassName).getMethod(targetMethodName, classArgs);
            Object[] obj = new Object[]{"ゲスト"};
            Object res = mthod.invoke(service, obj);
            System.out.println("【"+targetClassName+targetMethodName+"の実行結果】");
            System.out.println("  " + res.toString());
        } catch (Exception e){
            e.printStackTrace();
        }


        
        ClassLoader loader = this.getClass().getClassLoader();
        //ClassLoader loader = ClassLoader.getSystemClassLoader();
        //ClassLoader loader = ClassLoader.getPlatformClassLoader();
        //ClassLoader loader = ProjectClassLoader.createProjectClassLoader();
        //System.out.println(loader);
        try {
            loader.loadClass("com.sun.crypto.provider.DESKey");
            //loader.loadClass("javax.crypto.spec.DESKeySpec");
        }
        catch(Exception e) {
            e.printStackTrace();
        }


        /////////////////////////////////////////////////////////

        //System.setSecurityManager(null);

        try {
            URL[] urls = {new URL("file:///home/hokuda/src/github/hokuda/samples/java/internal-getparent/target/internal-getparent-1.0-SNAPSHOT.jar")};
            URLClassLoader newClassLoader = URLClassLoader.newInstance(urls, loader);
            //Thread.currentThread().setContextClassLoader(newClassLoader);
            Class cls = newClassLoader.loadClass("com.redhat.hokuda.internal.App");
            System.err.println(cls);
            //Class.forName("com.github.sarxos.webcam.Webcam", true, newClassLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event){
        // do nothing
    }
}

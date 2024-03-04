package com.redhat.hokuda.internal;

/**
 * Hello world!
 *
 */
public class App 
{
    public String doGetParent() {
        ClassLoader loader = this.getClass().getClassLoader();
        System.out.println("doGetParent: " + loader);
        return loader.getParent().toString();
    }
}

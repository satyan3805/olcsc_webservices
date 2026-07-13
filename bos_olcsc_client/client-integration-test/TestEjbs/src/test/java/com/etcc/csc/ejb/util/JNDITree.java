/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.ejb.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * Dumps the JNDI Tree of the current App Server.  Utility class.
 * 
 * @author Stephen Davidson
 * 
 */
public class JNDITree {
    private Context context = null;

    public static void main(String[] args) throws Exception {
        new JNDITree().printJNDITree("");
        System.out.println("DONE");
    }

    public JNDITree() throws NamingException {
        setEnv();
    }

    /* Please modify this method or comment and use jndi.properties
    */
    public void setEnv() throws NamingException {
        Hashtable<String, String> env = new Hashtable<String, String>();
        // OC4J
        // env.put(Context.INITIAL_CONTEXT_FACTORY, "com.evermind.server.rmi.RMIInitialContextFactory");
        // env.put(Context.PROVIDER_URL, "ormi://172.16.x.x:12404");
        // env.put(Context.SECURITY_PRINCIPAL, "admin");
        // env.put(Context.SECURITY_CREDENTIALS, "welcome");
        // JBOSS
        // env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        // env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        // env.put(Context.PROVIDER_URL, "jnp://172.16.x.x:1099");
        // WEBLOGIC
        // env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        // env.put(Context.PROVIDER_URL, "t3://172.16.x.x:7001");
        //Jettty/OpenEJB
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");

        context = new InitialContext(env);
    }

    public void printJNDITree(String ct) {
        try {
            printNE(context.list(ct), ct);
        } catch (NamingException e) {
            // ignore leaf node exception
        }
    }

    private void printNE(NamingEnumeration<NameClassPair> ne, String parentctx) throws NamingException {
        if (ne == null){
            printEntry(null);
            return;
        } //else
        while (ne.hasMoreElements()) {
            NameClassPair next = ne.nextElement();
            printEntry(next);
            increaseIndent();
            printJNDITree((parentctx.length() == 0) ? next.getName() : parentctx + "/" + next.getName());
            decreaseIndent();
        }
    }

    private void printEntry(NameClassPair next) {
        System.out.println(printIndent() + "-->" + next);
    }

    private int indentLevel = 0;

    private void increaseIndent() {
        indentLevel += 4;
    }

    private void decreaseIndent() {
        indentLevel -= 4;
    }

    private StringBuilder printIndent() {
        StringBuilder buf = new StringBuilder(indentLevel);
        for (int i = 0; i < indentLevel; i++) {
            buf.append(" ");
        }
        return buf;
    }
}

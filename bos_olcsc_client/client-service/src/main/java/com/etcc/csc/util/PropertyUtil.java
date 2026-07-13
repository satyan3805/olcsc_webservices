/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * The methods here are copied from <code>com.etcc.csc.util.Util</code>.
 * @author Stephen Davidson
 *
 */
public abstract class PropertyUtil {
    private static final Logger logger = Logger.getLogger(PropertyUtil.class);
    
    private static final boolean THROW_ON_LOAD_FAILURE = true;
    private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
    private static final String SUFFIX = ".properties";


    /**
     * Constructor.  Should be a static utility object.
     * 
     */
    private PropertyUtil() {
        // end <init>
    }

//    /**
//     * Gets the field mapping for the message from the ErrorMapping.properties file.
//     * @param msg The key to get the message for.
//     * @return the message
//     * @deprecated Should only be called by the JSPs (or a custom tag) to return a language specific string
//     * based on locale.
//     */
//    @Deprecated
//    public static String getFieldMapping(String msg){
//        try {
//            String key = msg.substring(msg.lastIndexOf("-")+1);
//            Properties props = loadProperties("ErrorMapping");
//            return props.getProperty(key);
//
//        } catch (Exception e) {
//            logger.error("Error loading ErrorMapping.properties: "+e.getMessage());
//            return null;
//        }
//    }

    /**
     * Looks up a resource named 'name' in the classpath. The resource must map
     * to a file with .properties extension. The name is assumed to be absolute
     * and can use either "/" or "." for package segment separation with an
     * optional leading "/" and optional ".properties" suffix. Thus, the
     * following names refer to the same resource:
     * <pre>
     * some.pkg.Resource
     * some.pkg.Resource.properties
     * some/pkg/Resource
     * some/pkg/Resource.properties
     * /some/pkg/Resource
     * /some/pkg/Resource.properties
     * </pre>
     *
     * @param name classpath resource name [may not be null]
     * @param loader classloader through which to load the resource [null
     * is equivalent to the application loader]
     *
     * @return resource converted to java.util.Properties [may be null if the
     * resource was not found and THROW_ON_LOAD_FAILURE is false]
     * @throws IllegalArgumentException if the resource was not found and
     * <tt>THROW_ON_LOAD_FAILURE</tt> is <tt>true</tt>
     */
    public static Properties loadProperties(String name, ClassLoader loader) { 
        if (name == null)
            throw new IllegalArgumentException("null input: name");

        if (name.startsWith("/"))
            name = name.substring(1);

        if (name.endsWith(SUFFIX))
            name = name.substring(0, name.length() - SUFFIX.length());

        Properties result = null;
        InputStream in = null;
        Exception loadError = null;
        try {
            if (loader == null) loader = ClassLoader.getSystemClassLoader();

            if (LOAD_AS_RESOURCE_BUNDLE) {
                // convert to resource-based locator
                name = name.replace ('/', '.');
                // Throws MissingResourceException on lookup failures:
                final ResourceBundle rb = ResourceBundle.getBundle(name,
                        Locale.getDefault(), loader);
                result = new Properties();
                for(Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();){
                    final String key = keys.nextElement();
                    result.put(key, rb.getString(key));
                }
            } else {
                // convert to filesystem-based locator
                name = name.replace ('.', '/');
                // add SUFFIX back (replace would mess this up if it wasn't first removed)
                if (! name.endsWith(SUFFIX))
                    name = name.concat(SUFFIX);
                // Returns null on lookup failures:
                in = loader.getResourceAsStream (name);
                if (in != null) {
                    result = new Properties();
                    result.load(in); // Can throw IOException
                }
            }
        } catch (Exception e) {
            result = null;
            loadError = e;
        } finally {
            if (in != null) try { in.close (); } catch (Throwable ignore) {}
        }

        if (THROW_ON_LOAD_FAILURE && (result == null)) {
            String msg = "could not load [" + name + "]"+
                    " as " + (LOAD_AS_RESOURCE_BUNDLE
                    ? "a resource bundle"
                    : "a classloader resource");
            IllegalArgumentException t = (loadError == null ? 
                new IllegalArgumentException(msg) : new IllegalArgumentException(msg, loadError));
            logger.fatal(msg, t);
            throw t;
        }

        return result;
    }

    /**
     * A convenience overload of {@link #loadProperties(String, ClassLoader)}
     * that uses the current thread's context class loader.
     */
    public static Properties loadProperties(final String name) {
        return loadProperties(name, Thread.currentThread().getContextClassLoader());
    }
}

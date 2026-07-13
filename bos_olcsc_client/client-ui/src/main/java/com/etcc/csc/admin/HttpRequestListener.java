package com.etcc.csc.admin;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class HttpRequestListener implements ServletRequestListener
{
    private static String localName;

    public void requestDestroyed(ServletRequestEvent servletRequestEvent)
    {
    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent)
    {
      localName = servletRequestEvent.getServletRequest().getLocalName( );
    }

    public static String getLocalName( )
    {
      return localName;
    }
}

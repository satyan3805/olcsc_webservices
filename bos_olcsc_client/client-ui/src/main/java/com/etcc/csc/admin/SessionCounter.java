package com.etcc.csc.admin;

import com.etcc.csc.delegate.AdminDelegate;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounter implements HttpSessionListener
{
  private static int activeSessions = 0;

    public void sessionCreated(HttpSessionEvent httpSessionEvent )
    {
        //BUG: This is NOT thread safe, and is accessed in a multithreaded manner!
      activeSessions++;
      new AdminDelegate( ).insertSessionCount( HttpRequestListener.getLocalName( ), activeSessions );
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
    {
        //BUG: This is NOT thread safe, and is accessed in a multithreaded manner!
      if ( activeSessions > 0 )
      {
        activeSessions--;
      }
      new AdminDelegate( ).insertSessionCount( HttpRequestListener.getLocalName( ), activeSessions );
    }

    public static int getActiveSessions( ) {
        return activeSessions;
    }
}
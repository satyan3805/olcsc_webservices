package com.etcc.csc.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This filter is added to print the SOAP requests
 * @author nveeraiah
 *
 */

public class LogRequestFilter implements Filter {

   private static final Log LOG = LogFactory.getLog(LogRequestFilter.class);

   /**
    * DoFilter method.
    */
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
         throws IOException, ServletException {
      LoggingRequest req = new LoggingRequest((HttpServletRequest)request);
      if (response.getCharacterEncoding() == null) {
          response.setCharacterEncoding("UTF-8"); // Or whatever default. UTF-8 is good for World Domination.
      }

      LoggingResponse responseCopier = new LoggingResponse((HttpServletResponse) response);

      chain.doFilter(req, responseCopier);
      byte[] copy = responseCopier.getCopy();
      LOG.info("SOAP Request ---->"+req.getPayload());
      LOG.info("SOAP Response ---->"+new String(copy, response.getCharacterEncoding()));
   }

   /**
    * Init method.
    */
   public void init(FilterConfig config) throws ServletException {
   }

   /**
    * Destroy method.
    */
   public void destroy() {
   }

   /* Private class definitions */
   private class LoggingRequest extends HttpServletRequestWrapper {

      private LoggingInputStream is;

      public LoggingRequest(HttpServletRequest request) throws IOException {
         super(request);
         this.is = new LoggingInputStream(request.getInputStream());
      }

      @Override
      public ServletInputStream getInputStream() throws IOException {
         return is;
      }

      public String getPayload() {
         return is.getPayload();
      }
   }

   private class LoggingInputStream extends ServletInputStream {

      private StringBuffer bfr = new StringBuffer();
      private ServletInputStream is;

      public LoggingInputStream(ServletInputStream is) {
         super();
         this.is = is;
      }

      // Since we are not sure which method is used just overwrite all 4 of them:
      @Override
      public int read() throws IOException {
         int ch = is.read();
         if (ch != -1) {
            bfr.append((char)ch);
         }
         return ch;
      }

      @Override
      public int read(byte[] b) throws IOException {
         int ch = is.read(b);
         if (ch != -1) {
            for (byte byteSingle: b )
              bfr.append( (char)byteSingle);
         }
         return ch;
      }

     /* @Override
      public int read(byte[] b, int o, int l) throws IOException {
         int ch = is.read(b,o,l);
         if (ch != -1) {
            for (byte byteSingle: b )
               bfr.append( (char)byteSingle);

         }
         return ch;
      }*/
      @Override
      public int readLine(byte[] b, int o, int l) throws IOException {
         int ch = is.readLine(b,o,l);
         if (ch != -1) {
            bfr.append(b);
         }
         return ch;
      }

      public String getPayload() {
          String payload = bfr.toString();
          return replaceData(payload); 
      }
       private String replaceData(String payload) {
          payload = payload.replaceAll("(?<=<in1>)[^<]+(?=</in1>)", "****");
          return payload;
      }
   }
}

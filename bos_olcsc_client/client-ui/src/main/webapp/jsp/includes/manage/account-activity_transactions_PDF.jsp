<%@ page import="java.lang.*" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
   <%          ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    byte[] inBytes=(byte[])request.getAttribute("byteArray");
                 

                         if(inBytes !=null){
                         //outStream.write(inBytes);
                         outStream.write(inBytes);
                         }
                         outStream.flush();                        
                         response.setHeader("Content-disposition","attachment; filename=" +"Report.pdf" );                        
                         response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
                         response.setHeader("Pragma", "public");

                         response.setHeader("Pragma", "no-cache"); //HTTP 1.0
                         response.setDateHeader("Expires", 0); //prevents caching at the proxy        server
                         response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
                         response.setHeader("Cache-Control", "max-age=0");                  
                         response.setContentType("application/pdf");
                         response.setContentLength(inBytes.length);
                         
                         ServletOutputStream str = response.getOutputStream();
                         str.write(inBytes);
                         
                         
                         %>
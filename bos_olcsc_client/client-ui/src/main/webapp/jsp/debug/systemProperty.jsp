<html>
<body>

<table border="1">

<%
    java.util.Enumeration propertyNames = System.getProperties().propertyNames();
  
    while ( propertyNames.hasMoreElements( ) )
    {
      String propertyName = propertyNames.nextElement( ).toString();
      out.println( "<tr>" );
      out.println( "<td STYLE=\"table-layout:fixed\" width=200>" + propertyName + "</td>" );
      out.println( "<td width=400>" + System.getProperty( propertyName ) + "</td>" );
      out.println( "</tr>" );
    }
 
%>

</table>
</body>
</html>

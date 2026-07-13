<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/cookie.js" ></script>
    <title>postTest</title>
  </head>
  <body>
    <%--<jsp:forward page="/AccountLogin.do"/>--%>
    <form action="http://revamp.hctra.org/eztagstore/authenticateUser.do" method="post">
        <input type="text" id="username" name="userName"/> 
        <input type="password" id="password" name="password" />
        <input type="submit"/>
    </form>
  </body> 
  
</html>
<script type="text/javascript">
    var idCookie = readCookie('OLCSC_ACCTID')  ;
    alert (idCookie);
</script>
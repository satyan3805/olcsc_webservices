<!-- the width & height of this element are set through CSS -->
<%@ include file="/jsp/common/Taglibs.jsp" %>
<jsp:useBean id="appDelegate"  class="com.etcc.csc.delegate.AppDelegate" scope="page"/>

<a href="${appDelegate.domainName}/" id="logo" rel="home"><img src="${pageContext.request.contextPath}/meta/media/common/logo.png" alt="Home" /></a>

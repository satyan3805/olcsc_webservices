Services are added to the Services module.  If they are to be exposed as WebServices, the module
Web/src/main/webapp/META-INF/xfire/services.xml needs to be updated.
===============================================================================================
http://jira.codehaus.org/browse/XFIRE-821

This project is triggering this bug.

For a WAR-ONLY deployment, on the OC4J console, disable the following libraries;
oracle.jwsdl
oracle.ws.client
oracle.ws.jaxrpc
oracle.xml
soap
===============================================================================================

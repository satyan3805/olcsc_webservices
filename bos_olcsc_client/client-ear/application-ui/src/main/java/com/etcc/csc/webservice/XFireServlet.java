/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.webservice;

import java.util.Collection;

import javax.servlet.ServletException;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.aegis.type.Type;
import org.codehaus.xfire.aegis.type.TypeMapping;
import org.codehaus.xfire.aegis.type.basic.ObjectType;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.ServiceRegistry;
import org.codehaus.xfire.soap.SoapConstants;
import org.codehaus.xfire.transport.http.XFireConfigurableServlet;

import com.etcc.csc.cart.CartList;
import com.etcc.csc.service.webservice.EnhancedCollectionType;

/**
 * Configures the custom Datatypes for XFire.  
 * <b>Not yet ready for use -- there is an instantiation issue with this servlet!</b>
 * The particular task that this Servlet was initially created to handle (a Shopping Cart issue) could
 * be handled by other means that while less generic (read - more fragile), could be brought online in the
 * time frame involved.  Specifically, at this point, there is only one use case for this servlet.
 * @see EnhancedCollectionType
 * @author (task 488) Stephen Davidson
 *
 */
//BUG: At this time, this servlet can not be instantiated.
@SuppressWarnings("serial")
public class XFireServlet extends XFireConfigurableServlet {
    
    @Override
    public void init() throws ServletException {
        final Logger logger = Logger.getLogger(XFireServlet.class);
        final boolean traceEnabled = logger.isTraceEnabled();
        logger.debug("Initializing XFire Services.");
        
        super.init();
 
        ServiceRegistry serviceRegistry = getXFire().getServiceRegistry();
        @SuppressWarnings({"cast", "unchecked"})
        final Collection<Service> services = (Collection<Service>) serviceRegistry.getServices();
        for( Service service : services ) {
            TypeMapping tm = ((AegisBindingProvider) service.getBindingProvider()).getTypeMapping(service);
            tm.register(new EnhancedCollectionType(createObjectType(tm)));
            if (traceEnabled){
                logger.trace("Registering EnhanceCollectionType for service " + service.getName());
            }
        }
        logger.debug("Initialization complete.");
    }
    
    private Type createObjectType(final TypeMapping tm){
        ObjectType type = new ObjectType();
        type.setSchemaType(new QName(SoapConstants.XSD, "cartList", SoapConstants.XSD_PREFIX));
        type.setTypeClass(CartList.class);
        type.setTypeMapping(tm);
        return type;
    }



}

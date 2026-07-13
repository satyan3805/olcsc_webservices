/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.util;

import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.ErrorMessageDTO;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class DtoUtil {

    private static Logger logger = Logger.getLogger(DtoUtil.class);
    private static final String[] COPY_ENABLED_TYPES = new String[] {
        "boolean", "byte", "char", "double", "float", "int", // primitives
        "long", "short", 
        "java.util.Date", "java.util.Calendar", // java.util
        "java.lang.Boolean", "java.lang.Byte", "java.lang.Character", // java.lang
        "java.lang.Double", "java.lang.Float", "java.lang.Integer",
        "java.lang.Long", "java.lang.Number", "java.lang.Short",
        "java.lang.String", "java.math.BigDecimal"

    };
    private Object sourceBean;
    private Object destinationBean;
    private PropertyDescriptor[] sourcePropertyDescriptors;
    private PropertyDescriptor[] destinationPropertyDescriptors;
    private ArrayList<PropertyDescriptor> nonCopiedProperties;
    
    public static String names(ArrayList<PropertyDescriptor> theDescriptors) {
        StringBuilder sb = new StringBuilder("");
        if (theDescriptors != null) {
            Iterator<PropertyDescriptor> theItems = theDescriptors.iterator();
            while (theItems.hasNext()) {
                PropertyDescriptor anItem = theItems.next();
                if (anItem != null) {
                    if (sb.length() != 0)
                        sb.append(",");
                    sb.append(anItem.getName());
                }
            }
        }
        return sb.toString();
    }
    
    public DtoUtil() {
    }
    
    public DtoUtil(Object theDestinationBean, Object theSourceBean) {
        this.setSourceBean(theSourceBean);
        this.setDestinationBean(theDestinationBean);
    }
    
    public ArrayList<PropertyDescriptor> copyProperties() {
        PropertyDescriptor theSourceDescriptors[] = getSourcePropertyDescriptors();
        ArrayList<PropertyDescriptor> theNonCopiedProps = new ArrayList<PropertyDescriptor>();
        for (int i = 0; i < theSourceDescriptors.length; i++) {
            if (isCopyEnabled(theSourceDescriptors[i])) {
                if (copyProperty(theSourceDescriptors[i]) == false)
                    theNonCopiedProps.add(theSourceDescriptors[i]);
            } else {
                theNonCopiedProps.add(theSourceDescriptors[i]);
            }
        }
        this.setNonCopiedProperties(theNonCopiedProps);
        return theNonCopiedProps;
    }
    
    protected PropertyDescriptor[] getSourcePropertyDescriptors() {
        try {
            if (this.sourcePropertyDescriptors == null)
                this.sourcePropertyDescriptors = DtoUtil.getPropertyDescriptors(this.getSourceBean());
            return this.sourcePropertyDescriptors;
        } catch (IntrospectionException ie) {
            logger.error("Failed to retrieve source bean properties: " + ie.getMessage(), ie);
            return new PropertyDescriptor[0];
        }
    }
    
    protected PropertyDescriptor[] getDestinationPropertyDescriptors() throws IntrospectionException {
        if (this.destinationPropertyDescriptors == null)
            this.destinationPropertyDescriptors = DtoUtil.getPropertyDescriptors(this.getDestinationBean());
        return this.destinationPropertyDescriptors;
    }
    
    private static boolean isCopyEnabled(PropertyDescriptor aPropertyDescriptor) {
        if (aPropertyDescriptor == null)
            return false;
        if (aPropertyDescriptor instanceof IndexedPropertyDescriptor)
            return false; // for simplification we ignore indexed properties
        Class<?> aPropertyType = aPropertyDescriptor.getPropertyType();
        String aPropertyTypeName = aPropertyType.getName();
        for (int i = 0; i < COPY_ENABLED_TYPES.length; i++) { // simplify life
            if (aPropertyTypeName.equals(COPY_ENABLED_TYPES[i]))
                return true;
        }
        return false;
    }
    
    private boolean copyProperty(PropertyDescriptor aSourceDescriptor) {
        try {
            String name = aSourceDescriptor.getName();
            if (isReadable(aSourceDescriptor)) {
                if (isWriteable(this.getDestinationPropertyDescriptors(), name)) {
                    Object value = getProperty(name);
                    setProperty(name, value);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            //logger.error("Failed to copy property: " + e.getMessage(), e);
            return false;
        }
    }
    
    private static boolean isReadable(PropertyDescriptor aDescriptor) {
        if (aDescriptor == null)
            return false;
        return (aDescriptor.getReadMethod() != null);
    }
    
    private static boolean isWriteable(PropertyDescriptor[] theDescriptors, String aDescriptorName) {
        if (theDescriptors == null)
            return false;
        if (aDescriptorName == null)
            return false;
        PropertyDescriptor aPropertyDescriptor = propertyDescriptor(theDescriptors, aDescriptorName);
        return (aPropertyDescriptor != null);
    }
    
    private static PropertyDescriptor propertyDescriptor(PropertyDescriptor[] theDescriptors, String aDescriptorName) {
        if (theDescriptors == null)
            return null;
        if (aDescriptorName == null)
            return null;
        for (int i = 0; i < theDescriptors.length; i++) {
            if (aDescriptorName.equals(theDescriptors[i].getName()))
                return theDescriptors[i];
        }
        return null;
    }
    
    private Object getProperty(String aDescriptorName) throws IllegalAccessException, 
                                                              InvocationTargetException {
        PropertyDescriptor aPropertyDescriptor = propertyDescriptor(this.getSourcePropertyDescriptors(), aDescriptorName);
        Method aMethod = aPropertyDescriptor.getReadMethod();
        return aMethod.invoke(this.getSourceBean(), new Object[0]);
    }
    
    private void setProperty(String aDescriptorName, Object aValue) throws IntrospectionException, 
                                                   IllegalAccessException, 
                                                   InvocationTargetException {
        PropertyDescriptor aPropertyDescriptor = propertyDescriptor(this.getDestinationPropertyDescriptors(), aDescriptorName);
        Method aMethod = aPropertyDescriptor.getWriteMethod();
        aMethod.invoke(this.getDestinationBean(), new Object[] { aValue });
    }
    
    
    
    private static PropertyDescriptor[] getPropertyDescriptors(Object aBean) throws IntrospectionException {
        if (aBean == null)
            throw new IllegalArgumentException("Bean is not specified");
        BeanInfo aBeanInfo = null;
        aBeanInfo = Introspector.getBeanInfo(aBean.getClass());
        PropertyDescriptor[] theBeanDescriptors = aBeanInfo.getPropertyDescriptors();
        if (theBeanDescriptors == null) {
            theBeanDescriptors = new PropertyDescriptor[0];
        }
        return theBeanDescriptors;
    }
    
    
    
    public static boolean copyProperties(Object dest, Object orig) {
        try {
            PropertyUtils.copyProperties(dest, orig);
            return true;
        } catch (Exception e) {
            logger.error("copyProperties failed: " + e.getMessage());
            return false;
        }
    }

    public void setSourceBean(Object sourceBean) {
        this.sourceBean = sourceBean;
    }

    public Object getSourceBean() {
        return sourceBean;
    }

    public void setDestinationBean(Object destinationBean) {
        this.destinationBean = destinationBean;
    }

    public Object getDestinationBean() {
        return destinationBean;
    }

    public void setNonCopiedProperties(ArrayList<PropertyDescriptor> nonCopiedProperties) {
        this.nonCopiedProperties = nonCopiedProperties;
    }

    public ArrayList<PropertyDescriptor> getNonCopiedProperties() {
        return nonCopiedProperties;
    }

/*
    public static void setErrors(Collection theErrors, com.etcc.csc.ws.types.BaseDTO aDestination) {
        if (aDestination == null)
            return;
        if (theErrors == null)
            aDestination.setErrors(null);
        aDestination.setErrors(new ArrayList(theErrors));
    }
    */

    @SuppressWarnings("deprecation")
	@Deprecated
    public static void setErrors(Collection<String> errors, BaseDTO aDestination) {
        if (aDestination != null)
        	aDestination.setErrors(BaseDTO.convertToMessages(errors));
    }

    @Deprecated
    public static Collection<String> convertArrayErrors(String errors[]) {
    	return Arrays.asList(errors);
//        Collection<String> result = new ArrayList<String>();
//        int n = 0;
//        if ( errors != null ) {
//          n = errors.length;
//          for (int i=0; i<n;++i) {
//              result.add(errors[i]);
//          }
//        }
//        return result;
    }

    @Deprecated
    public static String[] toStringArray(Collection<String> aCollection) {
    	return aCollection.toArray(new String[]{});
//        ArrayList<String> aStringArray = toStringArrayList(aCollection);
//        if (aStringArray == null)
//            return null;
//        return aStringArray.toArray(new String[aStringArray.size()]);
    }

    @Deprecated
    public static List<String> toStringList(Collection<String> aCollection) {
        return toStringArrayList(aCollection);
    }

    @Deprecated
    private static ArrayList<String> toStringArrayList(Collection<String> aCollection) {
        if (aCollection == null)        
            return null;
        ArrayList<String> aList = new ArrayList<String>(aCollection.size());
        Iterator<String> items = aCollection.iterator();
        while (items.hasNext()) {
            Object anItem = items.next();
            if (anItem instanceof String) {
                aList.add((String)anItem);
            }
        }
        return aList;     
    }

    public static ActionMessage createActionMessage(Object key, boolean resource) {
        return new ActionMessage(retrieveMsg(key), resource);
    }

    public static ActionMessage createActionMessage(String key, Object value) {
        return new ActionMessage(key, retrieveMsg(value));
    }

    public static ActionMessage createActionMessage(ErrorMessageDTO error) {
        return new ActionMessage(error.getKey(), error.getMessage());
    }

    /**
     * Create action messages from the specified property and error messages.
     * @param property Property name
     * @param errors error messages to convert
     * @return the action messages
     * @see ActionMessages#add(String, ActionMessage)
     * @author Milosh Boroyevich
     */
    public static ActionMessages createActionMessages(final String property, ErrorMessageDTO[] errors) {
    	if (errors == null)
    		return null;
    	ActionMessages messages = new ActionMessages();
    	for (ErrorMessageDTO error : errors)
    		messages.add(property, createActionMessage(error));
    	return messages;
    }

    public static String retrieveMsg(Object msgObject) {
        if (msgObject == null) { // This should never occur
            logger.info("retrieveMsg encountered non-existing message object");
            return "An error occured, please try again later";
        }
        if (msgObject instanceof String) {
            return (String)msgObject;
        } else if (msgObject instanceof ErrorMessageDTO) {
            ErrorMessageDTO aDto = (ErrorMessageDTO)msgObject;
            return aDto.getMessage();
        } else if (msgObject instanceof AlertDTO) {
        	AlertDTO aDto = (AlertDTO)msgObject;
            return aDto.getAlertMsg();
        } else { // This should never occur
            final String msg = msgObject.toString();
            logger.error("Unable to parse error class " + msgObject.getClass().getName() + ": " + msg);
            return msg;
        }
    }

    /**
     * Construct an action message with the specified replacement values.
     * @param key Message key for this message
     * @param value0 First replacement value
     * @param value1 Second replacement value
     * @returns the action message
     * @see ActionMessage#ActionMessage(String, Object[])
     */
    public static ActionMessage createActionMessage(String key, String value0, String value1) {
        return new ActionMessage(key, new Object[] { value0, value1 });
    }

    /**
     * All of the <tt>EtccSecurityException</tt> classes should be collapsed into one.
     * @deprecated Just catch the exception of interest!
     * @see com.etcc.csc.common.EtccSecurityException
     */
    @Deprecated
    public static boolean isEtccSecurityException(Throwable t) {
        if (t == null)
            return false;
        String aClassName = t.getClass().getName();
        return aClassName.endsWith("EtccSecurityException");
    }

    /**
     * All of the <tt>EtccErrorMessageException</tt> classes should be collapsed into one.
     * @deprecated Just catch the exception of interest!
     * @see com.etcc.csc.common.EtccErrorMessageException
     */
    @Deprecated
    public static boolean isEtccErrorMessageException(Throwable t) {
        if (t == null)
            return false;
        String aClassName = t.getClass().getName();
        return aClassName.endsWith("EtccErrorMessageException");
    }
}

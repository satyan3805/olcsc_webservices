/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.service.webservice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.aegis.MessageWriter;
import org.codehaus.xfire.aegis.type.Type;
import org.codehaus.xfire.aegis.type.basic.BeanType;
import org.codehaus.xfire.aegis.type.basic.BeanTypeInfo;
import org.codehaus.xfire.aegis.type.collection.CollectionType;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;
import org.codehaus.xfire.fault.XFireFault;
import org.codehaus.xfire.soap.SoapConstants;
import org.codehaus.xfire.util.NamespaceHelper;
import org.jdom.Attribute;
import org.jdom.Element;

/**
 * <p><b>WARNING - not yet tested or ready for use!  This implementation is not complete!</b>.  It <i>should</i> write,
 * but it will not read.  You have been warned.  This class should be completed for future versions if Webservices
 * will still be supported down the road.</p>
 * <p>This Type is for Reading/Writing Collections with additional Properties.  This Type requires a custom loader such 
 * as the com.etcc.csc.webservice.XFireServlet to configure the Type Registry on the Server side.  Due to build and 
 * classloader conflict with the Servlet during 488, it was decided to go with a "one-off" solution for the original use
 * case.</p>
 * @author (task 488) Stephen Davidson
 *
 */
public class EnhancedCollectionType extends CollectionType {
    
//    private static final Logger logger = Logger.getLogger(EnhancedCollectionType.class);

    /**
     * @param componentType
     */
    public EnhancedCollectionType(Type componentType) {
        super(componentType);
    }

    //TODO: Te
    @Override
    public void writeSchema(Element root) {
        super.writeSchema(root);
        //Find the schema that was just written.
        @SuppressWarnings("unchecked")
        List<Element> roots = root.getChildren("complexType");
        Element collectionElement = null;
        final String localName = getSchemaType().getLocalPart();
        for (Element element : roots) {
            if (element.getAttribute("name").getValue() == localName){
                collectionElement = element;
                break;
            }
        }
        if (collectionElement == null){
            throw new NullPointerException("Collection Element not found for schema: " + localName);
        }
        Element sequence = collectionElement.getChild("sequence");
        @SuppressWarnings("unchecked")
        Class<? extends Collection<?>> clazz = getTypeClass();
        AttributeWriter fieldWriter = new AttributeWriter(root, sequence);
        writeFields(clazz, fieldWriter);
        
        throw new UnsupportedOperationException("writeSchema not implemented yet.");
        
    }

    @Override
    public void writeObject(Object object, MessageWriter writer, MessageContext context) throws XFireFault {
        if (object == null){
            //Nothing to write
            return;
        }
        //Write the contents
        super.writeObject(object, writer, context);
        //Write any additional Attributes
        Class<?> clazz = object.getClass();
        ValueWriter fieldWriter = new ValueWriter(object, writer);
        writeFields(clazz, fieldWriter);
    }
    
    /**
     * Writes the fields of an object
     * @param clazz The Class of the Object being written.
     * @param fieldWriter The writer to use.
     */
    private void writeFields(Class<?> clazz, FieldWriter fieldWriter){
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0){
            //No fields to write
            return;
        }//else write the fields
        for (Field field : fields) {
            if (!field.isAccessible()){
                field.setAccessible(true);
            }
            Annotation[] annotations = field.getAnnotations();
            if (annotations!= null){
                //Ignore?
                boolean skip = false;
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == IgnoreProperty.class){
                        //Skip this property.
                        skip = true;
                        break;
                    }
                }
                if (skip) continue;
            }
            fieldWriter.writeField(field);
        }
    }
    
    private abstract class FieldWriter{
        abstract void writeField(final Field field);
        protected FieldWriter(){/*<init>*/}
    }
    
    private class ValueWriter extends FieldWriter{

        final Object object; //Object to write the Values from.
        final MessageWriter writer; //The Writer to use.
        
        /**
         * @param object Object with the Value to write.
         * @param writer The writer to sent the value to.
         */
        public ValueWriter(Object object, MessageWriter writer) {
            super();
            this.object = object;
            this.writer = writer;
        }
        
        @Override
        public void writeField(final Field field){
            try {
                Object value = field.get(this.object);
                this.writer.writeValue(value);
            } catch (IllegalArgumentException e) {
                // Should not be thrown
                throw new RuntimeException("Unable to access field " + field.getName() + " on " + this.object.getClass().getName()
                        + ": " + e.getMessage(), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access field " + field.getName() + " on " + this.object.getClass().getName()
                        + ": " + e.getMessage(), e);
            }

        }
    }
    
    private class AttributeWriter extends FieldWriter{
        final Element root;
        final Element seq;
        final String defaultNamespace;
        
        /**
         * @param root The root element
         * @param seq The Sequence Element.
         */
        public AttributeWriter(Element root, Element seq) {
            super();
            this.root = root;
            this.seq = seq;
            this.defaultNamespace = getComponentType().getSchemaType().getNamespaceURI();
        }

        @Override
        public void writeField(final Field field){
            
            Element element = new Element("element", SoapConstants.XSD_PREFIX, SoapConstants.XSD);
            this.seq.addContent(element);
            
            final BeanTypeInfo info = new BeanTypeInfo(field.getType(),this.defaultNamespace, true);
            BeanType componentType = new BeanType(info);

            String prefix = NamespaceHelper.getUniquePrefix((Element) this.root.getParent(), 
                    componentType.getSchemaType().getNamespaceURI());

            element.setAttribute(new Attribute("name", prefix + ":" + field.getName()));
            element.setAttribute(new Attribute("type", field.getType().getSimpleName().toLowerCase()));

            if (componentType.isNillable()) {
                element.setAttribute(new Attribute("nillable", "true"));
            }
            element.setAttribute(new Attribute("minOccurs", "1"));
            element.setAttribute(new Attribute("maxOccurs", "1"));
            
            if (componentType.isComplex()){
                componentType.writeSchema(this.root);
            }

        }
        
    }

}

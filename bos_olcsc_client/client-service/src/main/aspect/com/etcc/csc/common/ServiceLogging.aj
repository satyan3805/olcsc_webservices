package com.etcc.csc.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import com.etcc.csc.util.ReflectionUtils;
import com.etcc.csc.validation.ValidationException;

/**
 * Logging of Exceptions from the Service Tiers.  Similar to the DelegateLogging Aspect in the UI tier.  Note that
 * if any updates are made here, the UI version may need to be updated as well.  Note that Security and Validation
 * Exceptions are NOT logged at this level.
 * @author Stephen Davidson
 */
aspect ServiceLogging percflow(topLevelServiceOperation()){
    declare precedence: *, ServiceLogging;
    
    private Logger aspectLogger;
    
    protected pointcut topLevelServiceOperation():
        serviceOperation() && !cflowbelow(serviceOperation());

    pointcut serviceOperation():
        target(com.etcc.csc.service.ServiceInterface+)
        && execution(public * com.etcc.csc.service.*.*(..))
        //HACK: The above line is picking up some methods in many but not all DAOs.
        && !target(com.etcc.csc.dao.BaseDAO+)
        && !execution(public * com.etcc.csc.service.*Test.*(..))
        ;
    
    before():serviceOperation(){
        Signature sig = thisJoinPointStaticPart.getSignature();
        if (this.aspectLogger == null){
            this.aspectLogger = Logger.getLogger(sig.getDeclaringType());
        }
        this.aspectLogger.trace("Aspect Logging - Services Tier");
        if (this.aspectLogger.isDebugEnabled()){
            String msg = "\n***********************************\nServices Logging\n"
                + createMessage(sig, thisJoinPoint)
                + "\n***********************************";
            this.aspectLogger.debug(msg);
        }
    }
    
    after() returning (Object obj) : topLevelServiceOperation(){
        if (this.aspectLogger.isDebugEnabled()){
            Signature sig = thisJoinPointStaticPart.getSignature();
            String msg = "\n***********************************\nServices Logging"
                + "\nService Class : " + sig.getDeclaringType().getName()
                + "\nService Method: " + sig.getName()
                + "\nReturn type: " + (obj == null ? "<not calculated>" : obj.getClass().getName())
                + "\nReturn Value: " + returnValue(obj, new char[0])
                + "\n***********************************";
            this.aspectLogger.debug(msg);
        }
    }
    
    after() throwing (Exception e): topLevelServiceOperation() {
        if (e instanceof EtccSecurityException || e instanceof ValidationException){
            return;
        }
        Signature sig = thisJoinPointStaticPart.getSignature();
        this.aspectLogger.trace("Aspect Exception Logging - Services Tier");
        String msg = e.getClass().getName() + ':' + e.getMessage()
        + '\n' + createMessage(sig, thisJoinPoint);
        this.aspectLogger.error(msg, e);
    }
    
    /**
     * If the value is an array, returns a CharSequence representation of the Object with its contents.
     * Otherwise, just returns the object.
     * @param retValue The value to be checked as an array.
     * @return The object, unless an array, in which case, a {@link CharSequence} representation of the object.
     */
    private Object returnValue(final Object retValue, final char[] indent){
        if (retValue == null){
            return null;
        }//else
        final Class<? extends Object> clazz = retValue.getClass();
        if (clazz.isArray()){
            if (!clazz.getComponentType().isPrimitive()){
                StringBuilder sb = new StringBuilder(1024);
                sb.append(clazz.getName());
                sb.append('[');
                sb.append('\n');
                for (Object obj : (Object[])retValue) {
                    sb.append(indent);
                    if (obj != null && obj.getClass().isArray()){
                        final char[] newIndent = new char[indent.length + 4];
                        for (int i = 0, size = newIndent.length; i < size; i++) {
                            newIndent[i] = ' ';
                        }
                        sb.append(returnValue(obj, newIndent));
                    } else {
                        sb.append(obj);
                    }
                    sb.append('\n');
                }
                sb.deleteCharAt(sb.length() - 1); //remove last CR.
                sb.append(']');
                return sb;
            } //else (copied from Arrays.deepToString)
            if (clazz == byte[].class)
                return Arrays.toString((byte[]) retValue);
            else if (clazz == short[].class)
                return Arrays.toString((short[]) retValue);
            else if (clazz == int[].class)
                return Arrays.toString((int[]) retValue);
            else if (clazz == long[].class)
                return Arrays.toString((long[]) retValue);
            else if (clazz == char[].class)
                return Arrays.toString((char[]) retValue);
            else if (clazz == float[].class)
                return Arrays.toString((float[]) retValue);
            else if (clazz == double[].class)
                return Arrays.toString((double[]) retValue);
            else if (clazz == boolean[].class)
                return Arrays.toString((boolean[]) retValue);
        } //else
        return retValue;
    }
    
    private String createMessage(Signature sig, JoinPoint joinPoint){
        StringBuilder nullSb = null;
        return "Service Class : " + sig.getDeclaringType().getName()
        + "\nService Method: " + sig.getName()
        + "\nParameters: " + (joinPoint.getArgs() == null ? nullSb : createParameterMessage(sig, joinPoint));
    }
    
    private StringBuilder createParameterMessage(Signature sig, JoinPoint joinPoint){
        final Class<?>[] nullParams = new Class<?>[]{};
        final Object[] nullArgs = new Object[]{};

        final Object[] args = joinPoint.getArgs();
        String[] displayValues = getSensitiveArgDisplayValues(sig, args);
        
        int length = args.length;
        StringBuilder sb = new StringBuilder(length * 128);
        sb.append('\n');
        for (int i = 0; i < length; i++) {
            Object arg = args[i];
            if (arg == null){
                sb.append("null\n");
                continue;
            }
            Class<?> clazz = arg.getClass();
            sb.append(clazz.getSimpleName());
            sb.append(':');
            //Some DTOs have a StringBuilder method on them.
            try {
                Method msb = clazz.getMethod("toStringBuilder", nullParams);
                StringBuilder value = (StringBuilder)msb.invoke(arg, nullArgs);
                sb.append(value);
            } catch (SecurityException e) {
                //Why is the StringBuilder method not public???
                this.aspectLogger.warn(arg.getClass().getName() + ".toStringBuilder is not a public method.");
                sb.append(arg);
            } catch (NoSuchMethodException e) {
                //Some DTOs don't have a StringBuilder method on them.
                if (displayValues[i] != null){
                    sb.append(displayValues[i]);
                } else {
                    sb.append(arg);
                }
            } catch (IllegalArgumentException e) {
                this.aspectLogger.warn(arg.getClass().getName() + ".toStringBuilder requires non-void type parameters.");
                sb.append(arg);
            } catch (IllegalAccessException e) {
                this.aspectLogger.warn(arg.getClass().getName() + ".toStringBuilder is not a public method.");
                sb.append(arg);
            } catch (InvocationTargetException e) {
                //.toStringbuilder failed during execution....
                this.aspectLogger.warn(e.getMessage(), e);
                sb.append(arg);
            }
            sb.append('\n');
        }
        sb.deleteCharAt(sb.length() -1);
        return sb;
    }
    
    private String[] getSensitiveArgDisplayValues(Signature sig, final Object[] args){
        String methodName = sig.getName().intern();
        Class<?> clazz = sig.getDeclaringType();
        final int size = args.length;
        Class<?>[] parameterTypes = ReflectionUtils.getParameterTypes(args);
        String[] displayValues = new String[size];
        try {
            //Can't use getDeclaredMethod, as it fails if one of the parameter types is null.
            //Method m = clazz.getDeclaredMethod(sig.getName(), parameterTypes);
            Method m = ReflectionUtils.findMethod(clazz, methodName, parameterTypes);
            Annotation[][] annotations = m.getParameterAnnotations();
            if (annotations == null || annotations.length == 0){
                return displayValues;
            }//else
            for (int i = 0; i < annotations.length; i++) {
                Annotation[] paramAnnotations = annotations[i];
                if(paramAnnotations != null && paramAnnotations.length > 0){
                    //Parameter has annotations
                    for (Annotation annotation : paramAnnotations) {
                        if (annotation.annotationType() == Sensitive.class){
                            //Sensitive param
                            displayValues[i] = ((Sensitive)annotation).displayValue();
                            break;
                        }
                    }
                }
            }
            return displayValues;
        } catch (SecurityException e) {
            throw new RuntimeException("Security Exception accessing logged method " + 
                    sig.getDeclaringType().getName() + '.' + 
                    sig.getName() + ": " + e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("NoSuchMethodException accessing logged method " + 
                    sig.getDeclaringType().getName() + '.' + 
                    sig.getName() + ": " + e.getMessage(), e);
        }
    }
    
}

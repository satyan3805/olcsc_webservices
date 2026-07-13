/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.util;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Utility methods for Reflection operation.
 * @author (task 488) Stephen Davidson
 *
 */
public class ReflectionUtils {
    
    /** 
     * Find a "Best Match" Method on a Class with a "partial" set of parameters (rather than the "Exact Match" method
     * that {@link Class#getDeclaredMethod(String, Class...)} uses.
     * @param clazz The class to find the method on.
     * @param methodName The name of the Method to retrieve.
     * @param parameterTypes List of parameter types for the Method.  If any type is null, it is ignored for matching
     * purposes.
     * @return the first method found that takes the parameters listed in by <code>parameterTypes</code>
     * @throws NoSuchMethodException If the method, or method without a compatible signature, can not be found.
     */
    public static Method findMethod(final Class<?> clazz, final String methodName, final Class<?>[] parameterTypes) throws NoSuchMethodException{
        Method[] methods = clazz.getDeclaredMethods();
        final int length = parameterTypes.length;
        for (Method method : methods) {
            if (method.getName() == methodName){
                //Check signature
                Class<?>[] methodParams = method.getParameterTypes();
                if (methodParams.length == length){
                    for (int i = 0; i < length; i++) {
                        if (!parameterTypes[i].isAssignableFrom(methodParams[i])){
                            //Wrong signature
                            break;
                        }
                    }
                    //Right signature
                    return method;
                }//else, wrong parameter count, so wrong method
            }//wrong method
        }//end for
        throw new NoSuchMethodException("Unable to find method " + clazz.getName() + '.' + methodName + '(' +
                Arrays.toString(parameterTypes) + ')');
    }

    /**
     * Gets the types of argument list.
     * @param args the objects to get the classes of.
     * @return The classes of the arguments.  If an argument is null, there will be a null value at the corresponding
     * offset.
     */
    public static Class<?>[] getParameterTypes(Object[] args){
        int size = args.length;
        Class<?>[] parameterTypes = new Class<?>[size];
        for (int i = 0; i < size; i++) {
            parameterTypes[i] = args[i] == null ? Object.class : args[i].getClass();
        }
        return parameterTypes;
    }

}

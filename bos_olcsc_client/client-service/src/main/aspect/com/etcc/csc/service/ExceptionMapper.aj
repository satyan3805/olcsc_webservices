/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.Signature;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.ReturnParam;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.util.ReflectionUtils;
import com.etcc.csc.validation.ValidationException;


/**
 * Originally created to catch ValidationExceptions from the Service Tier, and remap to Business DTOs for the UI.
 * @todo The Business Tier needs to be reworked to handle Validation Exceptions.
 * @author (task 488) Stephen Davidson
 */
public aspect ExceptionMapper {
    declare precedence: ExceptionMapper, PreserveEtccException, ConnectionController, *;

    pointcut serviceOperation():
        execution(public * com.etcc.csc.service.*.*(..))
        && target(com.etcc.csc.service.ServiceInterface+)
        //HACK: The above line is acting like an 'OR', not an 'AND'
        && !target(com.etcc.csc.dao.BaseDAO+)
        //Hack: Does not implement ServiceInterface, so these should not be getting picked up.
        && !target(com.etcc.csc.service.ServiceFactory+)
        // Menu service never throws an exception
        && !target(com.etcc.csc.service.MenuInterface+)
        && !execution(* com.etcc.csc.service.*Test.*(..))
        ;


    Object around() throws EtccException, EtccSecurityException: serviceOperation() {
        //Get if any parameters are used as return values.
        Signature sig = thisJoinPointStaticPart.getSignature();
        final Object[] args = thisJoinPoint.getArgs();
        Object arg = getReturnParam(sig, args);

        try {
            return proceed();
        } catch (ValidationException ve){
            Logger logger = Logger.getLogger(getClass());
            if (arg != null){
                if (arg instanceof BaseDTO) {
                    logger.trace("Using parameter for return value.");
                    BaseDTO messageDto = (BaseDTO)arg;
                    //Populate with messages from the Exception
                    messageDto.addErrors(ve.getErrorMessages());
                    return messageDto;
                } //else
                logger.warn("Return Parameter not instance of BaseDTO: " + arg.getClass());
            }//else
            //Get Return type
            Class<? extends BaseDTO> dtoClass = getReturnType(sig, thisJoinPoint.getArgs());
            // Cannot instantiate a primitive, so just rethrow the exception
            if (dtoClass.isPrimitive())
                throw ve;
            try {
                //Instantiate return type
                BaseDTO messageDto = dtoClass.newInstance();
                if (logger.isDebugEnabled()){
                    logger.debug("Handling ValidationException: Have built " + dtoClass.getName());
                }
                //Populate with messages from the Exception
                messageDto.addErrors(ve.getErrorMessages());
                if (logger.isDebugEnabled()){
                    logger.debug("Validation Error Messages: " + ErrorMessageDTO.toStringBuilder(messageDto.getErrors()));
                }
                //Return.
                return messageDto;
            } catch (IllegalAccessException e) {
                Logger classLogger = Logger.getLogger(sig.getDeclaringType());
                classLogger.error("Unable to access instance of " + dtoClass.getName() +": " + e.getMessage(), e);
                throw ve;
            } catch (InstantiationException e) {
                Logger classLogger = Logger.getLogger(sig.getDeclaringType());
                classLogger.error("Unable to instantiate instance of " + dtoClass.getName() +": " + e.getMessage(), e);
                throw ve;
            }
        }
    }

    private Class<? extends BaseDTO> getReturnType(Signature sig, Object[] args){
        try {
            Method m = ReflectionUtils.findMethod(sig.getDeclaringType(), sig.getName(), ReflectionUtils.getParameterTypes(args));
            @SuppressWarnings("unchecked")
            Class<? extends BaseDTO> returnType = (Class<? extends BaseDTO>)m.getReturnType();
            return returnType;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("NoSuchMethodException accessing invoked method " +
                    sig.getDeclaringType().getName() + '.' +
                    sig.getName() + ": " + e.getMessage(), e);
        }
    }
    
    private Object getReturnParam(Signature sig, final Object[] args){
        String methodName = sig.getName().intern();
        Class<?> clazz = sig.getDeclaringType();
        final int size = args.length;
        Class<?>[] parameterTypes = ReflectionUtils.getParameterTypes(args);
        try {
            //Can't use getDeclaredMethod, as it fails if one of the parameter types is null.
            //Method m = clazz.getDeclaredMethod(sig.getName(), parameterTypes);
            Method m = ReflectionUtils.findMethod(clazz, methodName, parameterTypes);
            Annotation[][] annotations = m.getParameterAnnotations();
            if (annotations == null || annotations.length == 0){
                return null;
            }//else
            for (int i = 0; i < annotations.length; i++) {
                Annotation[] paramAnnotations = annotations[i];
                if(paramAnnotations != null && paramAnnotations.length > 0){
                    //Parameter has annotations
                    for (Annotation annotation : paramAnnotations) {
                        if (annotation.annotationType() == ReturnParam.class){
                            //Return parameter
                            return args[i];
                        }
                    }
                }
            }
            return null;
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

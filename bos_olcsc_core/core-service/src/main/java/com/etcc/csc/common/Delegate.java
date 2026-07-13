package com.etcc.csc.common;

public class Delegate implements DelegateInterface {

    protected Logger logger = null;
    public Delegate(Class clazz) {
        logger = Logger.getLogger(clazz);
    }
    
    public BusinessObjectInterface  getServiceObject(ServiceObjectEnum so){
    	return ServiceObjectFactory.create(so);
    }
}// end of Delegate Interface

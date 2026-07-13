package com.etcc.csc.common;

/**
 * Base class that creates objects anonymously
 */
public class EtcFactory {
	public EtcFactory() {
	}

	private static Logger logger = Logger.getLogger(EtcFactory.class);

	protected static Object getObject(StringEnum x) {

		Object o = null;
		try {
			Class clazz = Class.forName(x.getValue());
			o = clazz.newInstance();

		} catch (InstantiationException e) {
			logger.error("InstantiationException in EtcFactory.getObject for "
					+ x, e);
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException in EtcFactory.getObject for "
					+ x, e);
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in EtcFactory.getObject for "
					+ x, e);
		}
		return o;
	}
}

/**
 * Miscellaneous string utilities
 */
package com.etcc.csc.common;

public class StringUtil {
	private StringUtil() {
	}

	private static final String EMPTY_STRING = "";
	public static final int NOT_FOUND_INDEX = -1;

	public static boolean isBlankOrNull(String string) {
		return ((string == null) || (EMPTY_STRING.equals(string)) || string
				.length() == 0);
	}

	public static boolean isNotBlankOrNull(String string) {
		return !isBlankOrNull(string);
	}
}

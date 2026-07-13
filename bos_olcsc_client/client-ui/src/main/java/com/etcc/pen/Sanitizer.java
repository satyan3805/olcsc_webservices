package com.etcc.pen;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.errors.EncodingException;

/**
 *
 * Added by nkiboti
 */
public class Sanitizer {

    static Codec ORACLE_CODEC = new OracleCodec();
    static Encoder encoder = ESAPI.encoder();

    public static String santize(String val) {
        return encoder.encodeForSQL(ORACLE_CODEC, val);
    }
    
    public static String santizeForURL(String val) {
        try {
			return encoder.encodeForURL(val);
		} catch (EncodingException e) {
			System.out.println("Unsafe data found in Parameter.  Value : " + val);
			return "";
		}
    }
    
    
    public static String santizeForHTMLAttribute(String val) {
		return encoder.encodeForHTMLAttribute(val);		 
    }


    public static void main(String[] args) {
        try {
            String str = "<span class=red>hh</span>";
            str = "HUOAO123";
            System.out.println(">>" + encoder.encodeForSQL(ORACLE_CODEC, "update accts set name='jack' "));
            System.out.println(">>" + encoder.encodeForSQL(ORACLE_CODEC, "/personalInfoDisplay.do"));
            System.out.println(">>" + encoder.encodeForCSS(str));
            System.out.println(">>" + encoder.encodeForHTML(str));
            System.out.println(">>" + encoder.encodeForJavaScript(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

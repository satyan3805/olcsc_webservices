package com.etcc.pen;

import com.etcc.csc.common.Logger;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

public class KeywordFilter {

    private static Logger log = Logger.getLogger(KeywordFilter.class);
    static final String[] whiteList = new String[]{""};
    static final String[] blackList2 = new String[]{"onclick", "ondblclick",
        "onmousedown", "onmouseup", "onmouseover", "onmousemove",
        "onmouseout", "onkeydown", "onkeypress", "onkeyup", "onload",
        "onunload", "onabort", "onerror", "onresize", "onscroll",
        "onselect", "onchange", "onsubmit", "onreset", "onfocus", "onblur",
        "ondomfocusin", "ondomfocusout", "ondomactivate",
        "onsubtreemodified", "onnodeinserted", "onnoderemoved",
        "ondomnoderemovedfromdocument", "ondomnodeinsertedintodocument",
        "onattrmodified", "oncharacterdatamodified", "oncut", "oncopy",
        "onpaste", "onbeforecut", "onbeforecopy", "onbeforepaste",
        "onafterupdate", "onbeforeupdate", "oncellchange",
        "ondataavailable", "ondatasetchanged", "ondatasetcomplete",
        "onerrorupdate", "onrowenter", "onrowexit", "onrowsdelete",
        "onrowinserted", "oncontextmenu", "ondrag", "ondragstart",
        "ondragenter", "ondragover", "ondragleave", "ondragend", "ondrop",
        "onselectstart", "onhelp", "onbeforeunload", "onstop",
        "onbeforeeditfocus", "onstart", "onfinish", "onbounce",
        "onbeforeprint", "onafterprint", "onpropertychange",
        "onfilterchang", "onreadystatechange", "onlosecapture",
        "dommousescroll", "ondragdrop", "ondragenter", "ondragexit",
        "ondraggesture", "ondragover", "onclose", "oncommand", "oninput",
        "dommenuitemactive", "dommenuItemInactive", "oncontextmenu",
        "onoverflow", "onoverflowchanged", "onunderflow", "onpopuphidden",
        "onpopuphiding", "onpopupshowing", "onpopupshown", "onbroadcast",
        "oncommandupdate", "javascript", "<", ">", "++", "--", "==", "&#"};
    static final String[] blackListRegExp = new String[]{"style\\s*=",
        "class\\s*=", "document\\.", "size\\s*=", "width\\s*=",
        "height\\s*=", "expression\\s*\\(", "eval\\s*\\(", "alert\\s*\\(",
        "rel\\s*=", "src\\s*=", "href\\s*="};
    static final String[] sqlRegExp1 = new String[]{"update", "set", "="};
    static final String[] sqlRegExp2 = new String[]{"insert", "into", "values"};
    static final String[] sqlRegExp3 = new String[]{"select", "from"};
    static final String[] sqlRegExp4 = new String[]{"delete", "from"};
    static final String[] sqlRegExp5 = new String[]{"alter", "table"};
    static final String[] sqlRegExp6 = new String[]{"drop", "table"};
    static final Codec ORACLE_CODEC = new OracleCodec();
    private static Policy policy = retrievePolicyForInputValidation();

    private static boolean validateSamy(String inputStr) {
        int i = 1;
        try {
            AntiSamy as = new AntiSamy();
            CleanResults cr = as.scan(inputStr, policy);

            // as.scan("<SPAN class=olcscBtn>Add</SPAN>", policy);
            i = cr.getNumberOfErrors();
        } catch (Exception e) {
            log.error("samy exception:", e);
        }
        if (i >= 1) {
            log.error("Failed because of policy " + inputStr);
            return false;
        } else {
            return true;
        }
    }

    private static boolean allowWhiteListElements(String inputString) {
        try {
            for (int i = 0; i < whiteList.length; i++) {
                //log.error("whiteList[i]" + whiteList[i]);
                inputString = inputString.trim();
                if (inputString.trim().equalsIgnoreCase(whiteList[i])) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
    }

    private static boolean lookLikeSQL(String inputString) {
        if (hasAllTokens(inputString, sqlRegExp1)) {
            return true;
        }
        if (hasAllTokens(inputString, sqlRegExp2)) {
            return true;
        }
        if (hasAllTokens(inputString, sqlRegExp3)) {
            return true;
        }
        if (hasAllTokens(inputString, sqlRegExp4)) {
            return true;
        }
        if (hasAllTokens(inputString, sqlRegExp5)) {
            return true;
        }
        if (hasAllTokens(inputString, sqlRegExp6)) {
            return true;
        }
        return false;
    }

    private static boolean hasAllTokens(String str, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (str.indexOf(arr[i]) < 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateBlackListedElements(String inputStr) {
        boolean isInputValid = true;
        for (int i = 0; i < blackList2.length; i++) {
            if (inputStr.indexOf(blackList2[i]) >= 0) {
                log.error("Black list element >>" + blackList2[i]);
                isInputValid = false;
                break;
            }
        }
        for (int i = 0; i < blackListRegExp.length; i++) {

            Pattern p = Pattern.compile(blackListRegExp[i]);
            Matcher m = p.matcher(inputStr);
            boolean isInvalidInputFound = m.find();
            if (isInvalidInputFound) {
                log.error("Black list blackListRegExp >>" + blackListRegExp[i]);
                isInputValid = false;
                break;
            }
        }
        return isInputValid;
    }

    public static boolean validateInputParameters(HttpServletRequest req) {

        Map requestParameterMaps = req.getParameterMap();
        if (requestParameterMaps != null) {
            Iterator itr = requestParameterMaps.entrySet().iterator();
            if (itr != null) {
                while (itr.hasNext()) {
                    Map.Entry inputEntry = (Map.Entry) itr.next();
                    String[] inputVals = (String[]) inputEntry.getValue();
                    if (inputVals != null && inputVals.length > 0) {
                        for (int i = 0; i < inputVals.length; i++) {
                            boolean valid = validateInputStr(inputVals[i]);
                            if (!valid) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    static boolean validateInputStr(String inputString) {
        String invalidReason = "";
        if (StringUtils.isEmpty(inputString)) {
            return true;
        }
        inputString = inputString.toLowerCase();
        // inputString = allowWhiteListElements(inputString);
        boolean isInputValid = validateSamy(inputString);
        invalidReason = "caught samy";
        if (isInputValid) {
            isInputValid = validateBlackListedElements(inputString);
            invalidReason = "caught bad keyword ";
            if (isInputValid) {
                boolean lookSql = lookLikeSQL(inputString);
                if (lookSql) {
                    isInputValid = false;
                }
                invalidReason = "caught SQL ";
            }
        }
        if (!isInputValid) {
            boolean whiteListed = allowWhiteListElements(inputString);
            if (whiteListed) {
                isInputValid = true;
            }
        }
        if (!isInputValid) {
//            System.out.println("caught bad input >> Field: " + invalidReason + ">>"
//                    + "<>" + inputString);
            return false;
        }
        return true;
    }

    static Policy retrievePolicyForInputValidation() {
    	InputStream in = null;
        try {
            if (policy != null) {
                return policy;
            }
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            in = loader.getResourceAsStream("antisamy-ebay-1.4.4.xml");
            policy = Policy.getInstance(in);
        } catch (Exception e) {
            log.error("Exception " + e.toString());
        }
        finally{
        	try
        	{	
        		if (in != null) in.close();        	
        	}catch (Exception ex) {}
        }
        return policy;
    }

    public static void main(String[] args) {
        String str = "<SPAN class=olcscBtn>Add</SPAN>";
        System.out.println(KeywordFilter.validateSamy(str));
        System.out.println(validateBlackListedElements(str));
        System.out.println(allowWhiteListElements(str));
        System.out.println(lookLikeSQL(str));

        System.out.println("validateInputStr::" + validateInputStr(str));
    }
}

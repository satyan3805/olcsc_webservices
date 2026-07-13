package com.etcc.csc.presentation.taglib;

import org.apache.struts.taglib.html.FormTag;

public class AutocompleteOffFormTag extends FormTag
{
    public AutocompleteOffFormTag() {
    }
    
    protected void renderOtherAttributes(StringBuffer results) 
    {
      renderAttribute( results, "autocomplete", "off" );
    }

}

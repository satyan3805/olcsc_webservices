package com.etcc.csc.decorator;

import org.displaytag.decorator.TableDecorator;

/**
 * Displays rows in the same group in the same color.
 * @author nternida
 */
public class GroupRowColorTableDecorator extends TableDecorator {
    private boolean oddRow = true;
    private short groupRow = 0;
    
    public GroupRowColorTableDecorator() {
    }
    
    public void startOfGroup(String value, int group) {
        groupRow++;
        if (groupRow % 2 == 0) {
            oddRow = false;
        } else {
            oddRow = true;
        }
    }
    
    public String addRowClass() {
        if (oddRow) {
            return "grouped-row-odd";
        } else {
            return "grouped-row-even";
        }
    }

/*    public String addRowId() {
        if (oddRow) {
            return "grouped-row-odd";
        } else {
            return "grouped-row-even";
        }
    }
*/    
}

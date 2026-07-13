/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Represents a Menu Item and its children.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class MenuItemDTO extends BaseDTO implements Serializable, Comparable<MenuItemDTO> {
    /**
     * @author Milosh Boroyevich
     */
    public interface MenuItemBuilder {
        /**
         * Converts the specified record to a menu item.
         * @param record the object to convert
         * @return the menu item
         */
        public MenuItemDTO createObject(Object record);
    }

    public enum MenuCategory {
    	MENU,
    	OLCSC_LOG,
    	OLCSC_PAGE,
    	OLCSC_TTRY,
    	OLCSC_UNLG
		;
    }

    /**
     * Unique ID for serialization
     */
    //Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = -1666648857568112636L;

    private static final Logger logger = Logger.getLogger(MenuItemDTO.class);

    private int itemId;
    /** Menu item type - AKA category. */
    private MenuCategory itemType;
    private String itemLabel;
    private String itemUrl;
    private String itemImage;
    private String itemHelpText;
    private short orderWithinParent;
    private boolean shiftReq;
    private short menuLevel;
    /** Sorted list of child menu items. */
    private List<MenuItemDTO> children;
    /**
     * ID of this menu item's parent.
     * Note: since the menu hierarchy and category are co-mingled, the
     * parent ID is present on each menu item.
     */
    private int parentId;

    /**
     * Filter the specified menu items and return only those with the specified parent id.
     * The specified menu items are assumed hierarchical (i.e. if the parent
     * is present in the array, it is assumed all of the children can be
     * retrieved with a call to <tt>getMenuItems</tt> invoked on the parent).
     * Note: passing a value <= 0 for the parent item id will return the unmodified menus parameter.
     * @param menus menu items to filter
     * @param parentItemId id corresponding to the parent
     * @return the subset of menus
     * @author Milosh Boroyevich
     * @see #getParentId()
     * @see #findChildMenuItems(MenuItemDTO[], int)
     * @see #getMenuItems()
     */
    public static MenuItemDTO[] filterMenus(final MenuItemDTO[] menus, final int parentItemId) {
        if (menus != null && parentItemId > 0) {
        	ArrayList<MenuItemDTO> filteredMenus = new ArrayList<MenuItemDTO>();
        	for (MenuItemDTO menuItemDTO : menus) {
        		if (menuItemDTO.itemId == parentItemId) {
        			// this is the specified parent, so return the associated children
        			return menuItemDTO.getMenuItems();
        		} else if (menuItemDTO.parentId == parentItemId) {
        			// found a child with the specified parent id
        			filteredMenus.add(menuItemDTO);
        		} else if (menuItemDTO.getMenuItemsSize() > 0 && filteredMenus.isEmpty()) {
            		// scan the children for the requested parent id
            		MenuItemDTO[] children = findChildMenuItems(menuItemDTO.getMenuItems(), parentItemId);
            		if (children != null) {
            			// found children with the parent id
            			return children;
            		}
        		}
        	}
        	return filteredMenus.toArray(new MenuItemDTO[filteredMenus.size()]);
        }
        return menus;
    }

    /**
     * Recursive method implementing a depth-first-search looking for the
     * children corresponding to the parent of the specified id.
     * Note: a parent item id with value <= 0 will return the unmodified menus parameter.
     * <p><b>WARNING: This method assumes the menu items are hierarchically
     * structured within the specified menus.</b>
     * For the non-hierarchical implementation, see {@link #filterMenus(MenuItemDTO[],int)}.</p>
     * @param menus tree of menus to search
     * @param parentItemId id corresponding to the parent
     * @return the array of children if parent is found
     * @author Stephen Davidson
     * @author Milosh Boroyevich
     * @see #getMenuItems()
     * @see #filterMenus(MenuItemDTO[], int)
     */
    public static MenuItemDTO[] findChildMenuItems(final MenuItemDTO[] menus, final int parentItemId) {
        if (menus != null && parentItemId > 0) {
        	for (MenuItemDTO menuItemDTO : menus) {
        		if (menuItemDTO.itemId == parentItemId) {
        			// found the parent
        			return menuItemDTO.getMenuItems();
        		}// else
        		// scan the children for the requested parent id.
        		MenuItemDTO[] children = findChildMenuItems(menuItemDTO.getMenuItems(), parentItemId);
        		if (children != null) {
        			//found children with the parent id
        			return children;
        		}
        	}
        } else {
        	return menus;
        }
        // Did not find a matching menu id -- return null.
        return null;
    }

    /**
     * Build the menus tree from the specified array using the specified menu item builder.
     * <p>Note: this method performs a three-pass processing of the input array
     * (the first pass puts the items in corresponding <i>level</i> buckets,
     * the second pass builds the menus by <i>level</i> thus ensuring
     * potential parents are processed before the children, and the third
     * pass sorts all the menus.</p>
     * Assumptions:
     * <ul>
     *   <li><tt>level == 1</tt> is a top-level item</li>
     *   <li><tt>level &gt; 1</tt> must have a parent in the specified array located at <tt>level-1</tt></li>
     *   <li><tt>level &lt; 1</tt> is illegal and will throw an exception</li>
     * </ul>
     * Note: if the minimum level is greater than 1, it will be used as the "top level".
     * @param array of objects that the builder can process
     * @param builder object which can return a menu item for each element of the specified array
     * @return the menus tree
     * @throws NullPointerException if any parameters are <tt>null</tt>,
     *         or if there are no top-level menu items,
     *         or if a child doesn't have a parent
     * @throws IllegalArgumentException if the items in the specified array do not meet the assumptions
     * @see MenuItemBuilder#createObject(Object)
     * @see #getMenuLevel()
     * @see #getParentId()
     * @author Milosh Boroyevich
     */
    public static MenuItemDTO[] buildMenus(Object[] array, MenuItemBuilder builder) throws NullPointerException, IllegalArgumentException {
    	if (array == null || array.length <= 0)
    		return null;
    	short minLevel = Short.MAX_VALUE;
    	short maxLevel = 0;
    	// all menu items grouped into level buckets
    	HashMap<Short,ArrayList<MenuItemDTO>> levelBuckets = new HashMap<Short,ArrayList<MenuItemDTO>>();
    	// all menu items by item id
    	HashMap<Integer,MenuItemDTO> menuMap = new HashMap<Integer,MenuItemDTO>();
    	// do the pre-processing
    	for (Object a : array) {
            MenuItemDTO menu = builder.createObject(a);
            short level = menu.getMenuLevel();
            if (level > maxLevel) maxLevel = level;
            if (level < minLevel) minLevel = level;
            int id = menu.getItemId();
            ArrayList<MenuItemDTO> bucket = levelBuckets.get(Short.valueOf(level));
            if (bucket == null) {
            	bucket = new ArrayList<MenuItemDTO>();
            	levelBuckets.put(Short.valueOf(level), bucket);
            }
            bucket.add(menu);
            MenuItemDTO duplicate = menuMap.put(Integer.valueOf(id), menu);
            assert duplicate == null : "Duplicate menu item encountered.";
    	}
    	// build the menus
    	// parents first
        ArrayList<MenuItemDTO> bucket = levelBuckets.get(Short.valueOf(minLevel++));
        int size = bucket.size();
        MenuItemDTO[] result = new MenuItemDTO[size];
        for (int i = 0; i < size; i++) {
        	result[i] = bucket.get(i);
        }
        // now the children
    	for (short level = minLevel; level <= maxLevel; level++) {
            bucket = levelBuckets.get(Short.valueOf(level));
            for (MenuItemDTO child : bucket) {
            	MenuItemDTO parent = menuMap.get(Integer.valueOf(child.parentId));
            	// throws NPE if parent doesn't exist
            	if (child.menuLevel != parent.menuLevel+1)
            		throw new IllegalArgumentException("Inconsistent menu levels for child (" + child.menuLevel + ") and parent (" + parent.menuLevel + ").");
            	parent.addChild(child);
            }
    	}
    	// ensure all the menu children are sorted
    	for (MenuItemDTO menu : menuMap.values())
    		if (menu.children != null && !menu.children.isEmpty())
    			Collections.sort(menu.children);
		Arrays.sort(result);  // throws NPE if no top-level menu items
    	return result;
    }

    /**
     * Build the menus tree from the specified array using the specified menu item builder.
     * <b>Note: This method assumes the array is in depth-first order with the level
     * implying parent-child relationships.</b>
     * @param array of objects that the builder can process
     * @param builder object which can return a menu item for each element of the specified array
     * @return the menus tree
     * @see MenuItemBuilder#createObject(Object)
     * @see #getMenuLevel()
     * @author Unknown
     * @author Stephen Davidson
     * @author Milosh Boroyevich
     */
    public static MenuItemDTO[] buildMenusByLevel(Object[] array, MenuItemBuilder builder) {
        ArrayList<MenuItemDTO> result = new ArrayList<MenuItemDTO>();
        List<MenuItemDTO> parents = new ArrayList<MenuItemDTO>();
        List<Short> levels = new ArrayList<Short>();
        //            short prevLevel = 1;
        //            MenuItemDTO parentRecord = null;
        //            boolean newParent = true;
        if (array != null && array.length >= 0) {
            result.ensureCapacity(array.length);
            for (int i = 0; i < array.length; i++) {
                MenuItemDTO menu = builder.createObject(array[i]);
                if (menu == null) {
                	logger.warn("Invalid menu item (builder returned null): " + array[i]);
                	continue;
                }
                short level = menu.getMenuLevel();
                boolean iterate = false;
                do {
                    iterate = false;
                    if (parents.size() > 0) {
                        short currLevel = (levels.get(levels.size() - 1)).shortValue();
                        if (level == currLevel) {
                            // replace parent
                            parents.remove(parents.size() - 1);
                            parents.add(menu);
                            // if size > 1, add this as a child of the existing parent
                            if (parents.size() > 1) {
                                MenuItemDTO currMenu = parents.get(parents.size() - 2);
                                currMenu.addChild(menu);
                            } else {
                                result.add(menu);
                            }
                        } else {
                            if (level > currLevel) {
                                // add new parent
                                parents.add(menu);
                                levels.add(Short.valueOf(level));
                                // add curr menu to parent
                                MenuItemDTO currMenu = parents.get(parents.size() - 2);
                                currMenu.addChild(menu);
                            } else {
                                // less than; remove current parent
                                parents.remove(parents.size() - 1);
                                levels.remove(levels.size() - 1);
                                // save parent (?)
                                iterate = true;
                            }
                        }
                    } else {
                        result.add(menu);
                        parents.add(menu);
                        levels.add(Short.valueOf(level));
                    }
                } while (iterate);
            }
        }
        Collections.sort(result);
        return result.toArray(new MenuItemDTO[result.size()]);
    }

    /**
     * Menu items are ordered by parent ordering then by item id.
     * The value is computed as the difference of the corresponding hash codes.
	 * @throws NullPointerException if the specified object is <tt>null</tt>.
     * @see #orderWithinParent
     * @see #itemId
     * @see #hashCode()
     */
    public int compareTo(MenuItemDTO o) {
        // Shift orderWithinParent so that itemId is only a secondary sort parameter
        return this.hashCode() - o.hashCode();
    }

    /**
     * Returns a hash code for this menu item.
     * The hash code for a menu item is computed as:
     * <code>(orderWithinParent << 16) + (itemId % Short.MAX_VALUE)</code>
     * @see #orderWithinParent
     * @see #itemId
     */
    @Override
    public int hashCode() {
        return (this.orderWithinParent << 16) + (this.itemId % Short.MAX_VALUE);
    }

    /**
     * Two menu items are considered equal if they have the same item id and order within parent.
     * @see #itemId
     * @see #orderWithinParent
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        try {
            MenuItemDTO other = (MenuItemDTO) obj;
            return this.itemId == other.itemId
                && this.orderWithinParent == other.orderWithinParent;
        } catch (ClassCastException e) { }
        // Not a MenuItemDTO
        return false;
    }

    /**
     * Adds a Menu Item to this menu.
     * @param child the child menu item to add.
     */
    public void addChild(MenuItemDTO child) {
        if (this.children == null) {
            this.children = new ArrayList<MenuItemDTO>();
        }
        this.children.add(child);
        Collections.sort(this.children);
    }

    /**
     * Gets the number of child items in this menu.
     * @return the number of child items.
     */
    public int getMenuItemsSize() {
        if (this.children != null) {
            return this.children.size();
        } //else
        return 0;
    }

    /**
     * Returns the child menu items of this menu item.
     * @return the child menu items as an array, or <tt>null</tt> if none are present
     */
    public MenuItemDTO[] getMenuItems() {
        return (children == null ? null : children.toArray(new MenuItemDTO[children.size()]));
    }

    /**
     * @param menuItems the array of menuItems to set
     */
    public void setMenuItems(MenuItemDTO[] menuItems) {
        if (menuItems == null) {
            this.children = null;
        } else {
            Arrays.sort(menuItems);
            this.children = Arrays.asList(menuItems);
        }
    }

    /**
     * NOT USED -- Possibly required by some of the JSP tools.
     * @param value <b>IGNORED</b>
     * @deprecated
     */
    @Deprecated
    public void setMenuItemsSize(int value) {
        // stub only
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("itemId=");
        sb.append(this.itemId);
        sb.append(", parentId=");
        sb.append(this.parentId);
        sb.append(", itemLabel=");
        sb.append(this.itemLabel);
        sb.append(", itemHelpText=");
        sb.append(this.itemHelpText);
        sb.append(", itemType=");
        sb.append(this.itemType);
        sb.append(", itemUrl=");
        sb.append(this.itemUrl);
        sb.append(", orderWithinParent=");
        sb.append(this.orderWithinParent);
        sb.append(", shiftReq=");
        sb.append(this.shiftReq);
        sb.append(", menuLevel=");
        sb.append(this.menuLevel);
        sb.append(", children=");
        sb.append(this.children);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the itemId
     */
    public int getItemId() {
        return this.itemId;
        //end getItemId
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
        //end setItemId
    }

    /**
     * @return the itemType
     */
    public MenuCategory getItemType() {
        return this.itemType;
        //end getItemType
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(MenuCategory itemType) {
        this.itemType = itemType;
        //end setItemType
    }

    /**
     * @return the itemLabel
     */
    public String getItemLabel() {
        return this.itemLabel;
        //end getItemLabel
    }

    /**
     * @param itemLabel the itemLabel to set
     */
    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
        //end setItemLabel
    }

    /**
     * @return the itemUrl
     */
    public String getItemUrl() {
        return this.itemUrl;
        //end getItemUrl
    }

    /**
     * @param itemUrl the itemUrl to set
     */
    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
        //end setItemUrl
    }

    /**
     * @return the itemImage
     */
    public String getItemImage() {
        return this.itemImage;
        //end getItemImage
    }

    /**
     * @param itemImage the itemImage to set
     */
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
        //end setItemImage
    }

    /**
     * @return the itemHelpText
     */
    public String getItemHelpText() {
        return this.itemHelpText;
        //end getItemHelpText
    }

    /**
     * @param itemHelpText the itemHelpText to set
     */
    public void setItemHelpText(String itemHelpText) {
        this.itemHelpText = itemHelpText;
        //end setItemHelpText
    }

    /**
     * @return the orderWithinParent
     */
    public short getOrderWithinParent() {
        return this.orderWithinParent;
        //end getOrderWithinParent
    }

    /**
     * @param orderWithinParent the orderWithinParent to set
     */
    public void setOrderWithinParent(short orderWithinParent) {
        this.orderWithinParent = orderWithinParent;
        //end setOrderWithinParent
    }

    /**
     * @return the shiftReq
     */
    public boolean isShiftReq() {
        return this.shiftReq;
        //end isShiftReq
    }

    /**
     * @param shiftReq the shiftReq to set
     */
    public void setShiftReq(boolean shiftReq) {
        this.shiftReq = shiftReq;
        //end setShiftReq
    }

    /**
     * @return the menuLevel
     */
    public short getMenuLevel() {
        return this.menuLevel;
        //end getMenuLevel
    }

    /**
     * @param menuLevel the menuLevel to set
     */
    public void setMenuLevel(short menuLevel) {
        this.menuLevel = menuLevel;
        //end setMenuLevel
    }

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getParentId() {
		return parentId;
	}
}

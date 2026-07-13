package com.etcc.csc.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.etcc.csc.common.BaseDTO;

/**
 * Represents the Menu Item data.
 */
public class MenuItemDTO {
  private int itemId;
  private String itemType;
  private String itemLabel;
  private String itemUrl;
  private String itemImage;
  private String itemHelpText;
  private short orderWithinParent;
  private boolean shiftReq;
  private byte menuLevel;
  private Collection menuItems;
  
  public MenuItemDTO() {
  }

  /**
   * Returns the label for this menuItem.
   * @return 
   */
  public String getItemLabel() {
    return itemLabel;
  }
  
  /**
   * Sets the itemLabel to the specified value.
   * @param value
   */
  public void setItemLabel(String value) {
    itemLabel = value;
  }

  /**
   * Returns the itemHelpText.
   * @return 
   */
  public String getItemHelpText() {
    return itemHelpText;
  }
  
  /**
   * Sets the itemHelpText to the specified value.
   * @param value
   */
  public void setItemHelpText(String value) {
    itemHelpText = value;
  }
  /**
   * Returns the itemType.
   * @return 
   */
  public String getItemType() {
    return itemType;
  }
  /**
   * Sets the itemType to the specified value.
   * @param value
   */
  public void setItemType(String value) {
    itemType = value;
  }
  /**
   * Returns the itemUrl.
   * @return 
   */
  public String getItemUrl() {
    return itemUrl;
  }
  /**
   * Sets the itemUrl to the specified value.
   * @param value
   */
  public void setItemUrl(String value) {
    itemUrl = value;
  }
  /**
   * Returns the itemId.
   * @return 
   */
  public int getItemId() {
    return itemId;
  }
  
  /**
   * Sets the itemId to the specified value.
   * @param value
   */
  public void setItemId(int value) {
    itemId = value;
  }
  

  /**
   * Returns the sub menu items for this menu.
   * @return 
   */
  public Collection getMenuItems() {
    return menuItems;
  }
  
  /**
   * Sets the sub menu items for this menu.
   * @param value
   */
  public void setMenuItems(Collection value) {
    menuItems = value;
  }
  
  public void addChild(MenuItemDTO child) {
    if (menuItems == null) {
      menuItems = new ArrayList();
    }
    menuItems.add(child);
  }
  
  /**
   * Returns the orderWithinParent.
   * @return 
   */
  public short getOrderWithinParent() {
    return orderWithinParent;
  }
  
  /**
   * Sets the orderWithinParent to the specified value.
   * @param value
   */
  public void setOrderWithinParent(short value) {
    orderWithinParent = value;
  }

  /**
   * Returns the shiftReq.
   * @return 
   */
  public boolean isShiftReq() {
    return shiftReq;
  }
  
  /**
   * Sets the shiftReq to the specified value.
   * @param value
   */
  public void setShiftReq(boolean value) {
    shiftReq = value;
  }
  /**
   * Returns the menuLevel.
   * @return 
   */
  public byte getMenuLevel() {
    return menuLevel;
  }
  
  /**
   * Sets the menuLevel to the specified value.
   * @param value
   */
  public void setMenuLevel(byte value) {
    menuLevel = value;
  }

  public int getMenuItemsSize() {
      if (menuItems != null) {
          return menuItems.size();
      } else {
          return 0;
      }
  }
  public void setMenuItemsSize(int value) {
      // stub only
  }
 
  /**
   * Returns the string representation of this object.
   * @return 
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    sb.append("itemLabel=");
    sb.append(itemLabel);
    sb.append(", itemHelpText=");
    sb.append(itemHelpText);
    sb.append(", itemType=");
    sb.append(itemType);
    sb.append(", itemUrl=");
    sb.append(itemUrl);
    sb.append(", itemId=");
    sb.append(itemId);
    sb.append(", itemType=");
    sb.append(itemType);
    sb.append(", orderWithinParent=");
    sb.append(orderWithinParent);
    sb.append(", shiftReq=");
    sb.append(shiftReq);
    sb.append(", menuLevel=");
    sb.append(menuLevel);
    sb.append("]");
    return sb.toString();
  }
}
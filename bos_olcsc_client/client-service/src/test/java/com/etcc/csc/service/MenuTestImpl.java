/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.etcc.csc.dto.MenuItemDTO;

/**
 * Test implementation of <tt>MenuInterface</tt>.
 * @author Milosh Boroyevich
 */
@SuppressWarnings("boxing")
public class MenuTestImpl implements MenuInterface, MenuItemDTO.MenuItemBuilder {
    private static final Logger logger = Logger.getLogger(MenuTestImpl.class);

    private static final int ITEM_ARRAY_LENGTH = 8;
    // array elements (8) ordered as: id, type, label, url, text, parentId, order, level.
	private static final Object[][] arrayMain = new Object[][] {
        {190, "MENU", "Home", "/myAccountHome.do", "Home", 186, 250, 1},
        {191, "MENU", "Account Info", "/myAccount/accountInfo.do", "Account Info", 186, 260, 1},
        {192, "MENU", "Personal Info", "/personalInfoDisplay.do", "Personal Info", 191, 270, 2},
        {193, "MENU", "Payment Info", "/myAccount/accountInfo/paymentInfo.do", "Payment Info", 191, 280, 2},
        {194, "MENU", "Credit Card Mgmt", "/creditCardInfoEdit.do", "Credit Card Mgmt", 193, 290, 3},
        {195, "MENU", "Electronic Funds Transfer", "/myAccount/eft.do", "Electronic Funds Transfer", 193, 310, 3},
        {196, "MENU", "TollTags", "/account/tollTagDisplay.do", "Toll Tags", 191, 320, 2},
        {197, "MENU", "User Preferences", "/account/userPreferenceDisplay.do", "User Preferences", 191, 330, 2},
        {198, "MENU", "Account Maintenance", "/myAccount/accountMaintenance.do", "Account Maintenance", 186, 340, 1},
        {199, "MENU", "View Transactions", "/DisplayTransactions.do", "View Transactions", 188, 350, 1},
        {200, "MENU", "View Statements", "/viewStatements.do", "View Statements", 188, 360, 1},
        {201, "MENU", "View Summary", "/displayViewSummary.do", "View Summary", 188, 370, 1},
        {208, "MENU", "Change Password", "/displayChangePassword.do", "Change Password", 198, 430, 2},
        {209, "MENU", "Place Orders", "/placeOrders.do", "Place Orders", 198, 440, 2},
        {210, "MENU", "Make a Payment", "/InitializePayment.do?callBack=makePayment", "Make A Payment", 198, 450, 2},
        {212, "MENU", "Tag Requests", "/tagRequest.do", "Tag Requests", 209, 460, 3},
        {213, "MENU", "Order Status", "/orderStatus.do", "Order Status", 209, 470, 3},
        {214, "MENU", "Velcro Requests", "/velcroDisplay.do", "Velcro Requests", 209, 465, 3},
        {216, "MENU", "Contact Us", "/contactUsHome.do", "Email Us", 189, 510, 1},
    };
    private static final Object[][] arrayLog = new Object[][] {
        {186, "OLCSC_LOG", "Account Summary", "/myAccountHome.do", "account-summary", 0, 220, 1},
        {188, "OLCSC_LOG", "Account Activity", "/accountActivityHome.do", "account-activity", 0, 230, 1},
        {189, "OLCSC_LOG", "Account Information", "/accountInformation.do", "account-information", 0, 240, 1},
        {320, "OLCSC_LOG", "Vehicles & EZ TAGs", "/accountVehiclesAndTags.do", "vehicles-and-ez-tags", 0, 250, 1},
        {321, "OLCSC_LOG", "Payments & Orders", "/accountPaymentHome.do", "payments-and-orders", 0, 260, 1},
    };
    private static final Object[][] arrayPage = new Object[][] {
        //{311, "OLCSC_PAGE", "Home", "/home.do", "home", 0, 600, 1},
        //{312, "OLCSC_PAGE", "My EZ TAG", "/login.do", "my-ez-tag", 0, 620, 1},
        //{313, "OLCSC_PAGE", "Toll Road Info", "http://revamp.hctra.org/", "toll-road-info", 0, 640, 1},
        //{314, "OLCSC_PAGE", "About HCTRA", "/about.do", "about", 0, 660, 1},
        //{315, "OLCSC_PAGE", "Unpaid Tolls", "/violatorLoginDisplay.do", "unpaid-tolls", 0, 630, 1},
        {361, "OLCSC_PAGE", "Home", "/home.do", "home", 0, 600, 1},
        {362, "OLCSC_PAGE", "EZ Account", "/login.do", "my-ez-tag", 0, 620, 1},
        {363, "OLCSC_PAGE", "Toll Road Info", "../tollroads/", "toll-road-info", 0, 640, 1},
        {364, "OLCSC_PAGE", "About HCTRA", "../about/", "about", 0, 660, 1},
        {379, "OLCSC_PAGE", "Toll Violations", "/violatorLoginDisplay.do", "unpaid-tolls", 0, 630, 1},
    };
    private static final Object[][] arrayTtry = new Object[][] {
        {322, "OLCSC_TTRY", "Transactions", "/accountTransactions.do", "transactions", 188, 110, 1},
        {323, "OLCSC_TTRY", "Statements", "/accountStatements.do", "statements", 188, 120, 1},
        {324, "OLCSC_TTRY", "Yearly Summary", "/accountYearlySummary.do", "yearly-summary", 188, 130, 1},
        {325, "OLCSC_TTRY", "Receipts", "/accountReceipts.do", "receipts", 188, 140, 1},
        {326, "OLCSC_TTRY", "Monthly Invoices", "/accountMonthlyInvoices.do", "monthly-invoices", 188, 150, 1},
        {327, "OLCSC_TTRY", "View Orders", "/accountViewOrders.do", "view-orders", 321, 110, 1},
        {328, "OLCSC_TTRY", "Make a Payment", "/accountMakePayment.do", "make-payment", 321, 120, 1},
    };
    private static final Object[][] arrayUnlg = new Object[][] {
        {202, "OLCSC_UNLG", "Find an EZ TAG Store", "/home/homeTab.do", "find-a-store", 0, 380, 1},
        {204, "OLCSC_UNLG", "GET A TOLL TAG", "/home/tollTagTab.do", "get-a-toll-tag", 0, 390, 1},
        {205, "OLCSC_UNLG", "Already Have an EZ Tag? LOG IN", "/login.do", "logged-out", 0, 400, 1},
        {206, "OLCSC_UNLG", "HELP", "/home/helpTab.do", "help", 0, 410, 1},
        {207, "OLCSC_UNLG", "CONTACT US", "/home/contactUsTab.do", "contact-us", 0, 420, 1},
        {365, "OLCSC_UNLG", "Frequently Asked Questions", "https://www.hctra.org/about_faq", "frequently-asked-questions", 0, 375, 1},
    };
    private static final Object[][] arrayAll;

    static {
        int size = arrayMain.length
            + arrayLog.length
            + arrayPage.length
            + arrayTtry.length
            + arrayUnlg.length;
        ArrayList<Object[]> menuArray = new ArrayList<Object[]>(size);
        menuArray.addAll(Arrays.asList(arrayMain));
        menuArray.addAll(Arrays.asList(arrayLog));
        menuArray.addAll(Arrays.asList(arrayPage));
        menuArray.addAll(Arrays.asList(arrayTtry));
        menuArray.addAll(Arrays.asList(arrayUnlg));;
        arrayAll = menuArray.toArray(new Object[size][ITEM_ARRAY_LENGTH]);
    }

    private String user;
    private String category;

//    private MenuItemDTO[] getMenus(String sessionId, long accountId, String loginId,
//            String ipAddress, String menuUser) throws EtccException, 
//            EtccSecurityException {
//        logger.debug("getMenus():accountId=" + accountId + ", loginId=" + loginId + ", menuUser=" + menuUser);
//        return MenuItemDTO.buildMenus(arrayPage, this);
//        return getAllMenus();
/*
        this.user = menuUser;
        this.category = "TOP";
        ArrayList<MenuItemDTO> top = new ArrayList<MenuItemDTO>(5);
        short b = 1;
        top.add(createDTO(361, (byte) 1, b++, "Home", "../"));
        top.add(createDTO(362, (byte) 1, b++, "EZ Account", "/login.do"));
        top.add(createDTO(379, (byte) 1, b++, "Unpaid Tolls & Violations", "/violatorLoginDisplay.do"));
        top.add(createDTO(363, (byte) 1, b++, "Toll Road Info", "../tollroads/"));
        top.add(createDTO(364, (byte) 1, b++, "About HCTRA", "../about/"));
        return createMenu(top,3,4);
*/
//    }

//    private MenuItemDTO[] getMenus(String category, int parentItemId) 
//            throws EtccException, EtccSecurityException {
//        logger.debug("getMenusByParentString():category=" + category + ", parentId=" + parentItemId);
//        MenuItemDTO[] menus = getMenus(category);
//        if (logger.isTraceEnabled()) logger.trace("Full menu category: " + Arrays.toString(menus));
//        return MenuItemDTO.findChildMenus(menus, parentItemId);
//    }

    public MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category, int parentItemId) throws NullPointerException {
        logger.debug("getMenusByParent():category=" + category + ", parentId=" + parentItemId);
        MenuItemDTO[] menus = getMenus(category);
        return MenuItemDTO.filterMenus(menus, parentItemId);
    }

//    private MenuItemDTO[] getMenus(String category) throws EtccException, EtccSecurityException {
//        logger.debug("getMenusByCategoryString():category=" + category);
//        if (StringUtil.isEmpty(category))
//            return getAllMenus();
//        return this.getMenus(MenuItemDTO.MenuCategory.valueOf(category));
//    }

    public MenuItemDTO[] getMenus(MenuItemDTO.MenuCategory category) throws NullPointerException {
        logger.debug("getMenusByCategory():category=" + category);
        switch (category) {
        case MENU:
            return MenuItemDTO.buildMenus(arrayMain, this);
        case OLCSC_LOG:
            return MenuItemDTO.buildMenus(arrayLog, this);
        case OLCSC_PAGE:
            return MenuItemDTO.buildMenus(arrayPage, this);
        case OLCSC_TTRY:
            return MenuItemDTO.buildMenus(arrayTtry, this);
        case OLCSC_UNLG:
            return MenuItemDTO.buildMenus(arrayUnlg, this);
        }
        // this can't happen
        return new MenuItemDTO[0];
/*
        this.user = menuUser;
        this.category = category;
        ArrayList<MenuItemDTO> top = new ArrayList<MenuItemDTO>(5);
        short b = 0;
        top.add(createDTO(361, (byte) 0, b++, "Home", "../"));
        top.add(createDTO(362, (byte) 0, b++, "EZ Account", "/login.do"));
        top.add(createDTO(379, (byte) 0, b++, "Unpaid Tolls & Violations", "/violatorLoginDisplay.do"));
        top.add(createDTO(363, (byte) 0, b++, "Toll Road Info", "../tollroads/"));
        top.add(createDTO(364, (byte) 0, b++, "About HCTRA", "../about/"));
        return createMenu(top,6,4);
        //return createMenu(3,6,4);
*/
    }

    @SuppressWarnings("unused")
	private MenuItemDTO[] getAllMenus() {
        return MenuItemDTO.buildMenus(arrayAll, this);
    }

    /**
     * Create the entire menu.
     * @param c1 number of level 0 elements
     * @param c2 number of level 1 elements
     * @param c3 number of level 2 elements
     * @return the menu
     */
    @SuppressWarnings("unused")
	private ArrayList<MenuItemDTO> createMenu(int c1, int c2, int c3) {
        ArrayList<MenuItemDTO> result = new ArrayList<MenuItemDTO>();
        MenuItemDTO child, parent;
        int id = 0;
        byte level = 0;
        for (short a = 0; a < c1; a++) {
            parent = createDTO(id++, level, a);
            result.add(parent);
            for (short b = 0; b < c2; b++) {
                child = createDTO(id++, (byte)(level+1), b);
                parent.addChild(child);
                for (short c = 0; c < c3; c++) {
                    child.addChild(createDTO(id++, (byte)(level+2), c));
                }
            }
        }
        return result;
    }

    /**
     * Create the entire menu.
     * @param top collection of top-level menu items
     * @param c2 number of level 1 elements
     * @param c3 number of level 2 elements
     * @return the menu
     */
    @SuppressWarnings("unused")
	private MenuItemDTO[] createMenu(ArrayList<MenuItemDTO> top, int c2, int c3) {
        MenuItemDTO[] result = new MenuItemDTO[top.size()];
        MenuItemDTO child;
        int id = 0, idx =0;
        byte level = 0;
        for (MenuItemDTO parent : top) {
            result[idx++] = parent;
            for (short b = 0; b < c2; b++) {
                child = createDTO(id++, (byte)(level+1), b);
                parent.addChild(child);
                for (short c = 0; c < c3; c++) {
                    child.addChild(createDTO(id++, (byte)(level+2), c));
                }
            }
        }
        return result;
    }

    /**
     * Create a menu item.
     * @param id the id for the new menu item
     * @return the menu item
     */
    @SuppressWarnings("unused")
	private MenuItemDTO createDTO(int id, byte level, short order, String label, String url) {
        MenuItemDTO menu = createDTO(id, level, order);
        menu.setItemLabel(label);
        menu.setItemUrl(url);
        return menu;
    }

    /**
     * Create a menu item.
     * @param id the id for the new menu item
     * @return the menu item
     */
    private MenuItemDTO createDTO(int id, byte level, short order) {
        MenuItemDTO menu = new MenuItemDTO();
        menu.setItemHelpText("Help text for menu item " + id + ".");
        menu.setItemId(id);
        menu.setItemLabel(this.user + this.category + id);
        menu.setItemType(MenuItemDTO.MenuCategory.MENU);
        menu.setItemUrl("#");
        menu.setMenuLevel(level);
        menu.setOrderWithinParent(order);
        return menu;
    }

    public MenuItemDTO createObject(Object record) {
        Object[] item = (Object[]) record;
        MenuItemDTO menu = new MenuItemDTO();
        if (item != null && item.length == ITEM_ARRAY_LENGTH) {
            menu.setItemId((Integer) item[0]);
            menu.setItemType(MenuItemDTO.MenuCategory.valueOf(item[1].toString()));
            menu.setItemLabel((String) item[2]);
            menu.setItemUrl((String) item[3]);
            menu.setItemHelpText((String) item[4]);
            menu.setParentId(((Integer) item[5]).intValue());
            menu.setOrderWithinParent(((Integer) item[6]).shortValue());
            menu.setMenuLevel(((Integer) item[7]).shortValue());
        }
        return menu;
    }
}

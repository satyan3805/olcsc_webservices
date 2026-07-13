/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountAlertDelegate;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.EmailValidationDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountSummaryDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.AccountLoginDTO.LoginType;
import com.etcc.csc.dto.bean.AccountSummaryDetailBean;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.UIDateUtil;

/**
 * Action to gather the info needed to display the Account Summary Screen.
 */
public class AccountSummaryAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(AccountSummaryAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
    	String label = new AppDelegate().getMyEZTAGMenuLabel();
    	int menuId = SessionUtil.getPageMenuIdByLabel(session, label);
    	SessionUtil.setCurrentTabMenuId(session, menuId);
    	//         SessionUtil.getAcctDTO(request);

    	AccountLoginDTO loginDto = SessionUtil.getSessionAccountLogin(session);

    	if (loginDto.getLoginType() == null)
    	{
    		loginDto.setLoginType(LoginType.valueOf(SessionUtil.getLoginEntryPoint(session)));
    	}

    	 //D17678 11/12/2014 nkiboti--added this because we had defect when user resets password and clicks continue.
    	//Parameter added is for when reset is successful redirect user to account summary screen
    	String activityReset= request.getParameter("setResetActivity");
    	if (activityReset != null){
    	loginDto.setAcctActivity(activityReset);
    	}

    	if (loginDto == null)
    		throw new EtccSecurityException("session timed out in AccountSummaryAction");
       // else if ("P".equalsIgnoreCase(loginDto.getAcctActivity())) {
	   //D17678 08/22/2013 --Customers bypass required information by clicking EZTAGAccount

	    else if ("P".equalsIgnoreCase(loginDto.getAcctActivity())|| loginDto.getAcctActivity() == null ) {
    			return mapping.findForward("signupNotComplete");
    		}
    	AccountHistoryDelegate ahDelegate = new AccountHistoryDelegate();
    	AccountSummaryDTO dto = ahDelegate.getSummaryGraph(loginDto);
    	boolean confirmflag = false;

    	//***** Validate the email address ****/
        final String acctId = StringUtil.safeToString(request.getAttribute("acctId"));
        final boolean debugEnabled = logger.isDebugEnabled();
        if (debugEnabled){
            logger.debug("AccountSummary: Acct Id " + acctId);
        }
        if (acctId != null && !acctId.equals("0")) {
            confirmflag = changeValidationStatus(acctId, loginDto);
            if (debugEnabled)
    		logger.debug("Tagrequest.validationDone: => to change the validation status ");
    	}
    	/** Generate the email msg **/

        if (StringUtil.equals(request.getParameter("method"), "generateEmail"))
    		generateEmailMsg(loginDto);

    	AccountSummaryDetailBean[] sumCol = dto.getAcctSummary();
        SessionUtil.loadAuthorizedContacts(request);
    	generateBarChart(request, sumCol);

    	int validationstatus = validateEmailAddress(loginDto,request);
    	//Collection alertCol = dto.getAcctAlert(); Removing old implementation on alerts .....


    	//****** Account Summary Alerts Begins *******/
    	AccountAlertDelegate acctAlertDel = new AccountAlertDelegate();
    	//         AccountLoginDTO acctLoginAlertDto =  new AccountLoginDTO();
    	//         DtoUtil.copySimpleProperties(acctLoginAlertDto,loginDTO);
    	Collection<AlertDTO> alertCol = getAcctSummAlertsRequest(acctAlertDel.getAcctSummAlerts(loginDto), request);
    	//******* Account Summary Alerts Ends ******/

    	if (alertCol != null && alertCol.size() > 0 || validationstatus == 1 || validationstatus == 2){
//    		logger.info("I am validation the email");
    		saveAlerts (request, alertCol,validationstatus,confirmflag);
    	}

    	SessionUtil.getAcctDTO(request);
    	calculateHeight(request);

    	String currentDate = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(new Date());
        if (debugEnabled)
    	logger.debug("lastLoginDate:" + loginDto.getLastLoginDate());
    	String lastLoginDate = UIDateUtil.getLongDateTime(loginDto.getLastLoginDate());
    	request.setAttribute("lastLoginDate", lastLoginDate);
    	request.setAttribute("monthly", dto.getMonthly());
    	request.setAttribute("daily", dto.getDaily());
    	request.setAttribute("currentDate", currentDate);

    	String acctNumber =  dto.getBillingAcctNumber();
    	if (acctNumber!=null) {
    		if (acctNumber.length()>4)
    			acctNumber =  "****"+ acctNumber.substring(acctNumber.length()-4);
    		else
    			acctNumber = "****"+ acctNumber;
    	}

        if (Boolean.parseBoolean(request.getParameter("setEvent2")))
    		request.setAttribute("setEvent2",Boolean.TRUE);
    	//saveRequest("","");
    	request.setAttribute("acctNumber", acctNumber);
    	request.setAttribute("acctLoginInfo", loginDto);
    	session.setAttribute("acctLoginInfo", loginDto);

    	return mapping.findForward("success");
     }

    private void generateBarChart(HttpServletRequest request, AccountSummaryDetailBean[] sumBeans) {
    	try {
    		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    		if (sumBeans != null) {
    			//Iterator iter = sumCol.iterator();
    			//while(iter.hasNext()) {
    			for (int s = 0; s < sumBeans.length; s++) {
    				//AccountSummaryDetailBean bean =  (AccountSummaryDetailBean)iter.next();
    				dataset.addValue(sumBeans[s].getAmt().doubleValue(), "Series1", sumBeans[s].getAmtType());
    			}

    			CategoryAxis categoryAxis = new CategoryAxis("");
    			NumberAxis valueAxis = new NumberAxis("");
    			valueAxis.setNumberFormatOverride(StringUtil.currencyFormat);
    			BarRenderer renderer = new BarRenderer();
    			renderer.setMaximumBarWidth(0.1);
    			renderer.setItemMargin(0.20);
    			renderer.setSeriesPaint(0, new Color(98, 126, 155));
    			renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("Total amount for {1}: ${2}",
    					NumberFormat.getInstance()));
    			CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis, renderer);
    			CategoryAxis domainAxis = plot.getDomainAxis();
    			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
    			Font titleFont = new Font("Arial", Font.BOLD, 16);
    			JFreeChart chart = new JFreeChart("", titleFont, plot, false);
    			chart.setBackgroundPaint(Color.WHITE);
    			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
    			String fileName = ServletUtilities.saveChartAsPNG(chart, 350, 200, info, request.getSession());

    			//boolean imageMapExists = true;
    			//String imageMap = ChartUtilities.getImageMap(fileName, info);
    			request.setAttribute("fileName", fileName);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    private int validateEmailAddress(AccountLoginDTO acctLoginDto, HttpServletRequest request) throws EtccException, Exception {
    	EmailValidationDelegate emailValDel = new EmailValidationDelegate();
    	ResultDTO statusDTO = emailValDel.validationStatus(acctLoginDto);
    	return statusDTO.getStatus();
    }

    private boolean saveEmailValidationStatus(HttpServletRequest request, int status, boolean confirmflag) {
    	String msg = null;
    	if(status == 1){
    		/*String msg = "Your Primary e-mail address has not yet been confirmed. Please click on the link below to send a ";
        msg += "confirming email to your email account" + " <a href=\"${pageContext.request.contextPath}/accountSummary.do?method=generateEmail\">Send e-mail </a>";*/
    		//String msg = "<bean:message key = 'emailValidForm.generatePrimaryEmail' /> ";
    		String url = request.getContextPath()+"/accountSummary.do?method=generateEmail";
    		msg = "Your Primary e-mail address has not yet been confirmed. Please click on the link below to send a "
    		    + "confirming email to your email account" + "<a href=" + url + "> Send E-mail </a>";
    	} else if (status == 2 && confirmflag) {
    		msg  = "Your Primary E-mail has been confirmed";
    	}
        return saveAlert(request, msg);
    }

    private void saveAlerts(HttpServletRequest request, Collection<AlertDTO> alerts, int status, boolean confirmflag) {
    	saveEmailValidationStatus(request,status,confirmflag);

    	if(alerts != null) {
            Pattern p0 = Pattern.compile("\\{0\\}");
            Pattern ptag = Pattern.compile("\\{tag\\s\\d+\\}");
            String contextPath = request.getContextPath();
    		for (AlertDTO alertDTO : alerts) {
    			//AccountAlertDetailBean accountAlertDetailBean = (AccountAlertDetailBean)it.next();
    			//String text = accountAlertDetailBean.getAlertText();
    			String text = alertDTO.getAlertMsg();
                Matcher m = p0.matcher(text);
    			if (m.find()) {
                    String url = contextPath + "/violatorLoginDisplay.do?returnAction=myAccountHome";
    				text = m.replaceFirst("\""+url+ "\"");
                } else {
                    m = ptag.matcher(text);
                    if (m.find()) {
    					String tagToken = m.group();
    					String tagSeq = null;
    					Pattern p1 = Pattern.compile("\\d+");
    					Matcher m1 = p1.matcher(tagToken);
    					if (m1.find()) {
    						tagSeq = m1.group();
    					}
                        String url = contextPath + "/accountTempLicDisplay.do?tagSeq=" + tagSeq;
    					text = m.replaceFirst("\"" + url + "\"");
    				}
    			}

    			saveAlert(request, text);
    		}
    		//saveMessages(request, messages);
    	}
    }

    private void calculateHeight(HttpServletRequest request) throws EtccSecurityException, EtccException {
    	AccountDTO acctDto = SessionUtil.getAcctDTO(request);
    	double lowBalLevel =  acctDto.getLowBalLevel();
    	double currentBal = acctDto.getBalanceAmt();

    	int total =  130;
    	double height1 = 20;
    	if (currentBal > lowBalLevel) {
    		request.setAttribute("type", Integer.valueOf(1));
    		height1 = lowBalLevel/currentBal*total;
        } else if (currentBal < lowBalLevel) {
    		request.setAttribute("type", Integer.valueOf(2));
    		height1 = currentBal/lowBalLevel * total;
        } else {
    		request.setAttribute("type", Integer.valueOf(3));
    		height1 = 0;
    	}

    	if (height1 <10)
    		height1 = 10;
    	double height2 = total - height1;
    	if (height2 < 10 )
    		height2 = 10;

    	request.setAttribute("height1", Double.valueOf(height1));
    	request.setAttribute("height2", Double.valueOf(height2));

    	PaymentType paymentType = acctDto.getPmtTypeEnum();
    	if (paymentType == PaymentType.EFT ) {
    		request.setAttribute("acctType", "bank account");
        } else if (paymentType == PaymentType.CREDIT) {
    		request.setAttribute("acctType", "credit card");
    	}
    }

    private void generateEmailMsg(AccountLoginDTO acctLoginDto) throws EtccException {
    	EmailValidationDelegate emailValDel = new EmailValidationDelegate();
    	logger.debug("AccountSummaryAction ==> generating email");
    	emailValDel.generateEmailValidationMsg(acctLoginDto);
    }

    private boolean changeValidationStatus(String emailAcctId,AccountLoginDTO acctLoginDto) throws EtccException, Exception {
    	//emailValidForm.setAlternateEmailAddress("warren_moody@xxxx.com");
    	boolean confirmflag= false;
    	EmailValidationDelegate emailValDel = new EmailValidationDelegate();

    	if(acctLoginDto.getAcctId()== Long.parseLong(emailAcctId)){
    		logger.debug("AccountSummaryAction ==> Email Acct matches the given acct so setting the values");
    		emailValDel.setValidationDone(acctLoginDto);
    		confirmflag = true;
    	} else {
    		logger.debug("AccountSummaryAction ==>Email and Acct Ids dont match");
    	}
    	return confirmflag;
    }

    private Collection<AlertDTO> getAcctSummAlertsRequest(AlertDTO[] alertArr, HttpServletRequest request) {
    	if (alertArr == null) {
    		return null;
    	}//else
        ArrayList<AlertDTO> alerts = new ArrayList<AlertDTO>(alertArr.length);
    	for (int i = 0; i < alertArr.length; i++) {
    		alerts.add(alertArr[i]);
    		String alertMsg = alertArr[i].getAlertMsg();
    		if (alertMsg.endsWith("address?")) {
    			String url = request.getContextPath() + "/addressCleansing.do?addClean=";
    			alertArr[i].setAlertMsg(alertMsg + " <br /> <a href='" + url + "A'> Yes </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='" + url + "R'> No </a> ");
    		}
    	}
    	return alerts;
    }
}

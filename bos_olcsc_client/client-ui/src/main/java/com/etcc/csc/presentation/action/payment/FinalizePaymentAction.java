/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This action just performs a {@link ActionMapping#findForward(String)} with a value of <tt>"success"</tt>.
 * @author Milosh Boroyevich
 */
@Deprecated
public class FinalizePaymentAction extends Action {

    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
                                 
        /*
        HttpSession session = request.getSession();
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
        PaymentContext paymentContext = SessionUtil.getPaymentContext(session);
        
        if ( paymentContext.isPaymentComplete( ) ) 
        {
          if ( "IN".equals( accountLogin.getLoginType( ) ) || "CA".equals( accountLogin.getLoginType( ) ) ) 
          {
            return mapping.findForward("paymentInactiveViolator");
          }
          else
          {
            return mapping.findForward("paymentInactive");
          }
        }

        if (exceedsPaymentLimit(paymentContext)) {
            ActionMessages actionMessages = new ActionMessages();
            actionMessages.add(ActionErrors.GLOBAL_MESSAGE, 
                               new ActionMessage("Your payment cannot be processed online, please contact customer service at " + 
                                                 new AppDelegate().getContactPhoneNumber() + 
                                                 ".", false));
            saveErrors(request, actionMessages);
            return mapping.findForward("failure");
        }

        PaymentResponse paymentResponse = null;
        try {
            paymentResponse = processPayment(paymentContext, accountLogin);
        } catch (EtccErrorMessageException errorMessageException) {
            processErrorMessages(errorMessageException, request);
            return mapping.findForward("failure");
        }
        
        try
        {
          processAutoComment(paymentContext, accountLogin, paymentResponse);
        }
        catch ( Exception e ) {
          //Log this exception and do not stop the procees
          System.out.println( "[PAYMENT]Error occurred while setting payment auto comments" );
        }

        if ("A".equals(paymentResponse.getPostingStatus()) || 
            "P".equals(paymentResponse.getPostingStatus())) {
            paymentContext.setPaymentId(paymentResponse.getPaymentId().toPlainString());
            paymentContext.setPostingStatus( paymentResponse.getPostingStatus() );
            paymentContext.setPaymentComplete( true );
            if ("GetTollTag".equalsIgnoreCase(paymentContext.getCallBack())) {
                processLoginCreation(paymentContext, accountLogin, session, 
                                     response);
            }
        } else {
            ActionMessages actionMessages = new ActionMessages();
            actionMessages.add(ActionErrors.GLOBAL_MESSAGE, 
                               new ActionMessage("Payment fails, please make sure your payment information is correct.", 
                                                 false));
            saveErrors(request, actionMessages);
            return mapping.findForward("failure");
        }

        SessionUtil.setPaymentContext(session, paymentContext);
*/
        return mapping.findForward("success");
    }
/*
    private PaymentResponse processPayment(PaymentContext paymentContext, 
                                           AccountLoginDTO accountLogin) throws Exception {
                                           
        if (paymentContext.getPaymentAmount().doubleValue() <= 0) {
            EtccErrorMessageException err = new EtccErrorMessageException();
            ArrayList errList = new ArrayList();
            errList.add("Invalid amount to pay.");
            err.setErrorMessages(errList);
            throw err;
        }
                                                   
        String docType = accountLogin.getLoginType();
        if (StringUtils.isEmpty(docType)) {
            docType = "AC";
        }

        long docId = accountLogin.getAcctId();
        if ("IN".equals(docType) || "CA".equals(docType)) {
            docId = accountLogin.getInvoiceId();
        }

        CreditCard card = paymentContext.getPaymentMethod();
        return new Payment().combinedPayment(docId, 
                                                                 docType, 
                                                                 accountLogin.getDbSessionId(), 
                                                                 accountLogin.getLastLoginIp(), 
                                                                 accountLogin.getLoginId(), 
                                                                 paymentContext.getTransactionId(), 
                                                                 paymentContext.getTagAmount(), 
                                                                 paymentContext.getPaymentAmount(), 
                                                                 "", "", "", 
                                                                 paymentContext.getSavePaymentMethod(), 
                                                                 card.getCardTypeCode(), 
                                                                 "9", 
                                                                 card.getCardNumber(), 
                                                                 card.getExpirationMonth(), 
                                                                 card.getExpirationYear(), 
                                                                 card.getBillingName(), 
                                                                 card.getBillingAddress(), 
                                                                 card.getBillingAddressLine2(), 
                                                                 card.getBillingCity(), 
                                                                 card.getBillingState(), 
                                                                 card.getBillingZipcode(), 
                                                                 card.getBillingPlus4(), 
                                                                 card.getCardSecurityCode(), 
                                                                 paymentContext.getAuthorizedInvoices(), 
                                                                 paymentContext.getAuthorizedViolations(), 
                                                                 paymentContext.isVeaAccepted());
    }

    private boolean exceedsPaymentLimit(PaymentContext paymentContext) throws Exception {
        BigDecimal paymentLimit = 
            new Payment().getPaymentLimit();
        return paymentContext.getPaymentAmount().compareTo(paymentLimit) > 0;
    }

    private void processLoginCreation(PaymentContext paymentContext, 
                                      AccountLoginDTO accountLogin, 
                                      HttpSession session, 
                                      HttpServletResponse response) throws Exception {
        LoginCreationInfo loginCreationInfo = 
            paymentContext.getLoginCreation();
        String dbSessionId = 
            new TolltagDelegate().loginCreation(new BigDecimal(accountLogin.getAcctId()), 
                                                            accountLogin.getLoginType(), 
                                                            accountLogin.getDbSessionId(), 
                                                            loginCreationInfo.getUserId(), 
                                                            accountLogin.getLastLoginIp(), 
                                                            loginCreationInfo.getFirstName(), 
                                                            loginCreationInfo.getMiddleInitial(), 
                                                            loginCreationInfo.getLastName(), 
                                                            loginCreationInfo.getEmail());

        accountLogin.setLoginId(loginCreationInfo.getUserId());
        accountLogin.setDbSessionId(dbSessionId);
        session.setAttribute(Constants.SESSION_ACCOUNT_LOGIN, accountLogin);
        HttpDataUtil.setDbSessionIdCookie(response, 
                                          accountLogin.getDbSessionId());

    }


    private void processAutoComment(PaymentContext paymentContext, 
                                    AccountLoginDTO accountLogin, 
                                    PaymentResponse paymentResponse) throws Exception 
    {
        String postingStatus = paymentResponse.getPostingStatus( );
        
        long paymentId = 0;
        String failedFlag = "Y";
        if ("A".equals(postingStatus) || "P".equals(postingStatus))
        {
          paymentId = paymentResponse.getPaymentId( ).longValue();
          failedFlag = "N";
        }
        String docType = accountLogin.getLoginType();
        if (StringUtils.isEmpty(docType)) {
            docType = "AC";
        }
        long docId = accountLogin.getAcctId();
        if ("IN".equals(docType) || "CA".equals(docType)) {
            docId = accountLogin.getInvoiceId();
        }

        new Payment().autoComments(docId, docType, 
                                                       accountLogin.getDbSessionId(), 
                                                       accountLogin.getLastLoginIp(), 
                                                       accountLogin.getLoginId(), 
                                                       paymentId, 
                                                       paymentContext.getPaymentMethod().getBillingName(), 
                                                       paymentContext.getPaymentAmount().doubleValue(), 
                                                       failedFlag, 
                                                       createOlcVpsInvoicesRecBeans(paymentContext), 
                                                       createOlcUninvoicedViolsRecBeans(paymentContext));

        if (paymentContext.getVeaEligible() && 
            paymentContext.isVeaAccepted()) {
            new Payment().veaAutoComments(docId, docType, 
                                                              accountLogin.getDbSessionId(), 
                                                              accountLogin.getLastLoginIp(), 
                                                              accountLogin.getLoginId(), 
                                                              paymentContext.getVeaLicPlates()[0].getFirstName() + 
                                                              " " + 
                                                              paymentContext.getVeaLicPlates()[0].getLastName(), 
                                                              createOlcVpsInvoicesRecBeans(paymentContext));
        }
    }

    private OlcVpsInvoicesRecBean[] createOlcVpsInvoicesRecBeans(PaymentContext paymentContext) {
        OlcVpsInvoicesRecBean[] recBeans = null;
        Invoice[] invoices = paymentContext.getAuthorizedInvoices();
        if (!ArrayUtils.isEmpty(invoices)) 
        {
            recBeans = new OlcVpsInvoicesRecBean[ invoices.length ];
          for ( int  i = 0; i < invoices.length; i++ ) 
          {
              OlcVpsInvoicesRecBean recBean = new OlcVpsInvoicesRecBean( );
              recBean.setCurrDueDate( invoices[i].getDueDate()  );
              recBean.setFirstName( invoices[i].getFirstName()  );
              recBean.setInvoiceAmount( invoices[i].getAmount()  );
              recBean.setInvoiceDate( invoices[i].getInvoiceDate()  );
              recBean.setInvoiceId( new BigDecimal( invoices[i].getId()) );
              recBean.setLastName( invoices[i].getLastName()  );
              recBean.setLicPlateNbr( invoices[i].getLicPlateNumber()  );
              recBean.setLicState( invoices[i].getLicPlateState()  );
              recBean.setOnlineFee( invoices[i].getOnlineFee()  );
              recBean.setVeaAmount( invoices[i].getVeaAmount()  );
              recBean.setViolations( createOlcViolationRecBeans(invoices[i].getViolations() ) );
              recBean.setViolatorId( new BigDecimal( invoices[i].getViolatorId() ) );
              recBeans[i] = recBean;
          }
        }
        return recBeans;
    }

    private OlcViolationRecBean [] createOlcViolationRecBeans( Violation [] violations ) 
    {
      OlcViolationRecBean [] recBeans = null;
      if ( !ArrayUtils.isEmpty( violations ) ) 
      {
        recBeans = new OlcViolationRecBean[ violations.length ];
        for ( int  i = 0; i < violations.length; i++ )
        {
          OlcViolationRecBean recBean = new OlcViolationRecBean();
          recBean.setLaneName( violations[i].getLocation( ) );
          recBean.setViolationId( new BigDecimal( violations[i].getId() ) );
          recBean.setViolationTime( violations[i].getTimestamp()  );
          recBeans[i] = recBean;
        }
      }
      return recBeans;         
    }
    
    private OlcUninvoicedViolsRecBean[] createOlcUninvoicedViolsRecBeans(PaymentContext paymentContext) {
        OlcUninvoicedViolsRecBean[] recBeans = null;
        Violation[] violations = paymentContext.getAuthorizedViolations();
        if (!ArrayUtils.isEmpty(violations)) {
          recBeans = new OlcUninvoicedViolsRecBean[ violations.length ];
          for ( int  i= 0; i<violations.length; i++ ) 
          {
              OlcUninvoicedViolsRecBean recBean = new OlcUninvoicedViolsRecBean();
              recBean.setAviAmt( violations[i].getAviAmount() );
              recBean.setCashAmt( violations[i].getCashAmount() );
              recBean.setStatus( violations[i].getStatus() );
              recBean.setViolationDateTime( violations[i].getTimestamp() );
              recBean.setViolationId( new BigDecimal( violations[i].getId() ) );
              recBean.setViolationLocation( violations[i].getLocation() );
              recBean.setViolatorId( violations[i].getViolatorId() );
              recBeans[i] = recBean;
          }
        }
        return recBeans;
    }

    private void processErrorMessages(EtccErrorMessageException errorMessageException, 
                                      HttpServletRequest request) {
        ActionMessages actionMessages = new ActionMessages();
        Iterator iterator = 
            errorMessageException.getErrorMessages().iterator();
        while (iterator.hasNext()) {
            actionMessages.add(ActionErrors.GLOBAL_MESSAGE, 
                               DtoUtil.createActionMessage(iterator.next(), 
                                                 false));
        }
        saveErrors(request, actionMessages);
    }
*/
}

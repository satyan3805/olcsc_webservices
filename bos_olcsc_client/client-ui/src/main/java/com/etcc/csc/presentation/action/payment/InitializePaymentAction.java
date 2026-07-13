package com.etcc.csc.presentation.action.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@Deprecated
public class InitializePaymentAction extends Action {
    @Override
    @Deprecated
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        /*
        HttpSession session = request.getSession();

        if (!processValidate(session)) {
            // System.out.println("[PAYMENT]Validation fails");
        }

        processRefresh(session);

        AccountLoginDTO accountLoginDTO =
            SessionUtil.getSessionAccountLogin(session);
        String docType = accountLoginDTO.getLoginType();
        if (StringUtils.isEmpty(docType)) {
            docType = "AC";
        }
        long docId = accountLoginDTO.getAcctId();
        if ("IN".equals(docType) || "CA".equals(docType)) {
            docId = accountLoginDTO.getInvoiceId();
        }

        BigDecimal existingTransactionId = processExistingTransactionId( request );
        if ( existingTransactionId != null )
        {
          System.out.println( "[PAYMENT] Transaction id passed in: " + existingTransactionId.toPlainString( ) );
        }
        else
        {
          System.out.println( "[PAYMENT] Transaction id passed in: null" );
        }

        PaymentDetail paymentDetail =
            new Payment().getPaymentDetail(accountLoginDTO.getDbSessionId(),
                                                               accountLoginDTO.getLastLoginIp(),
                                                               accountLoginDTO.getLoginId(),
                                                               docId, docType,
                                                               accountLoginDTO.getLicPlate(),
                                                               accountLoginDTO.getLicState(),
                                                               existingTransactionId );

        PaymentContext paymentContext = SessionUtil.getPaymentContext(session);

        paymentContext.setInvoices(paymentDetail.getInvoices());
        paymentContext.setViolations(paymentDetail.getViolations());
        paymentContext.setTags(paymentDetail.getTags());
        paymentContext.setOriginalTagAmount(paymentDetail.getTagAmount());
        paymentContext.setTransactionId( paymentDetail.getTransactionId() );
        paymentContext.setCallBack( processCallBack(request) );
        processAutoAuthorize(paymentContext);

        if ("AC".equals(docType)) {
            processVea(paymentContext, accountLoginDTO, docType, docId);
            if ( paymentContext.getVeaEligible( ) )
            {
              paymentContext.setVeaAccepted( true );
            }
        }
*/
        /*
        if ( "makePayment".equalsIgnoreCase( paymentContext.getCallBack() ) &&
             paymentContext.getOriginalTagAmount( ).compareTo( new BigDecimal( 40 ) ) < 0 )
        {
          paymentContext.setOriginalTagAmount( new BigDecimal( 40 ) );
        }
        */
        /*
        paymentContext.setTagAmount( paymentContext.getOriginalTagAmount( ) );
        if ( "makePayment".equalsIgnoreCase( paymentContext.getCallBack( ) ) ||
             paymentContext.getCallBack( ) == null ||
             "".equalsIgnoreCase( paymentContext.getCallBack( ) ) )
        {
          if ( paymentContext.getTagAmount( ) != null && 
               paymentContext.getTagAmount( ).compareTo( new BigDecimal( 0 ) ) > 0 )
          {
            paymentContext.setOriginalTagAmount( getGoodBalanceMinPayment( ) );
          }
        }
        
        if ( StringUtils.isEmpty( paymentContext.getCallBack( ) ) && 
             !"Y".equalsIgnoreCase( paymentDetail.getForcePayment( ) ) ) 
        {
          paymentContext.setOriginalTagAmount( new BigDecimal( 0.0 ) );
          paymentContext.setTagAmount( new BigDecimal( 0.0 ) );
        }
        
        if ( "addTags".equalsIgnoreCase( paymentContext.getCallBack() ) )
        {
          paymentContext.setAddedTags( ( ( TagDTO [] ) request.getAttribute("addedTags") ) );
        }

        if ( "GetTollTag".equalsIgnoreCase( paymentContext.getCallBack() ) )
        {
          paymentContext.setLoginCreation( ( LoginCreationInfo ) request.getAttribute("loginCreationInfo") );
        }
//        paymentContext.setTagAmount( paymentContext.getOriginalTagAmount( ) );
        SessionUtil.setPaymentContext(session, paymentContext);
*/
        return mapping.findForward("success");
    }

/*
    private boolean processValidate(HttpSession session) {
        AccountLoginDTO accountLoginDTO =
            SessionUtil.getSessionAccountLogin(session);

        if (accountLoginDTO == null) {
            return false;
        }

        if (!(StringUtils.isEmpty(accountLoginDTO.getLoginType()) ||
              "AC".equals(accountLoginDTO.getLoginType()) ||
              "IN".equals(accountLoginDTO.getLoginType()) ||
              "CA".equals(accountLoginDTO.getLoginType()))) {
            System.out.println("[PAYMENT] Required information missing for payment: loginType is not valid");
        }

        if (StringUtils.isEmpty(accountLoginDTO.getLoginType()) ||
            "AC".equals(accountLoginDTO.getLoginType())) {
            if (accountLoginDTO.getAcctId() > 0) {
                System.out.println("[PAYMENT] Validation successful");
                return true;
            } else {
                System.out.println("[PAYMENT] Required information missing for payment: account id is not valid");
                return false;
            }
        }

        if ("IN".equals(accountLoginDTO.getLoginType()) ||
            "CA".equals(accountLoginDTO.getLoginType())) {
            if (StringUtils.isEmpty(accountLoginDTO.getLicPlate())) {
                System.out.println("[PAYMENT] Required information missing for payment: license plate empty");
                return false;
            }

            if (accountLoginDTO.getInvoiceId() > 0) {
                System.out.println("[PAYMENT] Validation successful");
                return true;
            } else {
                System.out.println("[PAYMENT] Required information missing for payment: invoice id is not valid");
                return false;
            }
        }

        return true;
    }


    private void processAutoAuthorize(PaymentContext paymentContext) {
        Invoice[] invoices = paymentContext.getInvoices();
        for (int i = 0; invoices != null && i < invoices.length; i++) {
            invoices[i].setAuthorized(true);
        }

        Violation[] violations = paymentContext.getViolations();
        for (int i = 0; violations != null && i < violations.length; i++) {
            violations[i].setAuthorized(true);
        }
    }


    private void processRefresh(HttpSession session) {
        SessionUtil.setPaymentContext(session, new PaymentContext());
    }


    private void processVea(PaymentContext paymentContext,
                            AccountLoginDTO accountLoginDTO, String docType,
                            long docId) throws Exception {
        Invoice[] distinctLicPlates = paymentContext.getDistinctLicPlates();
        ArrayList veaEligibleLicPlates = new ArrayList();
        if (!ArrayUtils.isEmpty(distinctLicPlates)) {
            for (Invoice invoice: distinctLicPlates) {
                if (new Payment().veaExists(new BigDecimal(docId),
                                                                docType,
                                                                accountLoginDTO.getDbSessionId(),
                                                                accountLoginDTO.getLastLoginIp(),
                                                                accountLoginDTO.getLoginId(),
                                                                invoice.getLicPlateNumber(),
                                                                invoice.getLicPlateState())) {
                    veaEligibleLicPlates.add(invoice.getLicPlateNumber() +
                                             invoice.getLicPlateState());
                }
            }
        }

        Invoice[] invoices = paymentContext.getInvoices();
        for (int i = 0; !ArrayUtils.isEmpty(invoices) && i < invoices.length;
             i++) {
            invoices[i].setVeaEligible(veaEligibleLicPlates.contains(invoices[i].getLicPlateNumber() +
                                                                     invoices[i].getLicPlateState()));
        }
    }


    private BigDecimal processExistingTransactionId( HttpServletRequest request )
    {
      BigDecimal result = null;
      Object retailTransactionIdObj = request.getAttribute( SessionUtil.SESSION_RETAIL_TRANS_ID );
      if ( retailTransactionIdObj != null )
      {
        result = new BigDecimal( retailTransactionIdObj.toString( ) );
      }
      return result;
    }

    private String processCallBack( HttpServletRequest request )
    {
      String callBack = ( String ) request.getAttribute("callBack");
      if ( StringUtils.isEmpty( callBack ) )
      {
        callBack = request.getParameter( "callBack" );
      }
      return callBack;
    }
    
    private BigDecimal getGoodBalanceMinPayment( ) 
    {
      try
      {
        String value = new AppDelegate().getSysParam( "OLC_TS_MIN_PMT_AMT_GOOD_BAL" );
        return new BigDecimal( value );
      }
      catch ( Exception e ) {
        System.out.println( e );  
      }
      return new BigDecimal( 10.0 );
    }
*/
}

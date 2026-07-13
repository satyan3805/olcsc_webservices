package com.etcc.csc.delegate;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
//import com.etcc.csc.common.Logger;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.UserEnvDTO;
import com.etcc.csc.service.AccountUtilInterface;
import com.etcc.csc.service.ServiceFactory;

public class AccountUtilDelegate implements AccountUtilInterface {
//    private static final Logger logger = Logger.getLogger(AccountUtilDelegate.class);


    public ResultDTO closeAccount(AccountLoginDTO accountLoginDTO, String refundCode)
            throws EtccException, EtccSecurityException {
        return ServiceFactory.getImplementation(AccountUtilInterface.class).closeAccount(accountLoginDTO, refundCode);

    }

    public AccountLoginDTO autoLogin(String userName, String jSessionId, String ipAddress,
            String sessionId, String acctId, UserEnvDTO userEnvDto) throws EtccException, EtccSecurityException {
        return ServiceFactory.getImplementation(AccountUtilInterface.class).autoLogin(userName, jSessionId, ipAddress, sessionId, acctId, userEnvDto);
    }

    public ResultDTO generatePlateReminder(AccountLoginDTO accountLoginDTO, String licPlate) throws EtccException, EtccSecurityException {
        return ServiceFactory.getImplementation(AccountUtilInterface.class).generatePlateReminder(accountLoginDTO, licPlate);
    }

    public String getCloseAccountAgreement() throws EtccException {
        /*
         * <div style="color:green;font-style:bold">Termination of Agreement/Ownership of an EZ Account</div> <br><br>
         * I understand that by closing this account, all EZ TAG(s)<br> associated with the account will no longer be
         * active and<br> will be in violation if used in an EZ TAG lane.<br> I also understand that if I have any
         * unpaid violations,I<br> am still responsible for payment of those violations.And<br> the closing of this EZ
         * Account, does not reprieve me of my<br> responsibility to pay existing violations.<br> I understand that if
         * there is a remaining balance on my <br> account, my credit card or bank account will be credited<br> that
         * amount within 45 days.<br>
         */
        return ServiceFactory.getImplementation(AccountUtilInterface.class).getCloseAccountAgreement();
        
//        /*
//         * termsCond = "I understand that by closing this account, all EZ TAG(s) " +
//         * "associated with the account will no longer be active and "; termsCond +=
//         * "will be in violation if used in an EZ TAG lane." +
//         * "I also understand that if I have any unpaid violations,I "; termsCond +=
//         * "am still responsible for payment of those violations.And the closing of this EZ Account, does not reprieve me of my "
//         * ; termsCond += "responsibility to pay existing violations." +
//         * "I understand that if there is a remaining balance on my "; termsCond +=
//         * "account, my credit card or bank account will be credited that amount within 45 days.";
//         */
//
//        termsCond = StringUtil.stripString(termsCond);
//        return termsCond;
    }

    public AccountLoginDTO checkCloseAccount(AccountLoginDTO accountLoginDTO) throws EtccException, EtccSecurityException {
        return ServiceFactory.getImplementation(AccountUtilInterface.class).checkCloseAccount(accountLoginDTO);
    }

}

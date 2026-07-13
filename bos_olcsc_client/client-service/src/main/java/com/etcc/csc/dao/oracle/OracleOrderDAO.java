package com.etcc.csc.dao.oracle;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.OrderDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.plsql.OLC_FULFILLMENT_REC;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.WSDateUtil;

public class OracleOrderDAO extends OrderDAO {
    private static final Logger logger = Logger.getLogger(OracleOrderDAO.class);

    /**
     * Retrieves all orders belonging to an account.
     * @return Collection<OrderDTO>
     * @throws EtccException
     * @throws EtccSecurityException
     */
    public Collection<OrderDTO> getOrders(AccountLoginDTO acctLoginDto,
            boolean pendingOnly) throws EtccException, EtccSecurityException {
        Collection<OrderDTO> col = null;
        OrderDTO[] returnOrders = null;

        CallableStatement cstmt = null;
        try {
        	 logger.debug("getOrders: acct=" + acctLoginDto.getAcctId());
/*

 FUNCTION Get_Fulfillment_Stat(P_SESSION              VARCHAR2,
                               P_ACCT_ID              NUMBER,
                               P_USER                 VARCHAR2,
                               P_IP_ADDRESS           IN VARCHAR2,
                               P_PENDING_FLAG         IN VARCHAR2 default 'N',
                               P_FULFILLMENT_STAT_ARR OUT OLC_FULFILLMENT_STAT_ARR,
                               P_ERROR_ARR            OUT OLC_ERROR_MSG_ARR)

 */
            cstmt = this.conn.prepareCall("{? = call OLCSC_TAG_ORDER_STATUS.Get_Fulfillment_Stat(?, ?, ?, ?, ?, ?, ?)}");

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_FULFILLMENT_REC",  OLC_FULFILLMENT_REC.class);
            conn.setTypeMap(typeMap);

            int idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, StringUtil.booleanToString(pendingOnly));
            cstmt.registerOutParameter(idx++, Types.ARRAY, "OL_OWNER.OLC_FULFILLMENT_STAT_ARR");
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            short found = cstmt.getShort(1);
            if (found == 1) {
                Array orders = (Array) cstmt.getObject(idx-1);
                if (orders != null) {
                    col = new ArrayList<OrderDTO>();
                    Object array[] = (Object[])orders.getArray();
                    for (int i=0; i<array.length; i++) {
                    	OLC_FULFILLMENT_REC tempOrder =
                            (OLC_FULFILLMENT_REC) array[i];
                        OrderDTO orderDto = new OrderDTO();
                        orderDto.setAcctId(acctLoginDto.getAcctId());
                        orderDto.setQty(tempOrder.getUNITS().intValue());
                        orderDto.setOrderDate(WSDateUtil.timestampToCalendar(
                            tempOrder.getREQUESTED_DATE()));
                        orderDto.setStatus(tempOrder.getFULFILLMENT_STATUS());
                        orderDto.setType(tempOrder.getFULFILLMENT_TYPE());
                        orderDto.setFullTagId(tempOrder.getTAG_ID());
                        orderDto.setLicPlate(tempOrder.getLIC_PLATE());
                        orderDto.setLocation(tempOrder.getLOCATION());
                        orderDto.setStatusDate(WSDateUtil.timestampToCalendar(
                            tempOrder.getFULFILLMENT_DATE()));
                        orderDto.setTransactionId(tempOrder.getTRANSACTION_ID().longValue());
                        col.add(orderDto);
                    }
                    returnOrders = new OrderDTO[col.size()];
                    col.toArray(returnOrders);
                }

            } else if (found == -1) {
                throw new EtccSecurityException("Security Exception in "
                    + "getOrders()");
            }

        } catch (SQLException e) {
            throw new EtccException("Error running getOrders(acctId): " + acctLoginDto.getAcctId() + ": " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
        return col;
    }
}

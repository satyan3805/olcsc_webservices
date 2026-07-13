/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.decorator;

import com.etcc.csc.dto.TagDTO;

import org.displaytag.decorator.TableDecorator;

public class RowStyleTableDecorator extends TableDecorator{

    private boolean start = false;
    private boolean end = false;


    @Override
    public void startOfGroup(String value, int group) {
        start = true;
    }

    @Override
    public void endOfGroup(String value, int group) {
        end = true;
    }

    @Override
    public String addRowClass() {
        StringBuilder sb = new StringBuilder("transaction-type-toll");
        if (start)
            sb.append(" first-in-group");

        if (end)
            sb.append(" last-in-group");

        start = false;
        end = false;

        return sb.toString();
    }

    private String checkNull(String value) {
        return value == null ?"":value;
    }
/*
    private String checkNull(Object value) {
        return value == null ? "" : value.toString();
    }
*/

    public String getLicPlateLink() {
        TagDTO tagDTO =(TagDTO) getCurrentRowObject();
        String licPlate = tagDTO.getLicPlate();
        long startIndex = tagDTO.getStartIndex();
        long index = startIndex + getListIndex();

        StringBuilder sb= null;

        if (("active".equalsIgnoreCase(tagDTO.getTagStatusDesc()) ||
            "pending".equalsIgnoreCase(tagDTO.getTagStatusDesc())) && !tagDTO.isAcctSuspended())
        {
            if (!tagDTO.isActivePbpTagExist() || "active".equalsIgnoreCase(tagDTO.getTagStatusDesc()) )
            {
                sb= new StringBuilder("<a href=\"javascript:goToEdit(");
                sb.append(index);
                sb.append(");\">");
                sb.append(checkNull(licPlate));
                sb.append("</a>");


                sb.append("<input type=\"hidden\" value=\"");
                sb.append(tagDTO.getAcctVehicleId());
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].acctVehicleId\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getLicPlate()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].licPlate\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(tagDTO.getLicState());
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].licState\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(tagDTO.isTemporaryLicPlate());
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].temporaryLicPlate\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getVehicleYear()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].vehicleYear\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getVehicleColor()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].vehicleColor\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getVehicleMake()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].vehicleMake\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getVehicleModel()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].vehicleModel\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getVehicleClassCode()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].vehicleClassCode\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getNickname()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].nickname\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(tagDTO.isMotorcycle());
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].motorcycle\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(tagDTO.getAcctTagSeq());
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].acctTagSeq\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(tagDTO.getTagAmount());
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].tagAmount\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getPbpStart()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].pbpStart\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getPbpEnd()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].pbpEnd\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(tagDTO.isPbpTag());
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].pbpTag\"/>");

                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getTagTypeCode()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].tagTypeCode\"/>");
                //TagRequestFlip
                sb.append("<input type=\"hidden\" value=\"");
                sb.append(checkNull(tagDTO.getTagStatusFlip()));
                sb.append("\" name=\"savedVehicle[");
                sb.append(index);
                sb.append("].tagStatusFlip\"/>");
            }
            else
                sb = new StringBuilder(checkNull(licPlate));
        }
        else
            sb = new StringBuilder(checkNull(licPlate));

        return sb.toString();
    }

    public String getTagStatusLink() {
      TagDTO tagDTO =(TagDTO) getCurrentRowObject();
      String tagStatusDesc = tagDTO.getTagStatusDesc();
      // logger.debug("Tag Status Desc "+ tagStatusDesc + tagDTO.isAcctSuspended());
      long startIndex = tagDTO.getStartIndex();
      long index = startIndex + getListIndex();

      StringBuilder sb= null;
      if("P".equalsIgnoreCase(tagDTO.getTagTypeCode())) {
          sb = new StringBuilder(checkNull(tagStatusDesc));
      }
      else {
        if (("A".equalsIgnoreCase(tagDTO.getAcctTagStatus()) ||
            "N".equalsIgnoreCase(tagDTO.getAcctTagStatus()) ||
            "PA".equalsIgnoreCase(tagDTO.getAcctTagStatus())) && !tagDTO.isAcctSuspended())
        {
            if (!tagDTO.isActivePbpTagExist() || "A".equalsIgnoreCase(tagDTO.getAcctTagStatus()) )
            {
                sb= new StringBuilder("<a href=\"javascript:goToChangeTagStatus(");
                sb.append(index);
                sb.append(",'"+ checkNull(tagStatusDesc)+"','"+ checkNull(tagDTO.getFullTagId())+ "'");
                sb.append(");\">");
                sb.append(checkNull(tagStatusDesc));
                sb.append("</a>");
            }
            if (!tagDTO.isActivePbpTagExist() || "N".equalsIgnoreCase(tagDTO.getAcctTagStatus()) )
            {
                sb= new StringBuilder("<a href=\"javascript:goToChangeTagStatus(");
                sb.append(index);
                sb.append(",'"+ checkNull(tagStatusDesc)+ "','"+ checkNull(tagDTO.getFullTagId())+ "'");
                sb.append(");\">");
                sb.append(checkNull(tagStatusDesc));
                sb.append("</a>");
            }
            if (!tagDTO.isActivePbpTagExist() || "PA".equalsIgnoreCase(tagDTO.getAcctTagStatus()) )
            {
            	sb= new StringBuilder("<a href=\"javascript:goToMailedTagActivation(");
        		sb.append(");\">");
        		sb.append(checkNull(tagStatusDesc));
        		sb.append("</a>");
            }
        }else{
            if(("F".equalsIgnoreCase(tagDTO.getAcctTagStatus())) && !tagDTO.isAcctSuspended()){
            	sb = new StringBuilder(checkNull(tagStatusDesc));
            }else{
            	if("V".equalsIgnoreCase(tagDTO.getAcctTagStatus()) && !tagDTO.isAcctSuspended()){

            		sb= new StringBuilder("<a href=\"javascript:goToMailedTagActivation(");
            		sb.append(");\">");
            		sb.append(checkNull(tagStatusDesc));
            		sb.append("</a>");
            	}
            	else{
            		sb = new StringBuilder(checkNull(tagStatusDesc));
            	}
            }
        }
      }
      return sb.toString();
    }
}

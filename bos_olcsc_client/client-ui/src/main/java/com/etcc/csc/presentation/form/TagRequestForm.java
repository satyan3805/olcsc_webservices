/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.util.SessionUtil;

/**
 * Object representation of an HTML form for storing Vehicle Tag data submitted by the user.
 * @author unknown
 * @author Milosh Boroyevich
 */
public class TagRequestForm extends ValidatorActionForm {
	private static final long serialVersionUID = -3819581179059628441L;

	private List<TagDTO> savedVehicles;
    private boolean deleteVehicle = false;
    private int vehicleIndexToModify=-1;
    private String licensePlate;
    private String state = "TX";
    private String year;
    private String color;
    private String make;
    private String model;
    private String vehicleClass;
    private boolean tempLicense = false;
    private String licenseExpiration;
    private boolean checkDup;
    private long acctTagSeq;
    private boolean printMode;
    private long retailTransId = -1;
    private boolean pickup;

    private String nickname;
    private boolean motorcycle = false;
    private double tagAmount;
    private double lowBalanceAmount;


	private double totalAmount;
    private double tagSaleAmount;
    private double rebillAmount;
    private double depositAmount;

    private boolean fromConfirmation; //used by the "create EZ tag account" flow. true means coming back from the confirmation page.

    private boolean pbpTag = false;  //this param will be used by EZ plate
    private String pbpStart;
    private String pbpEnd;

    private boolean activePbpTagExists=false;
    private boolean allowEditPbpStart;
    private boolean allowEditPbpEnd;

    // used to decide "ez tag or ez plate vehicle"
    private String ezTagOrPlate =   "Most vehicles can use the EZ TAG stickers but some vehicles have a limited amount of space on the windshield and may require the use of an EZ Plate. "
                                    +" (*Note: it is difficult to know all vehicles that cannot use a sticker tag. If you feel that your windshield mounted EZ TAG is not working properly, " +
                                     "please contact EZ TAG Customer Service at (281) 875-EASY (3279) to speak with a representative about your specific tag needs.)";

   //** Activation and Inactivation Form Fields **/
    private String agencyId;
    private String acctTagStatus;
    private String tagStatusDesc;
    private String tagTypeCode;
    private String tagStatusFlip;
    private int noOfActiveTags = 0;
    private String ezTagOrEZPlate;

    private Long stickerTagCount;
    private Long motocycleCount;
    private Long pbpCount;

    //private String requestStart;
   // private String requestEnd;

    private String showContinueMsg;
    public String getShowContinueMsg() {
		return showContinueMsg;
	}

	public void setShowContinueMsg(String showContinueMsg) {
		this.showContinueMsg = showContinueMsg;
	}

	/**
     * Returns a new tag initialized with the contents of this form.
     * @return the new tag DTO
     */
    public TagDTO newTagDTO() {

         return newTagDTO(this.tempLicense);

    }

    /**
     * Returns a new tag initialized with the contents of this form.
     * Overrides the temporary license plate flag with the specified value.
     * @param tempLicense flag to use for temporary license plate
     * @return the new tag DTO
     */
    public TagDTO newTagDTO(boolean tempLicense) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setLicPlate(getLicensePlate());
        tagDTO.setLicState(getState());
        tagDTO.setTemporaryLicPlate(tempLicense);
        tagDTO.setVehicleYear(getYear());
        tagDTO.setVehicleColor(getColor());
        tagDTO.setVehicleMake(getMake());
        tagDTO.setVehicleModel(getModel());
        tagDTO.setVehicleClassCode(getVehicleClass());
        tagDTO.setNickname(getNickname());
        tagDTO.setMotorcycle(isMotorcycle());
        tagDTO.setAcctTagSeq(getAcctTagSeq());
        tagDTO.setTagAmount(getTagAmount());


        return tagDTO;
    }

    /**
     * Initialize this form with the specified vehicle tag.
     * @param tagDTO vehicle tag to use for initialization
     * @param vehicleMakeToProperCase whether to convert the tag <tt>make</tt> to proper case
     * @return a reference to this form
     */
    // Consolidated from AccountTempLicDisplayAction and AccountVehicleEditDisplayAction in package com.etcc.csc.presentation.action.accountManagement
    public TagRequestForm initialize(TagDTO tagDTO, boolean vehicleMakeToProperCase) {
        setLicensePlate(tagDTO.getLicPlate());
        setState(tagDTO.getLicState());
        setTempLicense(tagDTO.isTemporaryLicPlate());
        setYear(tagDTO.getVehicleYear());
        setColor(tagDTO.getVehicleColor());
        if (vehicleMakeToProperCase) {
            String str = tagDTO.getVehicleMake();
            str = (str.substring(0,1).toUpperCase() + str.substring(1,str.length()).toLowerCase());
            setMake(str);
        } else {
            setMake(tagDTO.getVehicleMake());
        }
        setModel(tagDTO.getVehicleModel());
        setVehicleClass(tagDTO.getVehicleClassCode());
        setNickname(tagDTO.getNickname());
        setMotorcycle(tagDTO.isMotorcycle());
        setAcctTagSeq(tagDTO.getAcctTagSeq());
        return this;
    }

    /**
     * Set the PBP fields and return whether this form represents a PBP Tag.
     * @param tagDTO the tag to use for setting PBP fields
     * @return <tt>true</tt> if this form represents a PBP tag
     * @see #isPbpTag()
     * @see #setPbpTag(boolean)
     * @see #setPbpStart(String)
     * @see #setPbpEnd(String)
     */
    public boolean updatePbpTag(TagDTO tagDTO) {
    	if (tagDTO.isPbpTag()) {
    		this.pbpTag = true;
    		this.pbpStart = tagDTO.getPbpStart();
    		this.pbpEnd = tagDTO.getPbpEnd();
    	} else {
    		this.pbpTag = false;
    		this.pbpStart = "";
    		this.pbpEnd = "";
    	}
    	return this.pbpTag;
    }

    /**
     * Set the saved vehicles on this form using the value stored in the specified HTTP servlet request.
     * @param request the request containing the list of vehicles
     * @return <tt>true</tt> if this object was changed as a result of the call
     * @see SessionUtil#getVehicleList(HttpServletRequest)
     */
    public boolean setSavedVehicles(HttpServletRequest request) {
        List<TagDTO> requestVehicles = SessionUtil.getVehicleList(request);
        if (requestVehicles != null && this.savedVehicles != requestVehicles) {
            this.savedVehicles = requestVehicles;
            return true;
        }
        return false;
    }

    /**
     * Iterator over the collection of vehicles creating a list of vehicles to set.
     * @see #setSavedVehicles(List)
     */
    public void setSavedVehicles(Collection<TagDTO> savedVehicles) {
    	if (savedVehicles == null) {
            this.savedVehicles = null;
    	} else if (savedVehicles.getClass() == List.class) {
    		setSavedVehicles((List<TagDTO>) savedVehicles);
    	} else if (savedVehicles.isEmpty()) {
            this.savedVehicles = new ArrayList<TagDTO>();
    	} else {
    		ArrayList<TagDTO> vehicles = new ArrayList<TagDTO>(savedVehicles.size());
    		for (TagDTO tag : savedVehicles)
    			vehicles.add(tag);
    		this.savedVehicles = vehicles;
    	}
    }

    public TagDTO getSavedVehicle(int index) {
        if (savedVehicles == null) {
            savedVehicles = new ArrayList<TagDTO>();
        }
        int size = savedVehicles.size();
        for (int i=0; i<index+1-size; i++) {
            savedVehicles.add(new TagDTO());
        }
        return savedVehicles.get(index);
    }

    public void setVehicle(TagDTO vehicle) {
        this.licensePlate = vehicle.getLicPlate();
        if (vehicle.getLicState()!=null && vehicle.getLicState().length()>0)
            this.state = vehicle.getLicState();
        else
            this.state="TX";

        this.year = vehicle.getVehicleYear();
        this.make = vehicle.getVehicleMake();
        this.model = vehicle.getVehicleModel();
        this.color = vehicle.getVehicleColor();
        this.tempLicense = vehicle.isTemporaryLicPlate();
//        if (this.tempLicense && vehicle.getPlateExpiration()!=null)
//        {
//            //this.licenseExpiration = UIDateUtil.getShortDate(vehicle.getPlateExpirDate());
//            this.licenseExpiration = vehicle.getPlateExpiration();
//        }
//        else
//            this.licenseExpiration="";
        this.vehicleClass = vehicle.getVehicleClassCode();
        this.nickname = vehicle.getNickname();
        this.checkDup = vehicle.isCheckDup();
        this.acctTagSeq = vehicle.getAcctTagSeq();
        this.motorcycle = vehicle.isMotorcycle();
        this.tagAmount = vehicle.getTagAmount();
    }

    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
        setTempLicense(false);
        setDeleteVehicle(false);
        setVehicleIndexToModify(-1);
        setMotorcycle(false);
        pickup = false;
    }

    public void clearForm() {
    	licensePlate = null;
    	state = null;
    	year = null;
    	color = null;
    	make = null;
    	model = null;
    	vehicleClass = null;
    	tempLicense = false;
    	nickname = null;
    	vehicleIndexToModify=-1;
    	deleteVehicle= false;
    	motorcycle = false;
    	tagAmount = 0;
    	acctTagSeq = 0;
    	pbpTag = false;
    	pickup = false;
    }

    public TagDTO.DeliveryMethod getDeliveryMethod() {
        return this.pickup ? TagDTO.DeliveryMethod.PICKUP : TagDTO.DeliveryMethod.MAIL;
    }

    /**
     * Returns total tag count.
     * @return first tier tag count
     * @see #getTotalTagCount()
     */
    public int getFirstTierEZTag() {
        return getTotalTagCount();
    }

    /**
     * Returns total tag count minus 3 (min 0).
     * @return second tier tag count
     * @see #getTotalTagCount()
     */
    public int getSecondTierEZTag() {
        int vehiclesAdded = getTotalTagCount();
        return vehiclesAdded <= 3?0:(vehiclesAdded-3);
    }

    /**
     * Returns <tt>true</tt> if there is at least one EZ Tag present.
     * @return <tt>true</tt> if there is at least one non-PBP Tag present
     * @see #savedVehicles
     * @see TagDTO#isPbpTag()
     */
    public boolean isEzTagsExist() {
    	if (savedVehicles == null)
    		return false;
    	boolean found = false;
    	for (TagDTO tagDTO : savedVehicles) {
    		if (!tagDTO.isPbpTag()) {
    			found = true;
    			break;
    		}
    	}
    	return found;
    }

    public int getEzTagCount() {
    	if (savedVehicles == null)
    		return 0;
    	int ezTagCount = 0;
        for (TagDTO tagDTO : savedVehicles) {
    		if (!tagDTO.isPbpTag())
    			ezTagCount++;
    	}
    	return ezTagCount;
    }

    public int getEzPlateCount() {
    	if (savedVehicles == null)
    		return 0;
    	int ezPlateCount = 0;
        for (TagDTO tagDTO : savedVehicles) {
    		if (tagDTO.isPbpTag())
    			ezPlateCount++;
    	}
    	return ezPlateCount;
    }

    public int getTotalTagCount() {
    	return (savedVehicles == null ? 0 : savedVehicles.size());
    }

    /**
     * Returns <tt>true</tt> if there is at least one PBP Tag present.
     * @return <tt>true</tt> if there is at least one PBP Tag present
     * @see #savedVehicles
     * @see TagDTO#isPbpTag()
     */
    public boolean isPbpTagsExist() {
    	if (savedVehicles == null)
    		return false;
    	boolean found = false;
        for (TagDTO tagDTO : savedVehicles) {
    		if (tagDTO.isPbpTag()) {
    			setActivePbpTagExists(true);
    			found = true;
    			break;
    		}
    	}
    	return found;
    }



	// ******** Standard setters/getters ********

    public void setPickup(boolean forPickUp) {
        this.pickup = forPickUp;
    }

    public boolean isPickup() {
    	return pickup;
    }

    public void setDeleteVehicle(boolean deleteVehicle) {
        this.deleteVehicle = deleteVehicle;
    }

    public boolean isDeleteVehicle() {
        return deleteVehicle;
    }

    public void setVehicleIndexToModify(int vehicleIndexToModify) {
        this.vehicleIndexToModify = vehicleIndexToModify;
    }

    public int getVehicleIndexToModify() {
        return vehicleIndexToModify;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getMake() {
        return make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setTempLicense(boolean tempLicence) {
        this.tempLicense = tempLicence;
    }

    public boolean isTempLicense() {
        return tempLicense;
    }

    public void setLicenseExpiration(String licenceExpiration) {
        this.licenseExpiration = licenceExpiration;
    }

    public String getLicenseExpiration() {
        return licenseExpiration;
    }

    public void setCheckDup(boolean checkDup) {
        this.checkDup = checkDup;
    }

    public boolean isCheckDup() {
        return checkDup;
    }

    public void setAcctTagSeq(long acctTagSeq) {
        this.acctTagSeq = acctTagSeq;
    }

    public long getAcctTagSeq() {
        return acctTagSeq;
    }

    public void setSavedVehicles(List<TagDTO> savedVehicles) {
        this.savedVehicles = savedVehicles;
    }

    public List<TagDTO> getSavedVehicles() {
        return savedVehicles;
    }


    public List<TagDTO> getEzTagVehicles() {
    	List<TagDTO> result = new ArrayList<TagDTO>();
    	for ( TagDTO dto : savedVehicles )
    	{
    		if (!dto.isPbpTag())
    		{
    			result.add(dto);
    		}
    	}
    	return result;

    }

    public void setPrinterMode(boolean printMode) {
        this.printMode = printMode;
    }

    public boolean isPrintMode() {
        return printMode;
    }

    public void setRetailTransId(long retailTransId) {
        this.retailTransId = retailTransId;
    }

    public long getRetailTransId() {
        return retailTransId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setMotorcycle(boolean motorcycle) {
        this.motorcycle = motorcycle;
    }

    public boolean isMotorcycle() {
        return motorcycle;
    }

    public void setTagAmount(double tagAmount) {
        this.tagAmount = tagAmount;
    }

    public double getTagAmount() {
        return tagAmount;
    }

    public void setFromConfirmation(boolean fromConfirmation) {
        this.fromConfirmation = fromConfirmation;
    }

    public boolean isFromConfirmation() {
        return fromConfirmation;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getRebillAmount() {
		return rebillAmount;
	}

	public void setRebillAmount(double rebillAmount) {
		this.rebillAmount = rebillAmount;
	}

	public void setTagSaleAmount(double tagSaleAmount) {
        this.tagSaleAmount = tagSaleAmount;
    }

    public double getTagSaleAmount() {
        return tagSaleAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setPbpTag(boolean pbpTag) {
        this.pbpTag = pbpTag;
    }

    public boolean isPbpTag() {
        return pbpTag;
    }

    public void setPbpStart(String pbpStart) {
        this.pbpStart = pbpStart;
    }

    public String getPbpStart() {
        return pbpStart;
    }

    public void setPbpEnd(String pbpEnd) {
        this.pbpEnd = pbpEnd;
    }

    public String getPbpEnd() {
        return pbpEnd;
    }

    public void setActivePbpTagExists(boolean activePbpTagExists) {
        this.activePbpTagExists = activePbpTagExists;
    }

    public boolean isActivePbpTagExists() {
        return activePbpTagExists;
    }

    public void setAllowEditPbpStart(boolean allowEditPbpStart) {
        this.allowEditPbpStart = allowEditPbpStart;
    }

    public boolean isAllowEditPbpStart() {
        return allowEditPbpStart;
    }

    public void setAllowEditPbpEnd(boolean allowEditPbpEnd) {
        this.allowEditPbpEnd = allowEditPbpEnd;
    }

    public boolean isAllowEditPbpEnd() {
        return allowEditPbpEnd;
    }

    public void setEzTagOrPlate(String ezTagOrPlate) {
        this.ezTagOrPlate = ezTagOrPlate;
    }

    public String getEzTagOrPlate() {
        return ezTagOrPlate;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAcctTagStatus(String acctTagStatus) {
        this.acctTagStatus = acctTagStatus;
    }

    public String getAcctTagStatus() {
        return acctTagStatus;
    }

    public void setTagStatusDesc(String tagStatusDesc) {
        this.tagStatusDesc = tagStatusDesc;
    }

    public String getTagStatusDesc() {
        return tagStatusDesc;
    }

    public void setTagTypeCode(String tagTypeCode) {
        this.tagTypeCode = tagTypeCode;
    }

    public String getTagTypeCode() {
        return tagTypeCode;
    }

    public void setTagStatusFlip(String tagStatusFlip) {
        this.tagStatusFlip = tagStatusFlip;
    }

    public String getTagStatusFlip() {
        return tagStatusFlip;
    }

    public void setNoOfActiveTags(int noOfActiveTags) {
        this.noOfActiveTags = noOfActiveTags;
    }

    public int getNoOfActiveTags() {
        return noOfActiveTags;
    }

    public void setEzTagOrEZPlate(String ezTagOrEZPlate) {
        this.ezTagOrEZPlate = ezTagOrEZPlate;
    }

    public String getEzTagOrEZPlate() {
        return ezTagOrEZPlate;
    }

    public Long getStickerTagCount() {
		return stickerTagCount;
	}

	public void setStickerTagCount(Long stickerTagCount) {
		this.stickerTagCount = stickerTagCount;
	}

	public Long getMotocycleCount() {
		return motocycleCount;
	}

	public void setMotocycleCount(Long motocycleCount) {
		this.motocycleCount = motocycleCount;
	}

	public Long getPbpCount() {
		return pbpCount;
	}

	public void setPbpCount(Long pbpCount) {
		this.pbpCount = pbpCount;
	}

	@Override
    public String toString() {
        return "TagRequestForm [acctTagSeq=" + acctTagSeq + ", acctTagStatus="
                + acctTagStatus + ", activePbpTagExists=" + activePbpTagExists
                + ", agencyId=" + agencyId + ", allowEditPbpEnd="
                + allowEditPbpEnd + ", allowEditPbpStart=" + allowEditPbpStart
                + ", checkDup=" + checkDup + ", color=" + color
                + ", deleteVehicle=" + deleteVehicle + ", depositAmount="
                + depositAmount + ", ezTagOrEZPlate=" + ezTagOrEZPlate
                + ", ezTagOrPlate=" + ezTagOrPlate + ", fromConfirmation="
                + fromConfirmation + ", licenseExpiration=" + licenseExpiration
                + ", licensePlate=" + licensePlate + ", make=" + make
                + ", model=" + model + ", motorcycle=" + motorcycle
                + ", nickname=" + nickname + ", noOfActiveTags="
                + noOfActiveTags + ", pbpEnd=" + pbpEnd + ", pbpStart="
                + pbpStart + ", pbpTag=" + pbpTag + ", pickup=" + pickup
                + ", printMode=" + printMode + ", retailTransId="
                + retailTransId + ", savedVehicles=" + savedVehicles
                + ", state=" + state + ", tagAmount=" + tagAmount
                + ", tagSaleAmount=" + tagSaleAmount + ", tagStatusDesc="
                + tagStatusDesc + ", tagStatusFlip=" + tagStatusFlip
                + ", tagTypeCode=" + tagTypeCode + ", tempLicense="
                + tempLicense + ", totalAmount=" + totalAmount
                + ", vehicleClass=" + vehicleClass + ", vehicleIndexToModify="
                + vehicleIndexToModify + ", year=" + year + "]";
    }

    public List<TagDTO> copySavedVehicles() throws IllegalAccessException, InvocationTargetException {
    	if (this.getSavedVehicles() == null)
    		return null;
        List<TagDTO> aCopy = new ArrayList<TagDTO>();
        for (int i=0; i<this.getSavedVehicles().size(); i++) {
            TagDTO tmp = new TagDTO();
            BeanUtils.copyProperties(tmp, this.getSavedVehicle(i));
            aCopy.add(tmp);
        }
        return aCopy;
    }
    
    public double getLowBalanceAmount() {
		return lowBalanceAmount;
	}

	public void setLowBalanceAmount(double lowBalanceAmount) {
		this.lowBalanceAmount = lowBalanceAmount;
	}
}

package com.etcc.csc.dto;

public class FileMultiVehicleUploadDTO extends MultiVehicleUploadDTO {
	
	 /**
	 * author -- Pavan C , Jan-19-2017
	 */
	
	
	private static final long serialVersionUID = 1L;
	private String fileName;
	    private String fileLocation;
	    private String fileCheckSum;
	    
	    public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileLocation() {
			return fileLocation;
		}

		public void setFileLocation(String fileLocation) {
			this.fileLocation = fileLocation;
		}

		public String getFileCheckSum() {
			return fileCheckSum;
		}

		public void setFileCheckSum(String fileCheckSum) {
			this.fileCheckSum = fileCheckSum;
		}
}

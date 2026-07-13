package com.etcc.csc.dto;

/**
 * Data Transfer Object for a country.
 */
public class CountryDTO extends BaseDTO {
    
    /**
     * Unique ID for Serialization with Version number.
     */
    //Do NOT regenerate, to allow compatibility with "foreign" clients, such as Idea.
    private static final long serialVersionUID = 7534539722399726528L;
    
    private String country;
    private String countryCode;  // PK in DB

    /**
     * @return the country
     */
    public String getCountry() {
        return this.country;
        //end getCountry
    }
    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
        //end setCountry
    }
    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return this.countryCode;
        //end getCountryCode
    }
    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        //end setCountryCode
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.countryCode == null) ? 0 : this.countryCode.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CountryDTO other = (CountryDTO) obj;
        if (this.countryCode == null) {
            if (other.countryCode != null)
                return false;
        } else if (!this.countryCode.equals(other.countryCode))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "CountryDTO[" + this.country + "/" + this.countryCode + "]";
    }
}

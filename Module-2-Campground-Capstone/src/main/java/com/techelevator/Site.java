package com.techelevator;

public class Site {
	
	private Long siteId;
	private Long campgroundId;
	private Long siteNumber;
	private Long maxOccupancy;
	private Boolean isAccessible;
	private int maxRVLength;
	private Boolean hasUtilities;
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public Long getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(Long siteNumber) {
		this.siteNumber = siteNumber;
	}
	public Long getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(Long maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public Boolean getIsAccessible() {
		return isAccessible;
	}
	public void setIsAccessible(Boolean isAccessible) {
		this.isAccessible = isAccessible;
	}
	public int getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(int maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public Boolean getHasUtilities() {
		return hasUtilities;
	}
	public void setHasUtilities(Boolean hasUtilities) {
		this.hasUtilities = hasUtilities;
	}
	
	
}

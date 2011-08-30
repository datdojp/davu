package net.aihat.service;

public class ConfigurationService {
	public String imageBackupFolder;
	public int nHomepageClips;
	private int nFeaturedClips;
	private long nRowsPerPage;
	private long nFollowerToDisplay;

	//getter setter
	public String getImageBackupFolder() {
		return imageBackupFolder;
	}

	public void setImageBackupFolder(String imageBackupFolder) {
		this.imageBackupFolder = imageBackupFolder;
	}

	public int getnHomepageClips() {
		return nHomepageClips;
	}

	public void setnHomepageClips(int nHomepageClips) {
		this.nHomepageClips = nHomepageClips;
	}
	public int getnFeaturedClips() {
		return nFeaturedClips;
	}

	public void setnFeaturedClips(int nFeaturedClips) {
		this.nFeaturedClips = nFeaturedClips;
	}

	public long getnRowsPerPage() {
		return nRowsPerPage;
	}

	public void setnRowsPerPage(long nRowsPerPage) {
		this.nRowsPerPage = nRowsPerPage;
	}

	public long getnFollowerToDisplay() {
		return nFollowerToDisplay;
	}

	public void setnFollowerToDisplay(long nFollowerToDisplay) {
		this.nFollowerToDisplay = nFollowerToDisplay;
	}
	
}

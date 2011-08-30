package net.aihat.dto;


public class GenreDto extends BaseUserCareDto {
	private String nameVi;	  
	private String nameEn;
	private String nameViSearch;	  
	private String nameEnSearch;
//	private String nameViGet;
	private String nClips;
	
	//utils
	public String forLog() {
		String result = "";
		if(getId() != null) {
			result = result + "id:'" + getId() + "' ";
		}
		if(getNameEn() != null) {
			result = result + "english-name:'" + getNameEn() + "' ";
		}
		if(getNameVi() != null) {
			result = result + "vietnamese-name:'" + getNameVi() + "' ";
		}
		return result;
	}
	
	//getter setter
	public String getNameVi() {
		return nameVi;
	}
	public void setNameVi(String nameVi) {
		this.nameVi = nameVi;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getNameViSearch() {
		return nameViSearch;
	}
	public void setNameViSearch(String nameViSearch) {
		this.nameViSearch = nameViSearch;
	}
	public String getNameEnSearch() {
		return nameEnSearch;
	}
	public void setNameEnSearch(String nameEnSearch) {
		this.nameEnSearch = nameEnSearch;
	}

	public String getnClips() {
		return nClips;
	}
	public void setnClips(String nClips) {
		this.nClips = nClips;
	}

//	public String getNameViGet() {
//		return nameViGet;
//	}
//
//	public void setNameViGet(String nameViGet) {
//		this.nameViGet = nameViGet;
//	}
}

package net.aihat.dto;

import java.util.Date;

import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;

public class ComposerDto extends BaseUserCareDto {
	private String name;
	private String nameSearch;
	private Date birthday;	  
	private String country = AihatConstants.DEFAULT_COUNTRY;
	private String description;	  
	private String image;
	private String nClips;
	
	//utils
	private static final int DESCRIPTION_MAX_LENGTH = 45;
	public String getShortDescription() {
		if(AihatUtils.isEmpty(description) || description.length() <= DESCRIPTION_MAX_LENGTH) {
			return description;
		}
		
		return description.substring(0, DESCRIPTION_MAX_LENGTH) + "...";
	}
	
	public String forLog() {
		String result = "";
		if(getId() != null) {
			result = result + "id:'" + getId() + "' ";
		}
		if(getName() != null) {
			result = result + "name:'" + getName() + "' ";
		}
		return result;
	}
	
	//getter setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNameSearch() {
		return nameSearch;
	}
	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	public String getnClips() {
		return nClips;
	}
	public void setnClips(String nClips) {
		this.nClips = nClips;
	}
}

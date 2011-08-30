package net.aihat.dto;

import java.util.Date;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;

public class SingerDto extends BaseUserCareDto {
	private String name;
	private String nameSearch;
	private Date birthday;
	private String company;
	private String website;
	private String image;
	private String description;
	private String country = AihatConstants.DEFAULT_COUNTRY;
	private Integer nClips;
	private Integer nFans;
	private Integer nFollowers;
	
	//this field is to determine that singer is followed by current logged-in user
	private Boolean followed = Boolean.FALSE;
	private Boolean liked = Boolean.FALSE;
	
	//objects
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
	
	//utils
	private static final int DESCRIPTION_MAX_LENGTH = 45;
	public String getShortDescription() {
		if(AihatUtils.isEmpty(description) || description.length() <= DESCRIPTION_MAX_LENGTH) {
			return description;
		}
		
		return description.substring(0, DESCRIPTION_MAX_LENGTH) + "...";
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNameSearch() {
		return nameSearch;
	}
	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}
	public Integer getnClips() {
		return nClips;
	}
	public void setnClips(Integer nClips) {
		this.nClips = nClips;
	}
	public Integer getnFans() {
		return nFans;
	}
	public void setnFans(Integer nFans) {
		this.nFans = nFans;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getFollowed() {
		return followed;
	}

	public void setFollowed(Boolean followed) {
		this.followed = followed;
	}

	public Integer getnFollowers() {
		return nFollowers;
	}

	public void setnFollowers(Integer nFollowers) {
		this.nFollowers = nFollowers;
	}

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean liked) {
		this.liked = liked;
	}
	
}


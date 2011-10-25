package net.aihat.dto;

import java.util.Date;
import java.util.List;


public class UserDto extends BaseDto {
	private String mail;
	private Boolean blocked;
	private PasswordDto password;
	private Date lastLogin;
	private String language;
	private Boolean admin;
	private Boolean mod;
	private String image;
	private Integer nClips;
	private Integer nSingers;
	private Integer nComposers;
	private Integer nGenres;
	private String name;
	private String sex;
	private Date birthday;
	private Integer nFollowers;
	private Integer nViews;
	
	//followers of this uploader
	private List<UserDto> followers;
	
	//this field is to determine that uploader is followed by current logged-in user
	private Boolean followed = Boolean.FALSE;
	
	//utils
	public String forLog() {
		String result = "";
		if(getId() != null) {
			result = result + "id:'" + getId() + "' ";
		}
		if(getMail() != null) {
			result = result + "title:'" + getMail() + "' ";
		}
		return result;
	}
	
	public boolean isAdminOrMod() {
		return Boolean.TRUE.equals(getAdmin()) || Boolean.TRUE.equals(getMod());
	}
	
	//getter setter
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public PasswordDto getPassword() {
		return password;
	}
	public void setPassword(PasswordDto password) {
		this.password = password;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public Integer getnClips() {
		return nClips;
	}

	public void setnClips(Integer nClips) {
		this.nClips = nClips;
	}

	public Integer getnSingers() {
		return nSingers;
	}

	public void setnSingers(Integer nSingers) {
		this.nSingers = nSingers;
	}

	public Integer getnGenres() {
		return nGenres;
	}

	public void setnGenres(Integer nGenres) {
		this.nGenres = nGenres;
	}

	public Integer getnComposers() {
		return nComposers;
	}

	public void setnComposers(Integer nComposers) {
		this.nComposers = nComposers;
	}

	public List<UserDto> getFollowers() {
		return followers;
	}

	public void setFollowers(List<UserDto> followers) {
		this.followers = followers;
	}

	public Boolean getFollowed() {
		return followed;
	}

	public void setFollowed(Boolean followed) {
		this.followed = followed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getnFollowers() {
		return nFollowers;
	}

	public void setnFollowers(Integer nFollowers) {
		this.nFollowers = nFollowers;
	}

	public Boolean getMod() {
		return mod;
	}

	public void setMod(Boolean mod) {
		this.mod = mod;
	}

	public Integer getnViews() {
		return nViews;
	}

	public void setnViews(Integer nViews) {
		this.nViews = nViews;
	}
	
	
}

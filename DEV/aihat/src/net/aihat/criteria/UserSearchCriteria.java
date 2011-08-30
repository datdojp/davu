package net.aihat.criteria;

public class UserSearchCriteria extends BaseCriteria {
	private String mail;
	private Boolean blocked;
	private Boolean admin;
	private Integer followedBy;

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

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Integer getFollowedBy() {
		return followedBy;
	}

	public void setFollowedBy(Integer followedBy) {
		this.followedBy = followedBy;
	}
	
}

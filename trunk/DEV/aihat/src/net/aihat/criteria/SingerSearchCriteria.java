package net.aihat.criteria;

import net.aihat.dto.UserDto;

public class SingerSearchCriteria extends BaseCriteria {
	private String name;
	private UserDto user;
	private Integer followedBy;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public Integer getFollowedBy() {
		return followedBy;
	}
	public void setFollowedBy(Integer followedBy) {
		this.followedBy = followedBy;
	}
	
}

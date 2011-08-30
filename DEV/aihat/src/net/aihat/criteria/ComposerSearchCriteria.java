package net.aihat.criteria;

import net.aihat.dto.UserDto;

public class ComposerSearchCriteria extends BaseCriteria {
	private String name;
	private UserDto user;
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
}

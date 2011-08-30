package net.aihat.criteria;

import net.aihat.dto.UserDto;

public class GenreSearchCriteria extends BaseCriteria {
	private String language;
	private String name;
	private UserDto user;
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
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

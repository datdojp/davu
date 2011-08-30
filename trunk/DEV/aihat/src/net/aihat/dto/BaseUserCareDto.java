package net.aihat.dto;


public abstract class BaseUserCareDto extends BaseDto {
	private UserDto user;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}

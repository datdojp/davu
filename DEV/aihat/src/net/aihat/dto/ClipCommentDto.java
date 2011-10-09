package net.aihat.dto;

import java.util.Date;

public class ClipCommentDto extends BaseDto {
	private UserDto user;
	private ClipDto clip;
	private Date time;
	private String content;
	
	//override
	public String forLog() {
		return "TODO";
	}
	
	
	//getter setter
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public ClipDto getClip() {
		return clip;
	}
	public void setClip(ClipDto clip) {
		this.clip = clip;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}

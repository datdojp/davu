package net.aihat.criteria;

import java.util.Date;

import net.aihat.dto.ClipDto;
import net.aihat.dto.UserDto;

public class ClipCommentSearchCriteria extends BaseCriteria {
	//criteria
	private UserDto user;
	private ClipDto clip;
	private Date time;
	private Date fromTime;
	
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	
}

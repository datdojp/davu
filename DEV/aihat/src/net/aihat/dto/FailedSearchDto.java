package net.aihat.dto;

import java.util.Date;

public class FailedSearchDto extends BaseUserCareDto {
	private String keyword;
	private Date dateTime;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String forLog() {
		String result = "";
		if(getId() != null) {
			result = result + "id:'" + getId() + "' ";
		}
		if(getKeyword() != null) {
			result = result + "keyword:'" + getKeyword() + "' ";
		}
		if(getUser() != null && getUser().getId() != null) {
			result = result + "userid:'" + getUser().getId() + "' ";
		}
		if(getDateTime() != null) {
			result = result + "datetime:'" + getDateTime() + "' ";
		}
		return result;
	}
	
}

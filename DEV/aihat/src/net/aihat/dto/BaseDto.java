package net.aihat.dto;

import java.io.Serializable;
import java.util.Date;

import net.aihat.utils.AihatConstants;

import org.jasypt.util.text.BasicTextEncryptor;

public abstract class BaseDto implements Serializable, Cloneable {
	private Integer id;
	private Date createdTime;
	private Date modifiedTime;
	private Boolean deleted;
	
	//transient fields
	private boolean selectedInDataTable;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	//utils
	public abstract String forLog();
	public boolean isNew() {
		return id == null;
	}
	
	//getters setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public boolean isSelectedInDataTable() {
		return selectedInDataTable;
	}
	public void setSelectedInDataTable(boolean selectedInDataTable) {
		this.selectedInDataTable = selectedInDataTable;
	}
	
}

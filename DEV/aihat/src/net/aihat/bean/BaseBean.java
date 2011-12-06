package net.aihat.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.aihat.message.AihatMessage;
import net.aihat.service.ConfigurationService;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

public abstract class BaseBean implements Serializable {
	protected Logger logger;
	protected Logger getLogger() {
		return logger;
	}
	
	private String savedPageURI;
	
	protected boolean isInitialized = false;
	public synchronized String init() {
		isInitialized = true;
		return null;
	}
	public abstract String getBeanName();
	
	//utils
	protected abstract void cleanAllFields();
	protected void storePageURI() {
		savedPageURI = BeanUtils.getPageURI();
	}
	
	//messages
	private List<AihatMessage> messages = new ArrayList<AihatMessage>();
	public void addMessage(String msgContent, String msgType) {
		messages.add(new AihatMessage(msgContent, msgType));
	}
	public void addWarningMessage(String msgContent) {
		addMessage(msgContent, AihatMessage.TYPE_WARNING);
	}
	public void addInfoMessage(String msgContent) {
		addMessage(msgContent, AihatMessage.TYPE_INFO);
	}
	public void addErrorMessage(String msgContent) {
		addMessage(msgContent, AihatMessage.TYPE_ERROR);
	}
	public List<AihatMessage> getMessages() {
		List<AihatMessage> result = messages;
		messages = new ArrayList<AihatMessage>();
		return result;
	}
	public AihatMessage getFirstMessage() {
		List<AihatMessage> tempMsg = getMessages();
		if(!AihatUtils.isEmpty(tempMsg)) {
			return tempMsg.get(0);
		} else {
			return null;
		}
	}
	public String getFirstMessageContent() {
		AihatMessage msg = getFirstMessage();
		if(msg != null) {
			return msg.getContent();
		} else {
			return null;
		}
	}
	
	//getter setter
	public String getSavedPageURI() {
		return savedPageURI;
	}

	public void setSavedPageURI(String savedPageURI) {
		this.savedPageURI = savedPageURI;
	}
	
	public boolean isInitialized() {
		return isInitialized;
	}
	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}
}

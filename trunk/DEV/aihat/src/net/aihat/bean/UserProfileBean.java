package net.aihat.bean;

import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import net.aihat.bean.client.BaseClientBean;
import net.aihat.bean.client.MyProfileBean;
import net.aihat.dto.UserDto;
import net.aihat.service.UserService;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class UserProfileBean extends BaseClientBean {
	public UserProfileBean() {
		logger = Logger.getLogger(UserProfileBean.class);
	}
	
	public String getBeanName() {
		return "userProfileBean";
	}
	protected List getCurrentDtoList() {
		//nothing to return
		return null;
	}
	private String mail;
	private String password;
	private String language;
	private UserDto profile;
	private UserService userService;
	
	public String init() {
		return null;
	}
	
	public String getLanguageBundle() {
		//set default language if it is not defined
		if(AihatUtils.isEmpty(language)) {
			language = AihatConstants.DEFAULT_LANGUAGE;
		}

		//english
		if(AihatConstants.LANGUAGE_ENGLISH.equals(language)) {
			return "net.aihat.bundle.msg-en";
		}
		
		//vietnammese
		if(AihatConstants.LANGUAGE_VIETNAMES.equals(language)) {
			return "net.aihat.bundle.msg-vi";
		}
		
		return null;
	}
	
	public synchronized void login(AjaxBehaviorEvent event) {
		try {
			try {
				profile = getUserService().login(mail, password);
			} catch (DataAccessException e) {
				if(mail != null) {
					logger.error("mail:'" + mail + "'", e);
				}
			}
			if(profile != null) {
				if(profile.getBlocked()) {
					addErrorMessage(BeanUtils.getBundleMsg("CM0020"));
					profile = null;
					return;
				}
				
				//update user's language if it is not defined
				if(AihatUtils.isEmpty(profile.getLanguage())) {
					try {
						profile.setLanguage(AihatConstants.DEFAULT_LANGUAGE);
						getUserService().updateLanguage(profile.getId(), AihatConstants.DEFAULT_LANGUAGE);
					} catch (DataAccessException e) {
						logger.error(profile.forLog(), e);
					}
				}
	
				language = profile.getLanguage();
				
				if(profile.isAdminOrMod()) {
					BeanUtils.navigate("adminHomePage");
				} else {
					MyProfileBean myProfileBean = (MyProfileBean) BeanUtils.getContextBean("myProfileBean");
					myProfileBean.init();
				}
				
				MyProfileBean myProfileBean = (MyProfileBean) BeanUtils.getContextBean("myProfileBean");
				myProfileBean.loadUserInfo();
			} else {
				addWarningMessage(BeanUtils.getBundleMsg("login_fail_message"));
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void logout(AjaxBehaviorEvent event) {
		try {
			profile = null;
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	//for admin to logout
	public synchronized String logoutForAdmin() {
		profile = null;
		BeanUtils.navigate("zentai");
		return null;
	}

	public boolean getLoggedIn() {
		return profile != null;
	}
	
	private void switchEnglish() {
		language = AihatConstants.LANGUAGE_ENGLISH;
		if(profile != null) {
			try {
				profile.setLanguage(AihatConstants.LANGUAGE_ENGLISH);
				getUserService().updateLanguage(profile.getId(), language);
			} catch (DataAccessException e) {
				logger.error(profile.forLog(), e);
			}
		}
		BeanUtils.reloadPage();
	}
	
	private void switchVietnamese() {
		language = AihatConstants.LANGUAGE_VIETNAMES;
		if(profile != null) {
			try {
				profile.setLanguage(AihatConstants.LANGUAGE_VIETNAMES);
				getUserService().updateLanguage(profile.getId(), language);
			} catch(DataAccessException e) {
				logger.error(profile.forLog(), e);
			}
		}
		BeanUtils.reloadPage();
	}
	
	protected void cleanAllFields() {
		mail = null;
		password = null;
		profile = null;
		language = null;
	}
	
	//getter setter
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public UserDto getProfile() {
		return profile;
	}
	public void setProfile(UserDto profile) {
		this.profile = profile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}

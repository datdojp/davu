package net.aihat.utils;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.aihat.bean.BaseBean;
import net.aihat.bean.UserProfileBean;
import net.aihat.bean.UtilsBean;
import net.aihat.dto.UserDto;
import org.apache.log4j.Logger;

public class BeanUtils {
	private static Logger logger = Logger.getLogger(BeanUtils.class);
	
	private static ResourceBundle bundleVn = ResourceBundle.getBundle("net.aihat.bundle.msg-vi");
	private static ResourceBundle bundleEn = ResourceBundle.getBundle("net.aihat.bundle.msg-en");
	private static ResourceBundle config = ResourceBundle.getBundle("configurations");
	
	public static void navigate(String outCome) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getApplication().getNavigationHandler().handleNavigation(context, null, outCome);
	}
	
	public static void redirect(String uri) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
		} catch (IOException e) {
			logger.error("Unable to redirect to URI: '" + uri + "'", e);
		}
		
	}
	
	public static void reloadPage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(getPageURI());
		} catch (IOException e) {
			logger.error("Unable to reload page", e);
		}
	}
	
//	public static BaseBean getContextBean(String beanName, Class beanClass) {
//		FacesContext context = FacesContext.getCurrentInstance();
//		return (BaseBean) beanClass.cast(context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", BaseBean.class));
//	}
	
	public static BaseBean getContextBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (BaseBean) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", BaseBean.class);
	}
	
	public static UserProfileBean getUserProfileBean() {
		return (UserProfileBean) getContextBean("userProfileBean");
	}
	
	public static Integer getLogginUserId() {
		UserDto user = getUserProfileBean().getProfile();
		Integer userId = null;
		if(user != null) {
			userId = user.getId();
		}
		return userId;
	}
	
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request;
	}
	
	public static ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}
	
	public static String getPageURI() {
		String url = getRequest().getRequestURI();
		return url;
	}
	
	//this function is to clear the stored singer-list for references
	public static void clearSingerMasterData() {
		UtilsBean utilsBean = (UtilsBean) BeanUtils.getContextBean("utilsBean");
		utilsBean.setTempSingerList(null);
	}
	
	//this function is to clear the stored composer-list for references
	public static void clearComposerMasterData() {
		UtilsBean utilsBean = (UtilsBean) BeanUtils.getContextBean("utilsBean");
		utilsBean.setTempComposerList(null);
	}
	
	public static String getCurrentLanguage() {
		UserDto user = getUserProfileBean().getProfile();
		String language = AihatConstants.DEFAULT_LANGUAGE;
		if(user != null && !AihatUtils.isEmpty(user.getLanguage())) {
			language = user.getLanguage();
		}
		return language;
	}
	
	public static String getBundleMsg(String key) {
		if(!AihatUtils.isEmpty(key)) {
			String lang = getCurrentLanguage();
			if(AihatConstants.LANGUAGE_VIETNAMES.equals(lang)) {
				return bundleVn.getString(key);
			} else if (AihatConstants.LANGUAGE_ENGLISH.equals(lang)) {
				return bundleEn.getString(key);
			} else {
				return "";
			}
		}
		return "";
	}
	
	public static String getConfig(String key) {
		if(!AihatUtils.isEmpty(key)) {
			return config.getString(key);
		}
		return "";
	}
}

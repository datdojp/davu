package net.aihat.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import net.aihat.bean.BaseBean;
import net.aihat.bean.UserProfileBean;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class AihatPhaseListener implements PhaseListener {
	private Logger logger = Logger.getLogger(AihatPhaseListener.class);
	private static String[] INIT_REQUIRED_CLIENT_BEANS = new String[] {
		"clipsBean",
		"detailBean",
		"featuredClipsBean",
		"myProfileBean",
		"zentaiBean",
		"manageFeaturedClipBean"
	};
	
	public void afterPhase(PhaseEvent event) {
		String uri = BeanUtils.getPageURI();
		//TODO big security hole
		if(!AihatUtils.isEmpty(uri)) {
			//admin authentication
			if(uri.indexOf("pages/admin") >= 0) {
				UserProfileBean userProfileBean = BeanUtils.getUserProfileBean();
				if(userProfileBean == null || userProfileBean.getProfile() == null || !userProfileBean.getProfile().isAdminOrMod()) {
					BeanUtils.navigate("adminAuthFailure");
				}
			}
		}
	}

	public void beforePhase(PhaseEvent event) {
		for(String aBeanName : INIT_REQUIRED_CLIENT_BEANS) {
			BaseBean bean = (BaseBean) BeanUtils.getContextBean(aBeanName);
			if(!bean.isInitialized()) {
				bean.init();
			}
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	protected Logger getLogger() {
		return logger;
	}

}

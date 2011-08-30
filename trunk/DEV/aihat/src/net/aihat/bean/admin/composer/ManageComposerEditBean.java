package net.aihat.bean.admin.composer;

import net.aihat.bean.admin.clip.ManageClipBean;
import net.aihat.bean.admin.clip.ManageClipUploadBean;
import net.aihat.dto.ComposerDto;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageComposerEditBean extends ComposerDetailBaseBean {
	public ManageComposerEditBean() {
		logger = Logger.getLogger(ManageComposerEditBean.class);
	}
	
	public String getBeanName() {
		return "manageComposerEditBean";
	}
	
	private Integer composerId;

	public synchronized String init() {
		cleanAllFields();

		if(AihatUtils.isValidId(composerId)) {
			try {
				setComposer(getComposerService().getComposer(composerId));
			} catch (DataAccessException e) {
				ComposerDto logComposer = new ComposerDto();
				logComposer.setId(composerId);
				logger.error(logComposer.forLog(), e);
				addErrorMessage("Selected composer is no longer existing.");
			}
			return "manageComposerEditBean-init-complete";
		} else {
			BeanUtils.reloadPage();
			return null;	
		}
	}

	public synchronized String update() {
		try {
			storeComposer();
			
			//clear page 's data
			composerId = null;
			cleanAllFields();
		} catch (DataAccessException e) {
			logger.error(getComposer().forLog(), e);
			addErrorMessage("Unable to update composer. Please try again later.");
			BeanUtils.reloadPage();
			return "";
		}
		return "return-manage-composer";
	}

	public synchronized String searchAllClips() {
		ManageClipBean manageClipBean = (ManageClipBean) BeanUtils.getContextBean("manageClipBean");
		return manageClipBean.searchReference(AihatConstants.REF_TYPE_COMPOSER, composerId);
	}
	
	public synchronized String uploadClips() {
		ManageClipUploadBean manageClipUploadBean = (ManageClipUploadBean) BeanUtils.getContextBean("manageClipUploadBean");
		return manageClipUploadBean.uploadReference(AihatConstants.REF_TYPE_COMPOSER, composerId);
	}

	//getter setter
	public Integer getComposerId() {
		return composerId;
	}

	public void setComposerId(Integer composerId) {
		this.composerId = composerId;
	}
}

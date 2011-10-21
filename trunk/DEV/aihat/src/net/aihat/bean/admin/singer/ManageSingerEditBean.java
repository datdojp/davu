package net.aihat.bean.admin.singer;

import net.aihat.bean.admin.clip.ManageClipBean;
import net.aihat.bean.admin.clip.ManageClipUploadBean;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageSingerEditBean extends SingerDetailBaseBean {
	public ManageSingerEditBean() {
		logger = Logger.getLogger(ManageSingerEditBean.class);
	}
	
	public String getBeanName() {
		return "manageSingerEditBean";
	}
	private Integer singerId;

	public synchronized String init() {
		super.init();
		
		cleanAllFields();

		if(AihatUtils.isValidId(singerId)) {
			try {
				setSinger(getSingerService().getSinger(singerId));

				//check if the singer belongs to the current user
				UserDto profile = BeanUtils.getUserProfileBean().getProfile();
				if(!profile.getAdmin() && !profile.getId().equals(getSinger().getUser().getId())) {
					ManageSingerBean manageSingerBean = (ManageSingerBean) BeanUtils.getContextBean("manageSingerBean");
					manageSingerBean.addErrorMessage("You can't edit singer that belongs to other administrator/moderator");
					return null;
				}
			} catch (DataAccessException e) {
				SingerDto logSinger = new SingerDto();
				logSinger.setId(singerId);
				logger.error(logSinger.forLog(), e);
				addErrorMessage("Selected singer is no longer existing.");
			}
			return "manageSingerEditBean-init-complete";
		} else {
			BeanUtils.reloadPage();
			return null;	
		}
	}

	public synchronized String update() {
		try {
			storeSinger();
			
			//clear page 's data
			singerId = null;
			cleanAllFields();
		} catch (DataAccessException e) {
			logger.error(getSinger().forLog(), e);
			addErrorMessage("Unable to update singer. Please try again later.");
			BeanUtils.reloadPage();
			return "";
		}
		return "return-manage-singer";
	}

	public synchronized String searchAllClips() {
		ManageClipBean manageClipBean = (ManageClipBean) BeanUtils.getContextBean("manageClipBean");
		return manageClipBean.searchReference(AihatConstants.REF_TYPE_SINGER, singerId);
	}
	
	public synchronized String uploadClips() {
		ManageClipUploadBean manageClipUploadBean = (ManageClipUploadBean) BeanUtils.getContextBean("manageClipUploadBean");
		return manageClipUploadBean.uploadReference(AihatConstants.REF_TYPE_SINGER, singerId);
	}
	
	//getter setter
	public Integer getSingerId() {
		return singerId;
	}

	public void setSingerId(Integer singerId) {
		this.singerId = singerId;
	}
}

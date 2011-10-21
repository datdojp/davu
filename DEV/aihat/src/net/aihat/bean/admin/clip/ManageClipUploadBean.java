package net.aihat.bean.admin.clip;

import net.aihat.dto.UserDto;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageClipUploadBean extends ClipDetailBaseBean {
	public ManageClipUploadBean() {
		logger = Logger.getLogger(ManageClipUploadBean.class);
	}
	
	public String getBeanName() {
		return "manageClipUploadBean";
	}
	
	public synchronized String upload() {
		storeRelatedDtos();
		
		try {
			getClip().setUser(new UserDto());
			getClip().getUser().setId(BeanUtils.getUserProfileBean().getProfile().getId());
			getClipService().uploadClip(getClip());
		
			getClip().setId(null);
			getClip().setTitle(null);
			getClip().setLink(null);
			getClip().setOfficial(null);
			
			addInfoMessage("Upload successfully.");
		} catch (DataAccessException e) {
			logger.error(getClip().forLog(), e);
			addErrorMessage("Unable to upload clip. Please try again.");
		}
		BeanUtils.reloadPage();
		return null;
	}
	
	//this function is called by other bean, NOT FROM GUI
	public String uploadReference(String referenceField, Object value) {
		cleanAllFields();
		if(AihatConstants.REF_TYPE_SINGER.equals(referenceField)) {
			setSingerId_1((Integer)value);
		} else if(AihatConstants.REF_TYPE_COMPOSER.equals(referenceField)) {
			setComposerId_1((Integer)value);
		} else if(AihatConstants.REF_TYPE_GENRE.equals(referenceField)) {
			setGenreId_1((Integer) value);
		}
		return "adminManageClipUpload";
	}
	
	public synchronized String clear() {
		cleanAllFields();
		BeanUtils.reloadPage();
		return null;
	}
}
package net.aihat.bean.admin.clip;

import net.aihat.dto.ClipDto;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageClipEditBean extends ClipDetailBaseBean {
	public ManageClipEditBean() {
		logger = Logger.getLogger(ManageClipEditBean.class);
	}
	
	public String getBeanName() {
		return "manageClipEditBean";
	}
	
	private Integer clipId;
	
	public synchronized String init() {
		cleanAllFields();
		
		if(AihatUtils.isValidId(clipId)) {
			try {
				setClip(getClipService().getClip(clipId));
				
				//set singers info
				setSingerId_1(getClip().getSingers().get(0).getId());
				if(getClip().getSingers().size() >= 2) {
					setSingerId_2(getClip().getSingers().get(1).getId()); 
				}
				if(getClip().getSingers().size() >= 3) {
					setSingerId_3(getClip().getSingers().get(2).getId());
				}
				
				//set composers info
				setComposerId_1(getClip().getComposers().get(0).getId());
				if(getClip().getComposers().size() >= 2) {
					setComposerId_2(getClip().getComposers().get(1).getId());
				}
				if(getClip().getComposers().size() >= 3) {
					setComposerId_3(getClip().getComposers().get(2).getId());
				}
				
				//set genres info
				setGenreId_1(getClip().getGenres().get(0).getId());
				if(getClip().getGenres().size() >= 2) {
					setGenreId_2(getClip().getGenres().get(1).getId());
				}
				if(getClip().getGenres().size() >= 3) {
					setGenreId_3(getClip().getGenres().get(2).getId());
				}
			} catch (DataAccessException e) {
				ClipDto logClip = new ClipDto();
				logClip.setId(clipId);
				logger.error(logClip.forLog(), e);
				addErrorMessage("Selected clip is no longer existing.");
			}
			return "manageClipEditBean-init-complete";
		} else {
			BeanUtils.reloadPage();
			return null;	
		}
	}
	
	public synchronized String update() {
		storeRelatedDtos();
		
		try {
			getClipService().updateClip(getClip());
			
			//clear page 's data
			clipId = null;
			cleanAllFields();
		} catch (DataAccessException e) {
			logger.error(getClip().forLog(), e);
			addErrorMessage("Unable to update clip.Please try again later.");
			BeanUtils.reloadPage();
			return "";
		}
		return "return-manage-clip";
	}
	
	public Integer getClipId() {
		return clipId;
	}

	public void setClipId(Integer clipId) {
		this.clipId = clipId;
	}
}

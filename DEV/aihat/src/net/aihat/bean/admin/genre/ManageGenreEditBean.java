package net.aihat.bean.admin.genre;

import net.aihat.bean.admin.clip.ManageClipBean;
import net.aihat.bean.admin.clip.ManageClipUploadBean;
import net.aihat.dto.GenreDto;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageGenreEditBean extends GenreDetailBaseBean {
	public ManageGenreEditBean() {
		logger = Logger.getLogger(ManageGenreEditBean.class);
	}
	
	public String getBeanName() {
		return "manageGenreEditBean";
	}
	
	private Integer genreId;

	public synchronized String init() {
		super.init();
		
		cleanAllFields();

		if(AihatUtils.isValidId(genreId)) {
			try {
				setGenre(getGenreService().getGenre(genreId));
			} catch (DataAccessException e) {
				GenreDto logGenre = new GenreDto();
				logGenre.setId(genreId);
				logger.error(logGenre.forLog(), e);
				addErrorMessage("Selected genre is no longer existing.");
			}
			return "manageGenreEditBean-init-complete";
		} else {
			BeanUtils.reloadPage();
			return null;	
		}
	}

	public synchronized String update() {
		try {
			storeGenre();
			
			//clear page 's data
			genreId = null;
			cleanAllFields();
		} catch (DataAccessException e) {
			logger.error(getGenre().forLog(), e);
			addErrorMessage("Unable to update genre. Please try again later.");
			BeanUtils.reloadPage();
			return "";
		}
		return "return-manage-genre";
	}

	public synchronized String searchAllClips() {
		ManageClipBean manageClipBean = (ManageClipBean) BeanUtils.getContextBean("manageClipBean");
		return manageClipBean.searchReference(AihatConstants.REF_TYPE_GENRE, genreId);
	}
	
	public synchronized String uploadClips() {
		ManageClipUploadBean manageClipUploadBean = (ManageClipUploadBean) BeanUtils.getContextBean("manageClipUploadBean");
		return manageClipUploadBean.uploadReference(AihatConstants.REF_TYPE_GENRE, genreId);
	}
	
	//getter setter
	public Integer getGenreId() {
		return genreId;
	}

	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
}

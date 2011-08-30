package net.aihat.bean.admin.clip;

import java.util.List;

import net.aihat.bean.admin.DataTableCareBaseBean;
import net.aihat.dto.ClipDto;
import net.aihat.service.ClipService;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageClipBean extends DataTableCareBaseBean {
	public ManageClipBean() {
		logger = Logger.getLogger(ManageClipBean.class);
	}
	public String getBeanName() {
		return "manageClipBean";
	}
	
	private ClipService clipService;
	private String titleCriterion;
	private Integer singerIdCriterion;
	private Integer composerIdCriterion;
	private Integer genreIdCriterion;
	private String userMailCriterion;
	private int officialCriterion;
	private boolean hasDuplicateCriterion;
	
	public String init() {
		return null;
	}
	
	public synchronized String search() {
		if(!AihatUtils.isValidId(getIdCriterion())) {
			setIdCriterion(null);
		}
		setSearchResults(getSearchService().searchClips(getIdCriterion(), titleCriterion, singerIdCriterion, composerIdCriterion, genreIdCriterion, null, userMailCriterion, null, getOfficial(), hasDuplicateCriterion, getSortCriterion(), null, false, null, null).getResults());
		BeanUtils.reloadPage();
		return null;
	}
	
	private Boolean getOfficial() {
		Boolean official = null;//officialCriterion = 0 --> both
		if(officialCriterion == 1) {
			official = true;//-->official
		} else if(officialCriterion == 2){
			official = false;//-->NOT official
		}
		
		return official;
	}
	
	//this function is called by other bean, NOT FROM GUI
	public String searchReference(String referenceField, Object value) {
		cleanAllFields();
		if(AihatConstants.REF_TYPE_SINGER.equals(referenceField)) {
			singerIdCriterion = (Integer) value;
		} else if(AihatConstants.REF_TYPE_COMPOSER.equals(referenceField)) {
			composerIdCriterion = (Integer) value;
		} else if(AihatConstants.REF_TYPE_GENRE.equals(referenceField)) {
			genreIdCriterion = (Integer) value;
		} else if(AihatConstants.REF_TYPE_USER.equals(referenceField)) {
			userMailCriterion = (String) value;
		}
		
		setSearchResults(getSearchService().searchClips(getIdCriterion(), titleCriterion, singerIdCriterion, composerIdCriterion, genreIdCriterion, null, userMailCriterion, null, getOfficial(), hasDuplicateCriterion, getSortCriterion(), null, false, null, null).getResults());
		return "adminManageClip";
	}
	
	public synchronized String clear() {
		cleanAllFields();
		BeanUtils.reloadPage();
		return null;
	}
	
	protected void cleanAllFields() {
		setIdCriterion(null);
		titleCriterion = null;
		singerIdCriterion = null;
		composerIdCriterion = null;
		genreIdCriterion = null;
		userMailCriterion = null;
		officialCriterion = 0;
		hasDuplicateCriterion = false; 
		setSearchResults(null);		
	}
	
	public synchronized String delete() {
		List<ClipDto> deletedClips = getSelectedObjects();
		
		if(!AihatUtils.isEmpty(deletedClips)) {
			try {
				getClipService().deleteClips(deletedClips);
				addInfoMessage("Deletion completed successfully.");
			} catch (DataAccessException e) {
				logger.error(AihatUtils.getLogForCollection(deletedClips), e);
				addErrorMessage("Unable to delete clips. Please try again later");
			}
		} else {
			addErrorMessage("Please select clips to delete.");
		}
		
		search();
		return null;
	}
	
	//getter setter
	public void setTitleCriterion(String title) {
		this.titleCriterion = title;
	}

	public String getTitleCriterion() {
		return titleCriterion;
	}

	public Integer getSingerIdCriterion() {
		return singerIdCriterion;
	}

	public void setSingerIdCriterion(Integer singerId) {
		this.singerIdCriterion = singerId;
	}

	public Integer getComposerIdCriterion() {
		return composerIdCriterion;
	}

	public void setComposerIdCriterion(Integer composerId) {
		this.composerIdCriterion = composerId;
	}

	public Integer getGenreIdCriterion() {
		return genreIdCriterion;
	}

	public void setGenreIdCriterion(Integer genreId) {
		this.genreIdCriterion = genreId;
	}

	public String getUserMailCriterion() {
		return userMailCriterion;
	}

	public void setUserMailCriterion(String userMail) {
		this.userMailCriterion = userMail;
	}

	public ClipService getClipService() {
		return clipService;
	}

	public void setClipService(ClipService clipService) {
		this.clipService = clipService;
	}

	public int getOfficialCriterion() {
		return officialCriterion;
	}

	public void setOfficialCriterion(int officialCriterion) {
		this.officialCriterion = officialCriterion;
	}

	public boolean isHasDuplicateCriterion() {
		return hasDuplicateCriterion;
	}

	public void setHasDuplicateCriterion(boolean hasDuplicateCriterion) {
		this.hasDuplicateCriterion = hasDuplicateCriterion;
	}
	
	
}

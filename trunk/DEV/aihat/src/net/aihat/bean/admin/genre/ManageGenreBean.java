package net.aihat.bean.admin.genre;

import java.util.List;
import net.aihat.bean.admin.DataTableCareBaseBean;
import net.aihat.dto.GenreDto;
import net.aihat.service.GenreService;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageGenreBean extends DataTableCareBaseBean {
	public ManageGenreBean() {
		logger = Logger.getLogger(ManageGenreBean.class);
	}
	
	public String getBeanName() {
		return "manageGenreBean";
	}
	
	private String nameCriterion;
	private String userMailCriterion;
	private GenreService genreService;
	
	public synchronized String init() {
		return null;
	}
	
	public synchronized String search() {
		if(!AihatUtils.isValidId(getIdCriterion())) {
			setIdCriterion(null);
		}
		setSearchResults(getSearchService().searchGenres(getIdCriterion(), nameCriterion, AihatConstants.LANGUAGE_ENGLISH, userMailCriterion, getSortCriterion(), null, false).getResults());
		BeanUtils.reloadPage();
		return null;
	}

	//this function is called by other bean, NOT FROM GUI
	public String searchReference(String referenceField, Object value) {
		cleanAllFields();
		if(AihatConstants.REF_TYPE_USER.equals(referenceField)) {
			userMailCriterion = (String) value;
		}
		setSearchResults(getSearchService().searchGenres(getIdCriterion(), nameCriterion, AihatConstants.LANGUAGE_ENGLISH, userMailCriterion, getSortCriterion(), null, false).getResults());
		return "adminManageGenre";
	}
	
	public synchronized String clear() {
		cleanAllFields();
		BeanUtils.reloadPage();
		return null;
	}

	protected void cleanAllFields() {
		setIdCriterion(null);
		nameCriterion = null;
		userMailCriterion = null;
		setSearchResults(null);
	}

	
	public synchronized String delete() {
		List<GenreDto> deleteGenres = getSelectedObjects();
		
		if(!AihatUtils.isEmpty(deleteGenres)) {
			try {
				genreService.deleteGenres(deleteGenres);
				addInfoMessage("Deletion completed successfully.");
			} catch (DataAccessException e) {
				logger.error(AihatUtils.getLogForCollection(deleteGenres), e);
				addErrorMessage("Unable to delete genres. Please try again later");
			}
		} else {
			addErrorMessage("Please select genre to delete.");
		}
		
		search();
		return null;
	}

	//getter setter
	public String getNameCriterion() {
		return nameCriterion;
	}

	public void setNameCriterion(String nameCriterion) {
		this.nameCriterion = nameCriterion;
	}

	public GenreService getGenreService() {
		return genreService;
	}

	public void setGenreService(GenreService genreService) {
		this.genreService = genreService;
	}

	public String getUserMailCriterion() {
		return userMailCriterion;
	}

	public void setUserMailCriterion(String userMailCriterion) {
		this.userMailCriterion = userMailCriterion;
	}
	
}

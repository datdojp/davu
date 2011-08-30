package net.aihat.bean.admin.composer;

import java.util.List;

import net.aihat.bean.admin.DataTableCareBaseBean;
import net.aihat.dto.ComposerDto;
import net.aihat.service.ComposerService;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageComposerBean extends DataTableCareBaseBean {
	public ManageComposerBean() {
		logger = Logger.getLogger(ManageComposerBean.class);
	}
	
	public String getBeanName() {
		return "manageComposerBean";
	}
	
	private String nameCriterion;
	private String userMailCriterion;
	private ComposerService composerService;
	
	public String init() {
		return null;
	}
	
	public synchronized String search() {
		if(!AihatUtils.isValidId(getIdCriterion())) {
			setIdCriterion(null);
		}
		setSearchResults(getSearchService().searchComposers(getIdCriterion(), nameCriterion, userMailCriterion, getSortCriterion(), null, false).getResults());
		BeanUtils.reloadPage();
		return null;
	}

	//this function is called by other bean, NOT FROM GUI
	public String searchReference(String referenceField, Object value) {
		cleanAllFields();
		if(AihatConstants.REF_TYPE_USER.equals(referenceField)) {
			userMailCriterion = (String) value;
		}
		setSearchResults(getSearchService().searchComposers(getIdCriterion(), nameCriterion, userMailCriterion, getSortCriterion(), null, false).getResults());
		return "adminManageComposer";
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
		List<ComposerDto> deleteComposers = getSelectedObjects();
		
		if(!AihatUtils.isEmpty(deleteComposers)) {
			try {
				composerService.deleteComposers(deleteComposers);
				addInfoMessage("Deletion completed successfully.");
				BeanUtils.clearComposerMasterData();
			} catch (DataAccessException e) {
				logger.error(AihatUtils.getLogForCollection(deleteComposers), e);
				addErrorMessage("Unable to delete composers. Please try again later");
			}
		} else {
			addErrorMessage("Please select composer to delete.");
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

	public ComposerService getComposerService() {
		return composerService;
	}

	public void setComposerService(ComposerService composerService) {
		this.composerService = composerService;
	}

	public String getUserMailCriterion() {
		return userMailCriterion;
	}

	public void setUserMailCriterion(String userMailCriterion) {
		this.userMailCriterion = userMailCriterion;
	}

}

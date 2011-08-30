package net.aihat.bean.admin.singer;

import java.util.List;

import net.aihat.bean.admin.DataTableCareBaseBean;
import net.aihat.dto.SingerDto;
import net.aihat.service.SingerService;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageSingerBean extends DataTableCareBaseBean {
	public ManageSingerBean() {
		logger = Logger.getLogger(ManageSingerBean.class);
	}
	
	public String getBeanName() {
		return "manageSingerBean";
	}
	
	private String nameCriterion;
	private String userMailCriterion;
	private SingerService singerService;
	
	public synchronized String init() {
		return null;
	}
	
	public synchronized String search() {
		if(!AihatUtils.isValidId(getIdCriterion())) {
			setIdCriterion(null);
		}
		setSearchResults(getSearchService().searchSingers(getIdCriterion(), nameCriterion, userMailCriterion, null, getSortCriterion(), null, false, null).getResults());
		BeanUtils.reloadPage();
		return null;
	}

	//this function is called by other bean, NOT FROM GUI
	public String searchReference(String referenceField, Object value) {
		cleanAllFields();
		if(AihatConstants.REF_TYPE_USER.equals(referenceField)) {
			userMailCriterion = (String) value;
		}
		setSearchResults(getSearchService().searchSingers(getIdCriterion(), nameCriterion, userMailCriterion, null, getSortCriterion(), null, false, null).getResults());
		return "adminManageSinger";
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

	
	public String delete() {
		List<SingerDto> deleteSingers = getSelectedObjects();
		
		if(!AihatUtils.isEmpty(deleteSingers)) {
			try {
				singerService.deleteSingers(deleteSingers);
				addInfoMessage("Deletion completed successfully.");
				BeanUtils.clearSingerMasterData();
			} catch (DataAccessException e) {
				logger.error(AihatUtils.getLogForCollection(deleteSingers), e);
				addErrorMessage("Unable to delete singers. Please try again later");
			}
		} else {
			addErrorMessage("Please select singer to delete.");
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

	public SingerService getSingerService() {
		return singerService;
	}

	public void setSingerService(SingerService singerService) {
		this.singerService = singerService;
	}

	public String getUserMailCriterion() {
		return userMailCriterion;
	}

	public void setUserMailCriterion(String userMailCriterion) {
		this.userMailCriterion = userMailCriterion;
	}
}

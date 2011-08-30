package net.aihat.bean.admin;

import java.util.ArrayList;
import java.util.List;

import net.aihat.bean.BaseBean;
import net.aihat.criteria.SortCriterion;
import net.aihat.dto.BaseDto;
import net.aihat.service.SearchService;
import net.aihat.utils.AihatUtils;

public abstract class DataTableCareBaseBean extends BaseBean {
	private Integer idCriterion;
	private SortCriterion sortCriterion = new SortCriterion("id", "asc");
	private SearchService searchService;
	private List searchResults;
	
	//utils
	public int getSearchResultsSize() {
		if(!AihatUtils.isEmpty(getSearchResults())) {
			return getSearchResults().size();
		} else {
			return 0;
		}
	}
	protected List getSelectedObjects() {
		List objectsToBeDeleted = new ArrayList();
		if(!AihatUtils.isEmpty(searchResults)) {
			for(BaseDto anObject : (List<BaseDto>)searchResults) {
				if(anObject.isSelectedInDataTable()) {
					objectsToBeDeleted.add(anObject);
				}
			}
		}
		return objectsToBeDeleted;
	}
	
	
	//abstract methods
	public abstract String search();
	public abstract String clear();
	public abstract String delete();
	public abstract String searchReference(String referenceField, Object value);
	
	//getter & setter
	public SortCriterion getSortCriterion() {
		return sortCriterion;
	}
	public void setSortCriterion(SortCriterion sortCriterion) {
		this.sortCriterion = sortCriterion;
	}
	public void setSearchResults(List searchResults) {
		this.searchResults = searchResults;
	}
	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	public List getSearchResults() {
		return searchResults;
	}

	public Integer getIdCriterion() {
		return idCriterion;
	}

	public void setIdCriterion(Integer idCriterion) {
		this.idCriterion = idCriterion;
	}

}

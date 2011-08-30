package net.aihat.bean.admin.failedsearch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import net.aihat.bean.BaseBean;
import net.aihat.criteria.SortCriterion;
import net.aihat.dto.FailedSearchDto;
import net.aihat.service.FailedSearchService;
import net.aihat.utils.AihatUtils;

public class ManageFailedSearchBean extends BaseBean {
	public ManageFailedSearchBean() {
		logger = Logger.getLogger(ManageFailedSearchBean.class);
	}
	
	/**
	 * INHERITED METHODS
	 */
	public synchronized String init() {return null;}
	public String getBeanName() {
		return "managedFailedSearch";
	}
	protected void cleanAllFields() {}
	
	/**
	 * PRIVATE PROPERTIES
	 */
	private List<FailedSearchDto> failedSearchs = new ArrayList<FailedSearchDto>();
	private SortCriterion sortCriterion = new SortCriterion("dateTime", SortCriterion.ORDER_DESCENDING);
	private String fromDateYmd = SDF.format(new Date());
	private String toDateYmd = SDF.format(new Date());
	private FailedSearchService failedSearchService;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	private static final String MANAGEDFAILEDSEARCH = "pages/admin/ManagedFailedSearch.jsf";
	
	public synchronized String search() {
		Date fromDate = null;
		Date toDate = null;
		
		if(!AihatUtils.isEmpty(fromDateYmd)) {
			try {
				fromDate = SDF.parse(fromDateYmd);
			} catch (ParseException e) {
				addErrorMessage("Invalid FROM date");
				return MANAGEDFAILEDSEARCH;
			}
		}
		
		if(!AihatUtils.isEmpty(toDateYmd)) {
			try {
				toDate = SDF.parse(toDateYmd);
			} catch (ParseException e) {
				addErrorMessage("Invalid TO date");
				return MANAGEDFAILEDSEARCH;
			}
		}
		
		failedSearchs = failedSearchService.search(fromDate, toDate, sortCriterion);
		return MANAGEDFAILEDSEARCH;
	}
	
	//getter setter
	public List<FailedSearchDto> getFailedSearchs() {
		return failedSearchs;
	}
	public void setFailedSearchs(List<FailedSearchDto> failedSearchs) {
		this.failedSearchs = failedSearchs;
	}
	public String getFromDateYmd() {
		return fromDateYmd;
	}
	public void setFromDateYmd(String fromDateYmd) {
		this.fromDateYmd = fromDateYmd;
	}
	public String getToDateYmd() {
		return toDateYmd;
	}
	public void setToDateYmd(String toDateYmd) {
		this.toDateYmd = toDateYmd;
	}
	public FailedSearchService getFailedSearchService() {
		return failedSearchService;
	}
	public void setFailedSearchService(FailedSearchService failedSearchService) {
		this.failedSearchService = failedSearchService;
	}
	public SortCriterion getSortCriterion() {
		return sortCriterion;
	}
	public void setSortCriterion(SortCriterion sortCriterion) {
		this.sortCriterion = sortCriterion;
	}
}

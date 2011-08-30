package net.aihat.bean.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.AjaxBehaviorEvent;

import net.aihat.bean.PagingBean;
import net.aihat.criteria.PagingCriterion;

public abstract class MultiTabPagingBean extends BaseClientBean implements PagingBean {
	protected String displayTab;
	
	/**
	 * TABS 'S CONTENT
	 */
	protected Map<String, PagingCriterion> tabPagingMap = new HashMap<String, PagingCriterion>();
	protected Map<String, List> tabDataMap = new HashMap<String, List>();
	protected Map<String, Long> tabDataCountMap = new HashMap<String, Long>();
	protected abstract void initTabPagingMap();
	protected abstract void initTabDataMap();
	protected abstract void initTabDataCountMap();
	public String init() {
		super.init();
		initTabPagingMap();
		initTabDataMap();
		initTabDataCountMap();
		return null;
	}
	/**
	 * END OF TABS 'S CONTENT
	 */
	
	/**
	 * UTILS 
	 */
	protected PagingCriterion getCurrentPagingCriterion() {
		return tabPagingMap.get(displayTab);
	}
	protected long getCurrentCounting() {
		return tabDataCountMap.get(displayTab);
	}
	protected List getCurrentData() {
		return tabDataMap.get(displayTab);
	}
	
	protected abstract void reselectCurrentTab();
	protected abstract long updateCurrentCounting();
	protected List getCurrentDtoList() {
		return tabDataMap.get(displayTab);
	}
	
	/**
	 * PAGING 
	 */
	public synchronized void nextPage(AjaxBehaviorEvent e) {
		try {
			long n = updateCurrentCounting();
			long upperLimit = n - (n % getCurrentPagingCriterion().getRowCount());
			if(upperLimit == n) {
				upperLimit -= getCurrentPagingCriterion().getRowCount();
			}
			getCurrentPagingCriterion().setOffset(
					Math.min(upperLimit, getCurrentPagingCriterion().getOffset() + getCurrentPagingCriterion().getRowCount())
				);
			reselectCurrentTab();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void previousPage(AjaxBehaviorEvent e) {
		try {
			long n = updateCurrentCounting();
			getCurrentPagingCriterion().setOffset(
				Math.max(0, getCurrentPagingCriterion().getOffset() - getCurrentPagingCriterion().getRowCount())
			);
			reselectCurrentTab();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void firstPage(AjaxBehaviorEvent e) {
		try {
			long n = updateCurrentCounting();
			getCurrentPagingCriterion().setOffset(0l);
			reselectCurrentTab();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void lastPage(AjaxBehaviorEvent e) {
		try {
			long n = updateCurrentCounting();
			long upperLimit = n - (n % getCurrentPagingCriterion().getRowCount());
			if(upperLimit == n) {
				upperLimit -= getCurrentPagingCriterion().getRowCount();
			}
			getCurrentPagingCriterion().setOffset(upperLimit);
			reselectCurrentTab();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public String getPageRange() {
		if(getCurrentPagingCriterion() != null) {
			long start = getCurrentPagingCriterion().getOffset() + 1;
			long end = Math.min(getCurrentCounting(),
					getCurrentPagingCriterion().getOffset() + getCurrentPagingCriterion().getRowCount());
			return "" + start + "-" + end + "/" + getCurrentCounting();
		} else {
			return "";
		}
	}
	/**
	 * END OF PAGING 
	 */
	
	//getter setter
	public String getDisplayTab() {
		return displayTab;
	}

	public void setDisplayTab(String displayTab) {
		this.displayTab = displayTab;
	}
	public Map<String, PagingCriterion> getTabPagingMap() {
		return tabPagingMap;
	}
	public void setTabPagingMap(Map<String, PagingCriterion> tabPagingMap) {
		this.tabPagingMap = tabPagingMap;
	}
	public Map<String, List> getTabDataMap() {
		return tabDataMap;
	}
	public void setTabDataMap(Map<String, List> tabDataMap) {
		this.tabDataMap = tabDataMap;
	}
	public Map<String, Long> getTabDataCountMap() {
		return tabDataCountMap;
	}
	public void setTabDataCountMap(Map<String, Long> tabDataCountMap) {
		this.tabDataCountMap = tabDataCountMap;
	}
	
}

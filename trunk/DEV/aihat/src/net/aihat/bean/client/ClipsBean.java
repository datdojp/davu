package net.aihat.bean.client;

import java.util.ArrayList;

import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;

import net.aihat.criteria.PagingCriterion;
import net.aihat.criteria.SortCriterion;
import net.aihat.dto.UserDto;
import net.aihat.utils.BeanUtils;

public class ClipsBean extends MultiTabPagingBean {
	public ClipsBean() {
		logger = Logger.getLogger(ClipsBean.class);
		displayTab = CLIPS_TAB;
	}
	
	public String getBeanName() {
		return "clipsBean";
	}

	private String searchKeyword;
	
	private boolean focusSearchKeyword;
	public boolean isFocusSearchKeyword() {
		boolean temp = focusSearchKeyword;
		focusSearchKeyword = false;
		return temp;
	}

	private final String CLIPS_TAB = "clips";
	private final String SINGERS_TAB = "singers";
	private final String COMPOSERS_TAB = "composers";
	private final String PLAYLISTS_TAB = "playlists";
	private final String GENRES_TAB = "genres";
	private final String[] ALL_TABS =  new String[] {
		CLIPS_TAB, SINGERS_TAB, COMPOSERS_TAB, PLAYLISTS_TAB, GENRES_TAB
	};
	
	/**
	 * INIT
	 */
	public synchronized String init() {
		super.init();
//		reselectCurrentTab();TODO
		return null;
	}
	
	protected void initTabPagingMap() {
		tabPagingMap.put(CLIPS_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
		tabPagingMap.put(SINGERS_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
		tabPagingMap.put(COMPOSERS_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
		tabPagingMap.put(PLAYLISTS_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
		tabPagingMap.put(GENRES_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
	}

	protected void initTabDataMap() {
		tabDataMap.put(CLIPS_TAB, new ArrayList());
		tabDataMap.put(SINGERS_TAB, new ArrayList());
		tabDataMap.put(COMPOSERS_TAB, new ArrayList());
		tabDataMap.put(PLAYLISTS_TAB, new ArrayList());
		tabDataMap.put(GENRES_TAB, new ArrayList());
	}
	protected void initTabDataCountMap() {
		tabDataCountMap.put(CLIPS_TAB, 0l);
		tabDataCountMap.put(SINGERS_TAB, 0l);
		tabDataCountMap.put(COMPOSERS_TAB, 0l);
		tabDataCountMap.put(PLAYLISTS_TAB, 0l);
		tabDataCountMap.put(GENRES_TAB, 0l);
	}
	/**
	 * END OF INIT
	 */
	
	public synchronized void search(AjaxBehaviorEvent e) {
		try {
			init();
			
			updateClipsCounting();
			updateSingersCounting();
			updateComposersCounting();
			updatePlaylistsCounting();
			updateGenresCounting();
			
			//check if there is any result found,
			//if no result is found, log failed-search
			boolean isResultFound = false;
			for(String aTab : ALL_TABS) {
				if(tabDataCountMap.get(aTab) > 0) {
					isResultFound = true;
					break;
				}
			}
			if(!isResultFound) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0012"));
				if(!BeanUtils.getUserProfileBean().getLoggedIn() || !BeanUtils.getUserProfileBean().getProfile().isAdminOrMod()) {
					getFailedSearchService().logFailedSearch(searchKeyword, BeanUtils.getLogginUserId());	
				}
			}
			
			if(tabDataCountMap.get(CLIPS_TAB) > 0) {
				selectClipsTab(e);
			} else if(tabDataCountMap.get(SINGERS_TAB) > 0) {
				selectSingersTab(e);
			} else if(tabDataCountMap.get(COMPOSERS_TAB) > 0) {
				selectComposersTab(e);
			} else if(tabDataCountMap.get(PLAYLISTS_TAB) > 0) {
				selectPlaylistsTab(e);
			} else if(tabDataCountMap.get(GENRES_TAB) > 0) {
				selectGenresTab(e);
			} else {
				selectClipsTab(e);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void removeSearch(AjaxBehaviorEvent e) {
		searchKeyword = null;
		search(e);
		focusSearchKeyword = true;
		
		ZentaiBean zentaiBean = (ZentaiBean) BeanUtils.getContextBean("zentaiBean");
		zentaiBean.setPageName("Clips");
	}
	
	/**
	 * COUNTING 
	 */
	private long updateClipsCounting() {
		long n = getSearchService().searchClips(null, searchKeyword, null, null, null, null, null, null, null, null, null, null, true, null, null).getnResults(); 
		tabDataCountMap.put(CLIPS_TAB, n);
		return n;
	}
	private long updateSingersCounting() {
		long n = getSearchService().searchSingers(null, searchKeyword, null, null, null, null, true, null).getnResults();
		tabDataCountMap.put(SINGERS_TAB, n);
		return n;
	}
	private long updateComposersCounting() {
		long n = getSearchService().searchComposers(null, searchKeyword, null, null, null, true).getnResults(); 
		tabDataCountMap.put(COMPOSERS_TAB, n);
		return n;
	}
	private long updatePlaylistsCounting() {
		long n = getSearchService().searchPlaylists(null, searchKeyword, null, null, null, true).getnResults();
		tabDataCountMap.put(PLAYLISTS_TAB, n);
		return n;
	}
	private long updateGenresCounting() {
		long n = getSearchService().searchGenres(null, searchKeyword, BeanUtils.getCurrentLanguage(), null, null, null, true).getnResults();
		tabDataCountMap.put(GENRES_TAB, n);
		return n;
	}
	protected long updateCurrentCounting() {
		if(CLIPS_TAB.equals(displayTab)) {
			return updateClipsCounting();
		} else if(SINGERS_TAB.equals(displayTab)) {
			return updateSingersCounting();
		} else if(COMPOSERS_TAB.equals(displayTab)) {
			return updateComposersCounting();
		} else if(PLAYLISTS_TAB.equals(displayTab)) {
			return updatePlaylistsCounting();
		} else if(GENRES_TAB.equals(displayTab)) {
			return updateGenresCounting();
		}
		return -1;
	}
	/**
	 * END OF COUNTING 
	 */
	
	/**
	 * SELECT TAB
	 */
	public synchronized void selectClipsTab(AjaxBehaviorEvent e) {
		try {
			displayTab = CLIPS_TAB;
			
			UserDto user = BeanUtils.getUserProfileBean().getProfile();
			Integer userId = null;
			if(user != null) {
				userId = user.getId();
			}
			
			tabDataMap.put(displayTab, getSearchService().searchClips(null, searchKeyword, null, null, null, null, null, userId, null, null,
					new SortCriterion("nViews", SortCriterion.ORDER_DESCENDING), 
					tabPagingMap.get(CLIPS_TAB), false, null, null).getResults());
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public synchronized void selectSingersTab(AjaxBehaviorEvent e) {
		try {
			displayTab = SINGERS_TAB;
			
			UserDto user = BeanUtils.getUserProfileBean().getProfile();
			Integer userId = null;
			if(user != null) {
				userId = user.getId();
			}
			
			tabDataMap.put(displayTab, getSearchService().searchSingers(null, searchKeyword, null, userId,
					new SortCriterion("name", SortCriterion.ORDER_ASCENDING),
					tabPagingMap.get(SINGERS_TAB), false, null).getResults());
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public synchronized void selectComposersTab(AjaxBehaviorEvent e) {
		try {
			displayTab = COMPOSERS_TAB;
			tabDataMap.put(displayTab, getSearchService().searchComposers(null, searchKeyword, null,
					new SortCriterion("name", SortCriterion.ORDER_ASCENDING),
					tabPagingMap.get(COMPOSERS_TAB), false).getResults());
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public synchronized void selectPlaylistsTab(AjaxBehaviorEvent e) {
		try {
			displayTab = PLAYLISTS_TAB;
			tabDataMap.put(displayTab, getSearchService().searchPlaylists(null, searchKeyword, null,
					new SortCriterion("name", SortCriterion.ORDER_ASCENDING),
					tabPagingMap.get(PLAYLISTS_TAB), false).getResults());
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public synchronized void selectGenresTab(AjaxBehaviorEvent e) {
		try {
			displayTab = GENRES_TAB;
			tabDataMap.put(displayTab, getSearchService().searchGenres(null, searchKeyword, BeanUtils.getCurrentLanguage(), null,
					new SortCriterion("nameVi", SortCriterion.ORDER_ASCENDING),
					null, false).getResults());
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	protected void reselectCurrentTab() {
		if(CLIPS_TAB.equals(displayTab)) {
			selectClipsTab(null);
		} else if(SINGERS_TAB.equals(displayTab)) {
			selectSingersTab(null);
		} else if(COMPOSERS_TAB.equals(displayTab)) {
			selectComposersTab(null);
		} else if(PLAYLISTS_TAB.equals(displayTab)) {
			selectPlaylistsTab(null);
		} else if(GENRES_TAB.equals(displayTab)) {
			selectGenresTab(null);
		}
	}
	/**
	 * END OF SELECT TAB 
	 */

	//getter setter
	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
}

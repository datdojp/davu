package net.aihat.bean.admin.homepage;

import java.util.List;

import org.springframework.dao.DataAccessException;

import net.aihat.bean.BaseBean;
import net.aihat.dto.HomepageTabDto;
import net.aihat.service.HomepageTabService;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

public class ManageHomepageBean extends BaseBean {
	//data
	private List<HomepageTabDto> allTabs;
	private Integer id;
	private Integer order;
	private String titleVi;
	private String titleEn;
	private String genres;
	private	String topSingers;
	private String topPlaylists;
	private String recommendedClips;
	private String topUploaders;
	
	private String action;
	
	//service
	private HomepageTabService homepageTabService;

	//override
	public String getBeanName() {
		return "manageHomepageBean";
	}
	protected void cleanAllFields() {
		id = null;
		order = null;
		titleVi = null;
		titleEn = null;
		genres = null;
		topSingers = null;
		topPlaylists = null;
		recommendedClips = null;
		topUploaders = null;
	}

	//action
	public synchronized String init() {
		super.init();
		reloadTabs();
		return "/pages/admin/ManageHomepage";
	}
	private void reloadTabs() {
		allTabs = homepageTabService.getAllHomepageTab();
	}
	public synchronized String create() {
		cleanAllFields();
		action = "create";
		return null;
	}
	public synchronized String edit() {
		Integer tabId = Integer.parseInt(BeanUtils.getRequest().getParameter("tabId"));
		HomepageTabDto tab = (HomepageTabDto) AihatUtils.getDtoFromList(tabId, allTabs);
		id = tabId;
		order = tab.getOrder();
		titleVi = tab.getTitleVi();
		titleEn = tab.getTitleEn();
		genres = tab.getGenres();
		topSingers = tab.getTopSingers();
		topPlaylists = tab.getTopPlaylists();
		recommendedClips = tab.getRecommendedClips();
		topUploaders = tab.getTopUploaders();
		
		action = "edit";
		
		return null;
	}
	public synchronized String delete() {
		Integer tabId = Integer.parseInt(BeanUtils.getRequest().getParameter("tabId"));
		try {
			getHomepageTabService().deleteHomepageTab(tabId);
		} catch (DataAccessException ex) {
			logger.error(ex);
			addErrorMessage("Unable to delete tab. Please try again later.");
		}
		init();
		
		action = "delete";
		
		return null;
	}
	
	public synchronized String saveOrUpdate() {
		getHomepageTabService().createOrUpdateHomepageTab(id, order, titleVi, titleEn, genres, topSingers,
				topPlaylists, recommendedClips, topUploaders);
		action = null;
		reloadTabs();
		return null;
	}
	
	public synchronized String cancel() {
		cleanAllFields();
		action = null;
		return null;
	}
	
	//getter setter
	public List<HomepageTabDto> getAllTabs() {
		return allTabs;
	}

	public void setAllTabs(List<HomepageTabDto> allTabs) {
		this.allTabs = allTabs;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getTitleVi() {
		return titleVi;
	}

	public void setTitleVi(String titleVi) {
		this.titleVi = titleVi;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getTopSingers() {
		return topSingers;
	}

	public void setTopSingers(String topSingers) {
		this.topSingers = topSingers;
	}

	public String getTopPlaylists() {
		return topPlaylists;
	}

	public void setTopPlaylists(String topPlaylists) {
		this.topPlaylists = topPlaylists;
	}

	public String getRecommendedClips() {
		return recommendedClips;
	}

	public void setRecommendedClips(String recommendedClips) {
		this.recommendedClips = recommendedClips;
	}

	public String getTopUploaders() {
		return topUploaders;
	}

	public void setTopUploaders(String topUploaders) {
		this.topUploaders = topUploaders;
	}

	public HomepageTabService getHomepageTabService() {
		return homepageTabService;
	}

	public void setHomepageTabService(HomepageTabService homepageTabService) {
		this.homepageTabService = homepageTabService;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}

package net.aihat.bean.client;

import java.util.ArrayList;
import java.util.List;
import net.aihat.bean.client.ClipsBean;
import javax.faces.event.AjaxBehaviorEvent;

import net.aihat.dto.ClipDto;
import net.aihat.dto.HomepageTabDto;
import net.aihat.service.HomepageTabService;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class HomepageBean extends BaseClientBean {
	private HomepageTabDto currentTab;
	private List<HomepageTabDto> allTabs;
	
	public HomepageBean() {
		logger = Logger.getLogger(HomepageBean.class);
		init();
	}
	
	public synchronized String init() {
		super.init();
		return null;
	}
	
	public int getTopPlaylistsPerRow() {
		if( currentTab != null && !AihatUtils.isEmpty(currentTab.getTopPlaylists()) ) {
			return (currentTab.getListTopPlaylists().size()+1) / 2;
		} else {
			return 1; 
		}
	}
	
	public void setHomepageTabService(HomepageTabService homepageTabService) {
		super.setHomepageTabService(homepageTabService);
		allTabs = getHomepageTabService().getAllHomepageTab();
		if(!AihatUtils.isEmpty(allTabs)) {
			currentTab = allTabs.get(0);
			getHomepageTabService().loadHomepageTabContent(currentTab);
		}
	}
	
	protected List getCurrentDtoList() {
		List<ClipDto> results = new ArrayList<ClipDto>();
		results.addAll(currentTab.getListTopViewClips());
		results.addAll(currentTab.getListRecommendedClips());
		results.addAll(currentTab.getListNewUploadedClips());
		return results;
	}

	public String getBeanName() {
		return "homepageBean";
	}
	
	public void switchTab(AjaxBehaviorEvent e) {
		Integer tabId = Integer.parseInt(BeanUtils.getRequest().getParameter("tabId"));
		currentTab = (HomepageTabDto) AihatUtils.getDtoFromList(tabId, allTabs);
		if(!currentTab.isLoaded()) {
			getHomepageTabService().loadHomepageTabContent(currentTab);
		}
	}
	
	public synchronized void showAllClipsOfGenres(AjaxBehaviorEvent e) {
		ClipsBean clipsBean = (ClipsBean) BeanUtils.getContextBean("clipsBean");
		clipsBean.searchAllClipsOfGenres(currentTab.getListGenres());
	}

	public HomepageTabDto getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(HomepageTabDto currentTab) {
		this.currentTab = currentTab;
	}

	public List<HomepageTabDto> getAllTabs() {
		return allTabs;
	}

	public void setAllTabs(List<HomepageTabDto> allTabs) {
		this.allTabs = allTabs;
	}
	
}

package net.aihat.bean.client;

import java.util.ArrayList;
import java.util.List;

import net.aihat.dto.ClipDto;
import net.aihat.dto.HomepageTabDto;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class HomepageBean extends BaseClientBean {
	private HomepageTabDto currentTab;
	private List<HomepageTabDto> allTabs;
	
	public HomepageBean() {
		logger = Logger.getLogger(HomepageBean.class);
	}
	
	public synchronized String init() {
		super.init();
		allTabs = getHomepageTabService().getAllHomepageTab();
		if(!AihatUtils.isEmpty(allTabs)) {
			currentTab = allTabs.get(0);
			getHomepageTabService().loadHomepageTabContent(currentTab);
		}
		return null;
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
	
	public void switchTab() {
		Integer tabId = Integer.parseInt(BeanUtils.getRequest().getParameter("tabId"));
		currentTab = (HomepageTabDto) AihatUtils.getDtoFromList(tabId, allTabs);
		if(!currentTab.isLoaded()) {
			getHomepageTabService().loadHomepageTabContent(currentTab);
		}
	}
}

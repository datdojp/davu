package net.aihat.bean.client;

import java.util.ArrayList;
import java.util.List;

import net.aihat.dto.ClipDto;
import net.aihat.dto.HomePageTabDto;

import org.apache.log4j.Logger;

public class HomepageBean extends BaseClientBean {
	private HomePageTabDto currentTab;
	private List<HomePageTabDto> allTabs;
	
	public HomepageBean() {
		logger = Logger.getLogger(HomepageBean.class);
	}
	public synchronized String init() {
		super.init();
		allTabs = getHomepageTabService().getAllHomepageTab();
		currentTab = allTabs.get(0);
		getHomepageTabService().loadHomepageTabContent(currentTab);
		return null;
	}
	
	protected List getCurrentDtoList() {
		List<ClipDto> results = new ArrayList<ClipDto>();
		results.addAll(currentTab.getlist)
	}

	public String getBeanName() {
		return "homepageBean";
	}
}

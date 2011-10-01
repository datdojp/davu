package net.aihat.bean.admin.clip;

import java.util.List;

import net.aihat.bean.BaseBean;
import net.aihat.dto.ClipDto;
import net.aihat.dto.FeaturedClipDto;
import net.aihat.service.FeaturedClipService;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class ManageFeaturedClipBean extends BaseBean {
	public ManageFeaturedClipBean() {
		logger = Logger.getLogger(ManageFeaturedClipBean.class);
		newFeaturedClip.setClip(new ClipDto());
	}
	public String getBeanName() {
		return "manageFeaturedClipBean";
	}
	
	//service
	private FeaturedClipService featuredClipService;
	
	//data
	private List<FeaturedClipDto> featuredClips;
	private FeaturedClipDto newFeaturedClip = new FeaturedClipDto();
	
	//action
	public String init() {
		featuredClips = getFeaturedClipService().getAllFeaturedClips();
		return "/pages/admin/ManageFeaturedClip";
	}
	public String insert() {
		//check empty
		if(newFeaturedClip.getClip().getId() == null || newFeaturedClip.getOrder() == null) {
			addErrorMessage("Please input clip id and order.");
			return null;
		}
		
		//check duplicate clipid and order
		for(FeaturedClipDto aFC : featuredClips) {
			if(aFC.getClip().getId().equals(newFeaturedClip.getClip().getId())
					|| aFC.getOrder().equals(newFeaturedClip.getOrder())) {
				addErrorMessage("Clip ID or Order is existing in the list.");
				return null;
			}
		}
		
		getFeaturedClipService().addFeaturedClip(newFeaturedClip.getClip().getId(), newFeaturedClip.getOrder());
		addInfoMessage("Inserted successfully.");
		init();
		return null;
	}
	public String update() {
		getFeaturedClipService().updateFeaturedClips(featuredClips);
		addInfoMessage("Updated successfully.");
		init();
		return null;
	}
	public String delete() {
		String strDeletedClipId = BeanUtils.getRequest().getParameter("deletedClipId");
		Integer deletedClipId = Integer.parseInt(strDeletedClipId);
		getFeaturedClipService().deleteFeaturedClip(deletedClipId);
		addInfoMessage("Added successfully.");
		init();
		return null;
	}
	protected void cleanAllFields() {
		//do nothing
	}
	
	//getter setter
	public FeaturedClipService getFeaturedClipService() {
		return featuredClipService;
	}
	public void setFeaturedClipService(FeaturedClipService featuredClipService) {
		this.featuredClipService = featuredClipService;
	}
	public List<FeaturedClipDto> getFeaturedClips() {
		return featuredClips;
	}
	public void setFeaturedClips(List<FeaturedClipDto> featuredClips) {
		this.featuredClips = featuredClips;
	}
	public FeaturedClipDto getNewFeaturedClip() {
		return newFeaturedClip;
	}
	public void setNewFeaturedClip(FeaturedClipDto newFeaturedClip) {
		this.newFeaturedClip = newFeaturedClip;
	}
	
}

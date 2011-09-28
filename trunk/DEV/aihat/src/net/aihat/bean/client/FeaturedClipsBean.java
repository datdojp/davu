package net.aihat.bean.client;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import net.aihat.dto.ClipDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class FeaturedClipsBean extends BaseClientBean {
	private static final String PERMANENT_LINK_PARAM_CLIPID = "perlink_clipid";
	private static final String PERMANENT_LINK_PARAM_PLAYLISTID = "perlink_playlistid";
	private static final String PARAM_CLIPIDS = "clipids";
	
	public FeaturedClipsBean() {
		logger = Logger.getLogger(FeaturedClipsBean.class);
	}
	
	public String getBeanName() {
		return "featuredClipsBean";
	}
	
	public String init() {
		super.init();
		return null;
	}

	private List<ClipDto> featuredClips = new ArrayList<ClipDto>();
	private String currentEmbeddedLink;
	private double currentPlaybackPos;
	private void resetCurrent() {
		currentEmbeddedLink = null;
		currentPlaybackPos = 0;
	}
	
	public synchronized void syncCurrentPlayback(AjaxBehaviorEvent e) {
	}
	
	public List<ClipDto> getFeaturedClips() {
		//check if this is a permanent link
		String strClipIds = BeanUtils.getRequest().getParameter(PARAM_CLIPIDS);
		String strPermClipId = BeanUtils.getRequest().getParameter(PERMANENT_LINK_PARAM_CLIPID);
		String strPermPlaylistId = BeanUtils.getRequest().getParameter(PERMANENT_LINK_PARAM_PLAYLISTID);
		if(!AihatUtils.isEmpty(strClipIds)) {
			String[] splitted = strClipIds.split(",");
			featuredClips = new ArrayList<ClipDto>();
			for(String anStrId : splitted) {
				Integer anId = Integer.parseInt(anStrId);
				featuredClips.add(getClipService().getClip(anId));
			}
			addClipView(featuredClips);
		}
		if(!AihatUtils.isEmpty(strPermClipId)) {
			Integer clipId = Integer.parseInt(strPermClipId);
			ClipDto clipDto = getClipService().getClip(clipId);
			featuredClips = new ArrayList<ClipDto>();
			featuredClips.add(clipDto);
			addClipView(clipDto);
		} else if(!AihatUtils.isEmpty(strPermPlaylistId)) {
			Integer playlistId = Integer.parseInt(strPermPlaylistId);
			featuredClips = getSearchService().searchClips(null, null, null, null, null, playlistId, null, 
					BeanUtils.getLogginUserId(), null, null, null, null, false, null, null).getResults();
			addClipView(featuredClips);
		} else {
			if(AihatUtils.isEmpty(featuredClips)) {
				featuredClips = getClipService().getFeaturedClips(getConfigurationService().getnFeaturedClips());
			}
		}
		
		return featuredClips;
	}
	
	protected List getCurrentDtoList() {
		return featuredClips;
	}
	
	/**
	 * PLAY & ADD 
	 */
//	public synchronized void play(AjaxBehaviorEvent e) {
//		try {
//			List selectedClips = AihatUtils.getSelectedObjects(getReferenceBean().getCurrentDtoList()); 
//			if(!AihatUtils.isEmpty(selectedClips)) {
//				featuredClips = selectedClips;
//				resetCurrent();
//				addClipView(selectedClips);
//			}
//		} catch (Throwable err) {
//			handleGeneralError(err);
//		}
//	}
	
	public synchronized String play() {
		List selectedClips = AihatUtils.getSelectedObjects(getReferenceBean().getCurrentDtoList()); 
		if(!AihatUtils.isEmpty(selectedClips)) {
			resetCurrent();
			addClipView(selectedClips);
			BeanUtils.redirect(generateRedirectLink(selectedClips));
		}
		return null;
	}
	public synchronized void add(AjaxBehaviorEvent e) {
		try {
			List<ClipDto> selectedClips = AihatUtils.getSelectedObjects(getReferenceBean().getCurrentDtoList());
			if(!AihatUtils.isEmpty(selectedClips)) {
				for(ClipDto aClip : selectedClips) {
					if(AihatUtils.getDtoIndex(aClip, featuredClips) < 0) {
						featuredClips.add(aClip);
						addClipView(aClip);
					}
				}
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}

	public void playOne(AjaxBehaviorEvent e) {
		try {
			Integer selectedClipId = Integer.parseInt(BeanUtils.getRequest().getParameter("selectedClipId"));
			ClipDto selectedClip = getClipService().getClip(selectedClipId);
			featuredClips = new ArrayList<ClipDto>();
			featuredClips.add(selectedClip);
			resetCurrent();
			addClipView(selectedClip);
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void addOne(AjaxBehaviorEvent e) {
		try {
			Integer selectedClipId = Integer.parseInt(BeanUtils.getRequest().getParameter("selectedClipId"));
			ClipDto selectedClip = getClipService().getClip(selectedClipId);
			if(AihatUtils.getDtoIndex(selectedClip, featuredClips) < 0) {
				featuredClips.add(selectedClip);
				addClipView(selectedClip);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void removeOne(AjaxBehaviorEvent e) {
		try {
			Integer selectedClipId = Integer.parseInt(BeanUtils.getRequest().getParameter("selectedClipId"));
			int index = AihatUtils.getDtoIndex(selectedClipId, featuredClips);
			ClipDto selectedClip = (ClipDto) AihatUtils.getDtoFromList(selectedClipId, featuredClips);
			if(selectedClip.getEmbeddedLink().equals(currentEmbeddedLink)) {
				resetCurrent();
			}
			featuredClips.remove(index);
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void removeAll(AjaxBehaviorEvent e) {
		try {
			featuredClips = new ArrayList<ClipDto>();
			resetCurrent();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	private void addClipView(ClipDto clip) {
		UserDto profile = BeanUtils.getUserProfileBean().getProfile();
		if(!BeanUtils.availableInSession(clip.getId())) {
			getClipService().addView(clip.getId(), profile!=null?profile.getId():null);
			BeanUtils.pushToSession(clip.getId());
		}
	}
	private void addClipView(List<ClipDto> clips) {
		List<Integer> clipIds = new ArrayList<Integer>();
		for(ClipDto aClip : clips) {
			if(!BeanUtils.availableInSession(aClip.getId())) {
				clipIds.add(aClip.getId());
				BeanUtils.pushToSession(aClip.getId());
			}
		}
		UserDto profile = BeanUtils.getUserProfileBean().getProfile();
		getClipService().addView(clipIds, profile!=null?profile.getId():null);
	}
	private String generateRedirectLink(List<ClipDto> clips) {
		String result = "";
		for(ClipDto aClip : clips) {
			if(!AihatUtils.isEmpty(result)) {
				result = result + ",";
			}
			result = result + aClip.getId();
		}
		return BeanUtils.getConfig("server") + "pages/client/Zentai.jsf?" + PARAM_CLIPIDS + "=" + result;
	}
	/**
	 * END OF PLAY & ADD 
	 */
	
	//getter setter
	public void setFeaturedClips(List<ClipDto> featuredClips) {
		this.featuredClips = featuredClips;
	}

	public String getCurrentEmbeddedLink() {
		return currentEmbeddedLink;
	}

	public void setCurrentEmbeddedLink(String currentEmbeddedLink) {
		this.currentEmbeddedLink = currentEmbeddedLink;
	}

	public double getCurrentPlaybackPos() {
		return currentPlaybackPos;
	}

	public void setCurrentPlaybackPos(double currentPlaybackPos) {
		this.currentPlaybackPos = currentPlaybackPos;
	}

	
}
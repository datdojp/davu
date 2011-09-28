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
		String strClipIds = BeanUtils.getRequest().getParameter(PARAM_CLIPIDS);
		String strPermClipId = BeanUtils.getRequest().getParameter(PERMANENT_LINK_PARAM_CLIPID);
		String strPermPlaylistId = BeanUtils.getRequest().getParameter(PERMANENT_LINK_PARAM_PLAYLISTID);
		if(!AihatUtils.isEmpty(strClipIds)) {
			String[] splitted = strClipIds.split(",");
			List<Integer> clipIds = new ArrayList<Integer>();
			for(String anStrId : splitted) {
				clipIds.add(Integer.parseInt(anStrId));
			}
			featuredClips = getClipService().getClips(clipIds);
			addClipView(featuredClips);
		} else if(!AihatUtils.isEmpty(strPermClipId)) {
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
	public synchronized String play() {
		try {
			List selectedClips = AihatUtils.getSelectedObjects(getReferenceBean().getCurrentDtoList()); 
			if(!AihatUtils.isEmpty(selectedClips)) {
				resetCurrent();
				BeanUtils.redirect(generateRedirectLink(selectedClips));
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
		return null;
	}
	
	public synchronized String add() {
		try {
			List<ClipDto> selectedClips = AihatUtils.getSelectedObjects(getReferenceBean().getCurrentDtoList());
			if(!AihatUtils.isEmpty(selectedClips)) {
				for(ClipDto aClip : selectedClips) {
					if(AihatUtils.getDtoIndex(aClip, featuredClips) < 0) {
						featuredClips.add(aClip);
					}
				}
				BeanUtils.redirect(generateRedirectLink(featuredClips));
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
		return null;
	}

	public synchronized String playOne() {
		try {
			Integer selectedClipId = Integer.parseInt(BeanUtils.getRequest().getParameter("selectedClipId"));
			ClipDto clip = new ClipDto();
			clip.setId(selectedClipId);
			List<ClipDto> clipList = new ArrayList<ClipDto>();
			clipList.add(clip);
			resetCurrent();
			BeanUtils.redirect(generateRedirectLink(clipList));
		} catch (Throwable err) {
			handleGeneralError(err);
		}
		return null;
	}
	
	public synchronized String addOne() {
		try {
			Integer selectedClipId = Integer.parseInt(BeanUtils.getRequest().getParameter("selectedClipId"));
			ClipDto selectedClip = new ClipDto();
			selectedClip.setId(selectedClipId);
			if(AihatUtils.getDtoIndex(selectedClip, featuredClips) < 0) {
				featuredClips.add(selectedClip);
			}
			BeanUtils.redirect(generateRedirectLink(featuredClips));
		} catch (Throwable err) {
			handleGeneralError(err);
		}
		return null;
	}
	
	public synchronized String removeOne() {
		try {
			Integer selectedClipId = Integer.parseInt(BeanUtils.getRequest().getParameter("selectedClipId"));
			int index = AihatUtils.getDtoIndex(selectedClipId, featuredClips);
			ClipDto selectedClip = (ClipDto) AihatUtils.getDtoFromList(selectedClipId, featuredClips);
			if(selectedClip.getEmbeddedLink().equals(currentEmbeddedLink)) {
				resetCurrent();
			}
			featuredClips.remove(index);
			BeanUtils.redirect(generateRedirectLink(featuredClips));
		} catch (Throwable err) {
			handleGeneralError(err);
		}
		return null;
	}
	
	public synchronized String removeAll() {
		try {
			featuredClips = new ArrayList<ClipDto>();
			resetCurrent();
			BeanUtils.redirect(BeanUtils.getConfig("server"));
		} catch (Throwable err) {
			handleGeneralError(err);
		}
		return null;
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
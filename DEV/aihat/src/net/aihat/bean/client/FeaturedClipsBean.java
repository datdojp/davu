package net.aihat.bean.client;

import java.util.ArrayList;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import net.aihat.dto.ClipCommentDto;
import net.aihat.dto.ClipDto;
import net.aihat.dto.FeaturedClipDto;
import net.aihat.dto.UserDto;
import net.aihat.service.ConfigurationService;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import net.aihat.dto.PlaylistDto;

public class FeaturedClipsBean extends BaseClientBean {
	private static final String PERMANENT_LINK_PARAM_CLIPID = "perlink_clipid";
	private static final String PERMANENT_LINK_PARAM_PLAYLISTID = "perlink_playlistid";
	private static final String PARAM_CLIPIDS = "clipids";
	
	public FeaturedClipsBean() {
		logger = Logger.getLogger(FeaturedClipsBean.class);
		init();
	}
	
	public String getBeanName() {
		return "featuredClipsBean";
	}

	private List<ClipDto> featuredClips = new ArrayList<ClipDto>();
	private String currentEmbeddedLink;
	private double currentPlaybackPos;
	private Integer currentClipId;
	private ClipDto currentClip;
	private List<ClipDto> currentRelatedClips;
	private void resetCurrent() {
		currentClipId = null;
		currentEmbeddedLink = null;
		currentPlaybackPos = 0;
		currentClip = null;
	}
	
	public synchronized void syncAtLoad(AjaxBehaviorEvent e) {
		currentClip = (ClipDto) AihatUtils.getDtoFromList(currentClipId, featuredClips);
		currentPlaybackPos = 0;
		currentRelatedClips = getClipService().getRelatedClips(currentClipId, 
				Integer.parseInt(BeanUtils.getConfig("client.nRelatedClips")), BeanUtils.getLogginUserId());
		loadComments();
	}
	public synchronized void syncPlaybackPos(AjaxBehaviorEvent e) {
	}
	
	private String getPageTitleForClips(List<ClipDto> clips) {
		StringBuilder result = new StringBuilder();
		if(!AihatUtils.isEmpty(clips)) {
			result.append(clips.get(0).getTitle());
			if(clips.size() >= 2) {
				result.append(" | ").append(clips.get(1).getTitle());
			}
			if(clips.size() >= 3) {
				result.append(" | ").append(clips.get(2).getTitle());
			}
			if(clips.size() >= 4) {
				result.append("...");
			}
		}
		return result.toString();
	}
	
	public List<ClipDto> getFeaturedClips() {
		String strClipIds = BeanUtils.getRequest().getParameter(PARAM_CLIPIDS);
		String strPermClipId = BeanUtils.getRequest().getParameter(PERMANENT_LINK_PARAM_CLIPID);
		String strPermPlaylistId = BeanUtils.getRequest().getParameter(PERMANENT_LINK_PARAM_PLAYLISTID);
		
		ZentaiBean zentaiBean = (ZentaiBean) BeanUtils.getContextBean("zentaiBean");
		
		if(!AihatUtils.isEmpty(strClipIds)) {
			String[] splitted = strClipIds.split(",");
			List<Integer> clipIds = new ArrayList<Integer>();
			for(String anStrId : splitted) {
				clipIds.add(Integer.parseInt(anStrId));
			}
			featuredClips = getClipService().getClips(clipIds);
			addClipView(featuredClips);
			
			//set the page title
			zentaiBean.setPageTitle(getPageTitleForClips(featuredClips));
		} else if(!AihatUtils.isEmpty(strPermClipId)) {
			Integer clipId = Integer.parseInt(strPermClipId);
			ClipDto clipDto = getClipService().getClip(clipId);
			featuredClips = new ArrayList<ClipDto>();
			featuredClips.add(clipDto);
			addClipView(clipDto);
			
			//set the page title
			if(clipDto != null) {
				zentaiBean.setPageTitle(clipDto.getTitle());
			}
		} else if(!AihatUtils.isEmpty(strPermPlaylistId)) {
			Integer playlistId = Integer.parseInt(strPermPlaylistId);
			featuredClips = getSearchService().searchClips(null, null, null, null, null, playlistId, null, 
					BeanUtils.getLogginUserId(), null, null, null, null, false, null, null,null,false).getResults();
			addClipView(featuredClips);
			
			//set the page title
			PlaylistDto playlist = getPlaylistService().getPlaylist(playlistId);
			if(playlist != null) {
				zentaiBean.setPageTitle(playlist.getName());
			}
		} else {
			if(AihatUtils.isEmpty(featuredClips)) {
				List<FeaturedClipDto> fcs = getFeaturedClipService().getAllFeaturedClips();
				if(!AihatUtils.isEmpty(fcs)) {
					//GET FROM SETTING
					featuredClips = new ArrayList<ClipDto>();
					for(FeaturedClipDto aFC : fcs) {
						featuredClips.add(aFC.getClip());
					}
				} else {
					//DEFAULT
					featuredClips = getClipService().getFeaturedClips(ConfigurationService.getnFeaturedClips());
				}
				
				//set the page title
				zentaiBean.setPageTitle(BeanUtils.getBundleMsg("aihat_title"));
			}
		}
		
		if(currentClipId == null && !AihatUtils.isEmpty(featuredClips)) {
			currentClip = featuredClips.get(0);
			currentClipId = currentClip.getId();
			currentEmbeddedLink = currentClip.getEmbeddedLink();
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
			} else {
				addErrorMessage(BeanUtils.getBundleMsg("please_select_clip"));
				BeanUtils.redirect(generateRedirectLink(featuredClips));
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
		return null;
	}
	
	//only for detail page
	public synchronized String playAll() {
		DetailBean detailBean = (DetailBean) BeanUtils.getContextBean("detailBean");
		if(detailBean.getReferenceDtosCount() > Long.parseLong(BeanUtils.getConfig("client.maxClipsForPlayAll"))) {
			addErrorMessage(StringUtils.replace(
					BeanUtils.getBundleMsg("too_many_clips_selected_to_play"), "{0}", BeanUtils.getConfig("client.maxClipsForPlayAll")));
			return null;
		}
		
		resetCurrent();
		BeanUtils.redirect(generateRedirectLink(detailBean.getAllReferenceDtos()));
		
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
			} else {
				addErrorMessage(BeanUtils.getBundleMsg("please_select_clip"));
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
	
	/**
	 * COMMENT
	 */
	private List<ClipCommentDto> currentClipComments = new ArrayList<ClipCommentDto>();
	//TODO improve smart-comment-pole
//	private Date latestCommentUpdatedAt;
	private String commentContent;
	public synchronized void addComment(AjaxBehaviorEvent event) {
		if(commentContent == null || AihatUtils.isEmpty(commentContent.trim())) {
			addErrorMessage(BeanUtils.getBundleMsg("error_empty_comment"));
			return;
		}
		Integer userId;
		if(BeanUtils.getUserProfileBean().getLoggedIn()) {
			userId = BeanUtils.getLogginUserId();
		} else {
			userId = Integer.parseInt(BeanUtils.getConfig("guestId"));
		}
		ClipCommentDto clipComment = getClipCommentService().addNewComment(userId, currentClipId, commentContent);
		currentClipComments.add(0, clipComment);
		commentContent = "";
	}
	public synchronized void deleteComment(AjaxBehaviorEvent event) {
		int commentId = Integer.parseInt(BeanUtils.getRequest().getParameter("commentId"));
		getClipCommentService().deleteComment(commentId);
		ClipCommentDto comment = (ClipCommentDto) AihatUtils.getDtoFromList(commentId, currentClipComments);
		currentClipComments.remove(comment);
	}
	
	private synchronized void loadComments() {
		currentClipComments = getClipCommentService().getAllCommentOfClip(currentClipId);
		//TODO improve smart-comment-pole
//		if(!AihatUtils.isEmpty(currentClipComments)) {
//			latestCommentUpdatedAt = currentClipComments.get(0).getTime();
//		}
	}
	public synchronized void refreshComments(AjaxBehaviorEvent event) {
		//TODO improve smart-comment-pole
//		if(AihatUtils.isEmpty(currentClipComments)) {
//			loadComments();
//		} else {
//			currentClipComments.addAll(getClipCommentService().getCommentOfClipAfter(currentClipId, latestCommentUpdatedAt));
//		}
		loadComments();
	}
	
	/**
	 * END OF COMMENT
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

	public Integer getCurrentClipId() {
		return currentClipId;
	}

	public void setCurrentClipId(Integer currentClipId) {
		this.currentClipId = currentClipId;
	}

	public List<ClipCommentDto> getCurrentClipComments() {
		return currentClipComments;
	}

	public void setCurrentClipComments(List<ClipCommentDto> currentClipComments) {
		this.currentClipComments = currentClipComments;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public ClipDto getCurrentClip() {
		return currentClip;
	}

	public void setCurrentClip(ClipDto currentClip) {
		this.currentClip = currentClip;
	}

	public List<ClipDto> getCurrentRelatedClips() {
		return currentRelatedClips;
	}

	public void setCurrentRelatedClips(List<ClipDto> currentRelatedClips) {
		this.currentRelatedClips = currentRelatedClips;
	}
	
}
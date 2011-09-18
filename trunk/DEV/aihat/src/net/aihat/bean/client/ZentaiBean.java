package net.aihat.bean.client;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import net.aihat.dto.ClipDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class ZentaiBean extends BaseClientBean {
	public ZentaiBean() {
		logger = Logger.getLogger(ZentaiBean.class);
	}
	
	public String getBeanName() {
		return "zentaiBean";
	}
	protected List getCurrentDtoList() {
		return null;
	}
	public String init() {
		super.init();
		return null;
	}
	
	//current pagename, used for synchronous loading
	private String pageName;
	public String getPageName() {
		String result = pageName;
		pageName = null;
		return result;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
	/**
	 * POPUP MYPLAYLISTS
	 */
	private String newPlaylistTitle;
	private String newPlaylistDescription;
	private String selectedPlaylistIds;
	private List<PlaylistDto> myPlaylists;
	private void loadMyPlaylists() {
		UserDto profile = BeanUtils.getUserProfileBean().getProfile();
		if(profile == null || profile.getId() == null) {
			myPlaylists = new ArrayList<PlaylistDto>();
		}
		myPlaylists = getSearchService().searchPlaylists(null, null, profile.getId(), null, null, false).getResults();
	}
	public List<PlaylistDto> getMyPlaylists() {
		if(myPlaylists == null) {
			loadMyPlaylists();
		}
		return myPlaylists;
	}
	
	public synchronized void createPlaylistAndAddClips(AjaxBehaviorEvent e) {
		try {
			//check blank playlist name
			if(AihatUtils.isEmpty(newPlaylistTitle)) {
				addErrorMessage(BeanUtils.getBundleMsg("client_clips_popup_myplaylist_error_empty_playlist_name"));
				return;
			}
			
			//check duplicate an existing playlist name
			for(PlaylistDto playlist : myPlaylists) {
				if(newPlaylistTitle.equals(playlist.getName())) {
					addErrorMessage(BeanUtils.getBundleMsg("client_clips_popup_myplaylist_error_playlist_existing"));
					return;
				}
			}
			
			List<Integer> selectedClipIds = AihatUtils.getSelectedIds(getReferenceBean().getCurrentDtoList());
			//check not select clip
			if(AihatUtils.isEmpty(selectedClipIds)) {
				addErrorMessage(BeanUtils.getBundleMsg("client_clips_popup_myplaylist_error_not_select_clips"));
				return;
			}
			
			//create and add
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile != null && profile.getId() != null) {
				PlaylistDto createdPlaylist = getPlaylistService().createPlaylist(newPlaylistTitle, profile.getId(), newPlaylistDescription);
				getPlaylistService().addClips(createdPlaylist.getId(), selectedClipIds, null);
				loadMyPlaylists();
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public synchronized void addClipsToSelectedPlaylists(AjaxBehaviorEvent e) {
		try {
			List<Integer> selectedPlaylistIds = AihatUtils.getSelectedIds(myPlaylists);
			//check not select playlist
			if(AihatUtils.isEmpty(selectedPlaylistIds)) {
				addErrorMessage(BeanUtils.getBundleMsg("client_clips_popup_myplaylist_error_not_select_playlist"));
				return;
			}
			
			List<Integer> selectedClipIds = AihatUtils.getSelectedIds(getReferenceBean().getCurrentDtoList());
			//check not select clip
			if(AihatUtils.isEmpty(selectedClipIds)) {
				addErrorMessage(BeanUtils.getBundleMsg("client_clips_popup_myplaylist_error_not_select_clips"));
				return;
			}
			
			for(Integer anId : selectedPlaylistIds) {
				getPlaylistService().addClips(anId, selectedClipIds, null);
			}
			loadMyPlaylists();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	/**
	 * END OF POPUP MYPLAYLISTS
	 */
	
	/**
	 * FOLLOW & LIKE
	 */
	public synchronized void likeSinger(AjaxBehaviorEvent e) {
		try {
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return;
			}
			int singerId = Integer.parseInt(BeanUtils.getRequest().getParameter("singerId"));
			getSingerService().addLiked(profile.getId(), singerId);
			
			//check liked=true for singer
			SingerDto likedSinger;
			if(getReferenceBean() instanceof DetailBean) {
				likedSinger = ((DetailBean)getReferenceBean()).getSingerDetail();
			} else {
				likedSinger = (SingerDto) AihatUtils.getDtoFromList(singerId, getReferenceBean().getCurrentDtoList());
			}
			if(likedSinger != null) {
				likedSinger.setLiked(true);
			}
			
			//like action also involves follow action
			if(!likedSinger.getFollowed()) {
				followSinger(e);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void unlikeSinger(AjaxBehaviorEvent e) {
		try {
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return;
			}
			int singerId = Integer.parseInt(BeanUtils.getRequest().getParameter("singerId"));
			getSingerService().removeLiked(profile.getId(), singerId);
			
			//check liked=false for singer
			SingerDto likedSinger;
			if(getReferenceBean() instanceof DetailBean) {
				likedSinger = ((DetailBean)getReferenceBean()).getSingerDetail();
			} else {
				likedSinger = (SingerDto) AihatUtils.getDtoFromList(singerId, getReferenceBean().getCurrentDtoList());
			}
			if(likedSinger != null) {
				likedSinger.setLiked(false);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void followSinger(AjaxBehaviorEvent e) {
		try {
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return;
			}
			int singerId = Integer.parseInt(BeanUtils.getRequest().getParameter("singerId"));
			getSingerService().addFollower(profile.getId(), singerId);
			
			//check followed=true for singer
			SingerDto followedSinger;
			if(getReferenceBean() instanceof DetailBean) {
				followedSinger = ((DetailBean)getReferenceBean()).getSingerDetail();
			} else {
				followedSinger = (SingerDto) AihatUtils.getDtoFromList(singerId, getReferenceBean().getCurrentDtoList());
			}
			if(followedSinger != null) {
				followedSinger.setFollowed(true);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void unfollowSinger(AjaxBehaviorEvent e) {
		try {
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return;
			}
			int singerId = Integer.parseInt(BeanUtils.getRequest().getParameter("singerId"));
			getSingerService().removeFollower(profile.getId(), singerId);
			
			//check followed=false for singer
			SingerDto followedSinger;
			if(getReferenceBean() instanceof DetailBean) {
				followedSinger = ((DetailBean)getReferenceBean()).getSingerDetail();
			} else {
				followedSinger = (SingerDto) AihatUtils.getDtoFromList(singerId, getReferenceBean().getCurrentDtoList());
			}
			if(followedSinger != null) {
				followedSinger.setFollowed(false);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void followUploader(AjaxBehaviorEvent e) {
		try {
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return;
			}
			int uploaderId = Integer.parseInt(BeanUtils.getRequest().getParameter("uploaderId"));
			getUserService().addFollower(profile.getId(), uploaderId);
			
			//check followed=true for singer
			UserDto followedUploader;
			if(getReferenceBean() instanceof DetailBean) {
				followedUploader = ((DetailBean)getReferenceBean()).getUserDetail();
			} else {
				followedUploader = (UserDto) AihatUtils.getDtoFromList(uploaderId, getReferenceBean().getCurrentDtoList());
			}
			if(followedUploader != null) {
				followedUploader.setFollowed(true);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void unfollowUploader(AjaxBehaviorEvent e) {
		try {
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return;
			}
			int uploaderId = Integer.parseInt(BeanUtils.getRequest().getParameter("uploaderId"));
			getUserService().removeFollower(profile.getId(), uploaderId);
			
			//check followed=false for singer
			UserDto followedUploader;
			if(getReferenceBean() instanceof DetailBean) {
				followedUploader = ((DetailBean)getReferenceBean()).getUserDetail();
			} else {
				followedUploader = (UserDto) AihatUtils.getDtoFromList(uploaderId, getReferenceBean().getCurrentDtoList());
			}
			if(followedUploader != null) {
				followedUploader.setFollowed(false);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void likeClip(AjaxBehaviorEvent e) {
		try {
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return;
			}
			int clipId = Integer.parseInt(BeanUtils.getRequest().getParameter("clipId"));
			getClipService().addLiked(clipId, profile.getId());
			
			//check followed=true for clip
			ClipDto likedClip = (ClipDto) AihatUtils.getDtoFromList(clipId, getReferenceBean().getCurrentDtoList());
			if(likedClip != null) {
				likedClip.setLiked(true);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public synchronized void unlikeClip(AjaxBehaviorEvent e) {
		try {
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return;
			}
			int clipId = Integer.parseInt(BeanUtils.getRequest().getParameter("clipId"));
			getClipService().removeLiked(clipId, profile.getId());
			
			//check followed=false for singer
			ClipDto likedClip = (ClipDto) AihatUtils.getDtoFromList(clipId, getReferenceBean().getCurrentDtoList());
			if(likedClip != null) {
				likedClip.setLiked(false);
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	/**
	 * END OF FOLLOW & LIKE
	 */
	
	/**
	 * NOTIFIED CLIPS
	 */
	public long getNotifiedClipsCount() {
		if(BeanUtils.getUserProfileBean().getLoggedIn()) {
			return getSearchService().searchClips(null, null, null, null, null, null, null,
					BeanUtils.getLogginUserId(), null, null, null, null, true, null, 
					BeanUtils.getLogginUserId()).getnResults();
		} else {
			return 0;
		}
	}
	/**
	 * END OF NOTIFIED CLIPS
	 */
	
	public int getNumberOfTopPanelItems() {
		if(BeanUtils.getUserProfileBean().getLoggedIn()) {
			return 4;
		} else {
			return 3;
		}
	}
	
	public void keepSession(AjaxBehaviorEvent e) {
	}
	
	//getter setter
	public String getNewPlaylistTitle() {
		return newPlaylistTitle;
	}
	public void setNewPlaylistTitle(String newPlaylistTitle) {
		this.newPlaylistTitle = newPlaylistTitle;
	}
	public String getSelectedPlaylistIds() {
		return selectedPlaylistIds;
	}
	public void setSelectedPlaylistIds(String selectedPlaylistIds) {
		this.selectedPlaylistIds = selectedPlaylistIds;
	}
	public void setMyPlaylists(List<PlaylistDto> myPlaylists) {
		this.myPlaylists = myPlaylists;
	}

	public String getNewPlaylistDescription() {
		return newPlaylistDescription;
	}

	public void setNewPlaylistDescription(String newPlaylistDescription) {
		this.newPlaylistDescription = newPlaylistDescription;
	}
	
}

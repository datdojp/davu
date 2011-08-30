package net.aihat.bean.client;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import net.aihat.bean.PagingBean;
import net.aihat.criteria.PagingCriterion;
import net.aihat.criteria.SortCriterion;
import net.aihat.dto.ClipDto;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class DetailBean extends BaseClientBean implements PagingBean {
	public DetailBean() {
		logger = Logger.getLogger(DetailBean.class);
	}
	
	private SingerDto singerDetail = new SingerDto();
	private ComposerDto composerDetail = new ComposerDto();
	private PlaylistDto playlistDetail = new PlaylistDto();
	private GenreDto genreDetail = new GenreDto();
	private UserDto userDetail = new UserDto();
	
	private String objectType;
	private final static String SINGER = "singer";
	private final static String COMPOSER = "composer";
	private final static String PLAYLIST = "playlist";
	private final static String GENRE = "genre";
	private final static String USER = "user";
	
	private List referenceDtos;
	private long referenceDtosCount;
	private PagingCriterion referenceDtosPaging;
	
	/**
	 * MISC
	 */
	protected List getCurrentDtoList() {
		return referenceDtos;
	}
	public String init() {
		super.init();
		return null;
	}
	public String getBeanName() {
		return "detailBean";
	}
	/**
	 * END OF MISC
	 */
	
	/**
	 * SHOW DETAIL
	 */
	public synchronized void showSingerDetail(AjaxBehaviorEvent e) {
		try {
			objectType = SINGER;
			
			Integer singerId = Integer.parseInt(BeanUtils.getRequest().getParameter("singerId"));
			singerDetail = getSingerService().getSinger(singerId);
			
			Integer userId = BeanUtils.getLogginUserId();
			if(userId != null) {
				singerDetail.setFollowed(getSingerService().checkFollower(userId, singerDetail.getId()));
				singerDetail.setLiked(getSingerService().checkLiked(userId, singerDetail.getId()));
			}
			
			resetReferenceDtosPaging();
			updateReferenceDtos();
			updateReferenceDtosCount();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void showComposerDetail(AjaxBehaviorEvent e) {
		try {
			objectType = COMPOSER;
			
			Integer composerId = Integer.parseInt(BeanUtils.getRequest().getParameter("composerId"));
			composerDetail = getComposerService().getComposer(composerId);
			
			resetReferenceDtosPaging();
			updateReferenceDtos();
			updateReferenceDtosCount();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void showPlaylistDetail(AjaxBehaviorEvent e) {
		try {
			objectType = PLAYLIST;
			
			Integer playlistId = Integer.parseInt(BeanUtils.getRequest().getParameter("playlistId"));
			playlistDetail = getPlaylistService().getPlaylist(playlistId);
			if(BeanUtils.getUserProfileBean().getLoggedIn()) {
				playlistDetail.setEditable(BeanUtils.getLogginUserId().equals(playlistDetail.getUser().getId()));
			}
			
			updateReferenceDtos();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void updatePlaylistName(AjaxBehaviorEvent e) {
		try {
			if(AihatUtils.isEmpty(playlistDetail.getName())) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0007"));
			} else {
				getPlaylistService().updatePlaylist(playlistDetail);
				addInfoMessage(BeanUtils.getBundleMsg("CM0008"));
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void removeClipsFromPlaylist(AjaxBehaviorEvent e) {
		try {
			if(BeanUtils.getUserProfileBean().getLoggedIn()) {
				getPlaylistService().removeClips(playlistDetail.getId(),
						AihatUtils.getSelectedIds(referenceDtos));
				updateReferenceDtos();
				playlistDetail = getPlaylistService().getPlaylist(playlistDetail.getId());
			} else {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void changeClipOrderInPlaylist(AjaxBehaviorEvent e) {
		try {
			if(BeanUtils.getUserProfileBean().getLoggedIn()) {
				Integer clipId = Integer.parseInt(BeanUtils.getRequest().getParameter("clipId"));
				Integer orderChangedBy = Integer.parseInt(BeanUtils.getRequest().getParameter("orderChangedBy"));
				
				ClipDto clip = (ClipDto) AihatUtils.getDtoFromList(clipId, referenceDtos);
				
				Integer oldOrder = clip.getOrderInPlaylist();
				Integer newOrder = clip.getOrderInPlaylist() + orderChangedBy;
				
				if(0 <= newOrder && newOrder < referenceDtos.size()) {
					clip.setOrderInPlaylist(newOrder);
					ClipDto effectedClip = (ClipDto)referenceDtos.get(newOrder);
					effectedClip.setOrderInPlaylist(oldOrder);
					
					List<ClipDto> toUpdateOrder = new ArrayList<ClipDto>();
					toUpdateOrder.add(clip);
					toUpdateOrder.add(effectedClip);
					getPlaylistService().updateClipOrder(playlistDetail.getId(), toUpdateOrder);
				}
				updateReferenceDtos();
			} else {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void showGenreDetail(AjaxBehaviorEvent e) {
		try {
			objectType = GENRE;
			
			Integer genreId = Integer.parseInt(BeanUtils.getRequest().getParameter("genreId"));
			genreDetail = getGenreService().getGenre(genreId);
		
			resetReferenceDtosPaging();
			updateReferenceDtos();
			updateReferenceDtosCount();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void showUserDetail(AjaxBehaviorEvent e) {
		try {
			objectType = USER;
			
			Integer userId = Integer.parseInt(BeanUtils.getRequest().getParameter("userId"));
			userDetail = getUserService().getUser(userId);
			userDetail.setFollowers(getUserService().findFollower(userDetail.getId()));
			if(BeanUtils.getUserProfileBean().getLoggedIn()) {
				userDetail.setFollowed(getUserService().checkFollower(BeanUtils.getLogginUserId(), userDetail.getId()));
			}
			
			resetReferenceDtosPaging();
			updateReferenceDtos();
			updateReferenceDtosCount();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	private void resetReferenceDtosPaging() {
		referenceDtosPaging = new PagingCriterion(0l, getConfigurationService().getnRowsPerPage());
	}
	
	private long updateReferenceDtosCount() {
		if(SINGER.equals(objectType)) {
			referenceDtosCount =
				getSearchService().searchClips(null, null, singerDetail.getId(), null, null, null, null, BeanUtils.getLogginUserId(), null, null, null, null, true, null, null).getnResults();
		} else if(COMPOSER.equals(objectType)) {
			referenceDtosCount = 
				getSearchService().searchClips(null, null, null, composerDetail.getId(), null, null, null, BeanUtils.getLogginUserId(), null, null, null, null, true, null, null).getnResults();
		} else if(PLAYLIST.equals(objectType)) {
			referenceDtosCount =
				getSearchService().searchClips(null, null, null, null, null, playlistDetail.getId(), null, BeanUtils.getLogginUserId(), null, null, null, null, true, null, null).getnResults();
		} else if(GENRE.equals(objectType)) {
			referenceDtosCount =
				getSearchService().searchClips(null, null, null, null, genreDetail.getId(), null, null, BeanUtils.getLogginUserId(), null, null, null, null, true, null, null).getnResults();
		} else if(USER.equals(objectType)) {
			referenceDtosCount =
				getSearchService().searchClips(null, null, null, null, null, null, userDetail.getMail(), BeanUtils.getLogginUserId(), null, null, null, null, true, null, null).getnResults();
		}
		return referenceDtosCount;
	}
	
	private void updateReferenceDtos() {
		if(SINGER.equals(objectType)) {
			referenceDtos =
				getSearchService().searchClips(null, null, singerDetail.getId(), null, null, null, null, BeanUtils.getLogginUserId(), null, null, new SortCriterion("title", SortCriterion.ORDER_ASCENDING), referenceDtosPaging, false, null, null).getResults();
		} else if(COMPOSER.equals(objectType)) {
			referenceDtos = 
				getSearchService().searchClips(null, null, null, composerDetail.getId(), null, null, null, BeanUtils.getLogginUserId(), null, null, new SortCriterion("title", SortCriterion.ORDER_ASCENDING), referenceDtosPaging, false, null, null).getResults();
		} else if(PLAYLIST.equals(objectType)) {
			referenceDtos =
				getSearchService().searchClips(null, null, null, null, null, playlistDetail.getId(), null, BeanUtils.getLogginUserId(), null, null, new SortCriterion("orderInPlaylist", SortCriterion.ORDER_ASCENDING), null, false, null, null).getResults();
		} else if(GENRE.equals(objectType)) {
			referenceDtos =
				getSearchService().searchClips(null, null, null, null, genreDetail.getId(), null, null, BeanUtils.getLogginUserId(), null, null, new SortCriterion("title", SortCriterion.ORDER_ASCENDING), referenceDtosPaging, false, null, null).getResults();
		} else if(USER.equals(objectType)) {
			referenceDtos =
				getSearchService().searchClips(null, null, null, null, null, null, userDetail.getMail(), BeanUtils.getLogginUserId(), null, null, new SortCriterion("title", SortCriterion.ORDER_ASCENDING), referenceDtosPaging, false, null, null).getResults();
		}
	}
	/**
	 * END OF SHOW DETAIL
	 */
	
	/**
	 * PLAYLIST FUNCTIONS
	 */
	public synchronized void deletePlaylists(AjaxBehaviorEvent e) {
		try {
			List<PlaylistDto> playlists = new ArrayList<PlaylistDto>();
			playlists.add(playlistDetail);
			getPlaylistService().deletePlaylists(playlists);
			addInfoMessage(BeanUtils.getBundleMsg("CM0011"));
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	/**
	 * END OF PLAYLIST FUNCTIONS
	 */

	
	/**
	 * PAGING
	 */
	public synchronized void nextPage(AjaxBehaviorEvent e) {
		try {
			long n = updateReferenceDtosCount();
			long upperLimit = n - (n % referenceDtosPaging.getRowCount());
			if(upperLimit == n) {
				upperLimit -= referenceDtosPaging.getRowCount();
			}
			referenceDtosPaging.setOffset(
					Math.min(upperLimit, referenceDtosPaging.getOffset() + referenceDtosPaging.getRowCount())
				);
			updateReferenceDtos();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void previousPage(AjaxBehaviorEvent e) {
		try {
			long n = updateReferenceDtosCount();
			referenceDtosPaging.setOffset(
				Math.max(0, referenceDtosPaging.getOffset() - referenceDtosPaging.getRowCount())
			);
			updateReferenceDtos();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void firstPage(AjaxBehaviorEvent e) {
		try {
			long n = updateReferenceDtosCount();
			referenceDtosPaging.setOffset(0l);
			updateReferenceDtos();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void lastPage(AjaxBehaviorEvent e) {
		try {
			long n = updateReferenceDtosCount();
			long upperLimit = n - (n % referenceDtosPaging.getRowCount());
			if(upperLimit == n) {
				upperLimit -= referenceDtosPaging.getRowCount();
			}
			referenceDtosPaging.setOffset(upperLimit);
			updateReferenceDtos();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public String getPageRange() {
		if(referenceDtosPaging != null) {
			long start = referenceDtosPaging.getOffset() + 1;
			long end = Math.min(referenceDtosCount,
					referenceDtosPaging.getOffset() + referenceDtosPaging.getRowCount());
			return "" + start + "-" + end + "/" + referenceDtosCount;
		} else {
			return "";
		}
	}
	/**
	 * END OF PAGING
	 */
	
	//getter setter
	public SingerDto getSingerDetail() {
		return singerDetail;
	}
	public void setSingerDetail(SingerDto singerDetail) {
		this.singerDetail = singerDetail;
	}
	public ComposerDto getComposerDetail() {
		return composerDetail;
	}
	public void setComposerDetail(ComposerDto composerDetail) {
		this.composerDetail = composerDetail;
	}
	public PlaylistDto getPlaylistDetail() {
		return playlistDetail;
	}
	public void setPlaylistDetail(PlaylistDto playlistDetail) {
		this.playlistDetail = playlistDetail;
	}
	public GenreDto getGenreDetail() {
		return genreDetail;
	}
	public void setGenreDetail(GenreDto genreDetail) {
		this.genreDetail = genreDetail;
	}
	public UserDto getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDto userDetail) {
		this.userDetail = userDetail;
	}
	public List getReferenceDtos() {
		return referenceDtos;
	}
	public void setReferenceDtos(List referenceDtos) {
		this.referenceDtos = referenceDtos;
	}
	public long getReferenceDtosCount() {
		return referenceDtosCount;
	}
	public void setReferenceDtosCount(long referenceDtosCount) {
		this.referenceDtosCount = referenceDtosCount;
	}
	public PagingCriterion getReferenceDtosPaging() {
		return referenceDtosPaging;
	}
	public void setReferenceDtosPaging(PagingCriterion referenceDtosPaging) {
		this.referenceDtosPaging = referenceDtosPaging;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}


	

package net.aihat.service;

import java.util.ArrayList;
import java.util.List;

import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.criteria.SortCriterion;
import net.aihat.dto.ClipDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class PlaylistServiceImpl extends BaseService implements PlaylistService {
	@Transactional(rollbackFor=DataAccessException.class)
	public void deletePlaylists(List<PlaylistDto> playlists) throws DataAccessException {
		if(!AihatUtils.isEmpty(playlists)) {
			List<Integer> playlistIds = new ArrayList<Integer>(playlists.size());
			for(PlaylistDto aPlaylist : playlists) {
				playlistIds.add(aPlaylist.getId());
			}
			getPlaylistDao().updateDeleted(playlistIds);
		}
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public PlaylistDto createPlaylist(String name, Integer userId, String description) throws DataAccessException {
		PlaylistDto playlist = new PlaylistDto();
		playlist.setName(name);
		playlist.setUser(new UserDto());
		playlist.getUser().setId(userId);
		playlist.setDescription(description);
		return getPlaylistDao().insert(playlist);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void addClips(Integer playlistId, List<Integer> clipIds, Integer mainClipId) throws DataAccessException {
		PlaylistDto playlist = new PlaylistDto();
		
		//get all clips of playlist
		playlist.setId(playlistId);
		ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
		clipSearchCriteria.setPlaylist(playlist);
		clipSearchCriteria.setSortCriterion(new SortCriterion("orderInPlaylist", SortCriterion.ORDER_ASCENDING));
		List<ClipDto> clipsOfPlaylist = getClipDao().search(clipSearchCriteria).getResults();
		
		//get list of clips that not in the playlist
		List<ClipDto> clipsToAdd = new ArrayList<ClipDto>();
		for(Integer aClipId : clipIds) {
			if(AihatUtils.getDtoFromList(aClipId, clipsOfPlaylist) == null) {
				ClipDto clip = new ClipDto();
				clip.setId(aClipId);
				clipsToAdd.add(clip);
			}
		}
		
		//add
		int order = 0;
		if(!AihatUtils.isEmpty(clipsOfPlaylist)) {
			order = clipsOfPlaylist.size();
		}
		for(ClipDto aClip : clipsToAdd) {
			boolean isMainClip = false;
			if(AihatUtils.isValidId(mainClipId)) {
				isMainClip = mainClipId.equals(aClip.getId());
			}
			aClip.setOrderInPlaylist(order);
			getPlaylistDao().addClip(playlist, aClip, isMainClip);
			order++;
		}
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public PlaylistDto getPlaylist(Integer id) throws DataAccessException {
		PlaylistDto playlist = getPlaylistDao().get(id);
		ClipDto mainClip = getPlaylistDao().getMainClip(playlist);
		playlist.setMainClip(mainClip);
		return playlist;
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updatePlaylist(PlaylistDto playlist) throws DataAccessException {
		getPlaylistDao().update(playlist);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void removeClips(Integer playlistId, List<Integer> clipsIds)	throws DataAccessException {
		PlaylistDto playlist = new PlaylistDto();
		playlist.setId(playlistId);
		for(Integer aClipId : clipsIds) {
			ClipDto aClip = new ClipDto();
			aClip.setId(aClipId);
			getPlaylistDao().removeClip(playlist, aClip);
		}
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateClipOrder(Integer playlistId, List<ClipDto> clips) throws DataAccessException {
		if(AihatUtils.isEmpty(clips)) {
			return;
		}
		PlaylistDto playlist = new PlaylistDto();
		playlist.setId(playlistId);
		for(ClipDto aClip : clips) {
			getPlaylistDao().updateClipOrder(playlist, aClip);
		}
	}
}

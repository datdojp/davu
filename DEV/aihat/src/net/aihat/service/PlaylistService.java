package net.aihat.service;

import java.util.List;

import net.aihat.dto.ClipDto;
import net.aihat.dto.PlaylistDto;
import org.springframework.dao.DataAccessException;

public interface PlaylistService {
	public PlaylistDto getPlaylist(Integer id) throws DataAccessException;
	public void deletePlaylists(List<PlaylistDto> playlists) throws DataAccessException;
	public PlaylistDto createPlaylist(String name, Integer userId) throws DataAccessException;
	public void addClips(Integer playlistId, List<Integer> clipIds, Integer mainClipId) throws DataAccessException;
	public void removeClips(Integer playlistId, List<Integer> clipsIds) throws DataAccessException;
	public void updatePlaylist(PlaylistDto playlist) throws DataAccessException;
	public void updateClipOrder(Integer playlistId, List<ClipDto> clips) throws DataAccessException;
}

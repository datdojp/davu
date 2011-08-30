package net.aihat.dao;

import java.util.List;
import net.aihat.criteria.PlaylistSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.SearchResultDto;

import org.springframework.dao.DataAccessException;

public interface PlaylistDao {
	public PlaylistDto get(Integer id) throws DataAccessException;
	public SearchResultDto search(PlaylistSearchCriteria criteria) throws DataAccessException;
	public PlaylistDto insert(PlaylistDto dto) throws DataAccessException;
	public void updateDeleted(List<Integer> ids) throws DataAccessException;
	public void update(PlaylistDto dto) throws DataAccessException;
	public void addClip(PlaylistDto playlist, ClipDto clip, boolean isMainClip)throws DataAccessException;
	public void removeClip(PlaylistDto playlist, ClipDto clip) throws DataAccessException;
	public ClipDto getMainClip(PlaylistDto playlist) throws DataAccessException;
	public void updateClipOrder(PlaylistDto playlist, ClipDto clip) throws DataAccessException;
}

package net.aihat.dao;

import java.util.List;

import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.SearchResultDto;
import net.aihat.dto.UserDto;

import org.springframework.dao.DataAccessException;

public interface ClipDao {
	public ClipDto get(Integer id) throws DataAccessException;
	public List<ClipDto> getMostLiked(int nClips) throws DataAccessException;
	public SearchResultDto search(ClipSearchCriteria criteria) throws DataAccessException;
	public ClipDto insert(ClipDto dto) throws DataAccessException;
	public void insertClipSinger(Integer clipId, Integer singerId, Integer order) throws DataAccessException;
	public void insertClipComposer(Integer clipId, Integer composerId, Integer order) throws DataAccessException;
	public void insertClipGenre(Integer clipId, Integer genreId, Integer order) throws DataAccessException;
	public void updateDeleted(List<Integer> ids) throws DataAccessException;
	public void update(ClipDto dto) throws DataAccessException;
	public void clearClipSinger(Integer clipId) throws DataAccessException;
	public void clearClipComposer(Integer clipId) throws DataAccessException;
	public void clearClipGenre(Integer clipId) throws DataAccessException;
	public void addView(ClipDto clip, UserDto user) throws DataAccessException;
	public void addLiked(ClipDto clip, UserDto user) throws DataAccessException;
	public void deleteUserLikeClip(ClipDto clip, UserDto user) throws DataAccessException;
	public boolean checkLiked(ClipDto clip, UserDto user) throws DataAccessException;
	public List<ClipDto> getAllClipsInDB() throws DataAccessException;
	public void updateTitle(ClipDto dto) throws DataAccessException;
}

package net.aihat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.SearchResultDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;

public class ClipDaoImpl extends BaseDao implements ClipDao {
	private static final int ACTION_ENCRYPT = 0;
	private static final int ACTION_DECRYPT = 1;
	private List<ClipDto> crypt(List<ClipDto> clips, int action) {
		if(AihatUtils.isEmpty(clips)) {
			return clips;
		}
		for(ClipDto aClip : clips) {
			if(action == ACTION_ENCRYPT) {
				aClip.setTitle(AihatUtils.encryptText(aClip.getTitle()));
			} else if(action  == ACTION_DECRYPT) {
				aClip.setTitle(AihatUtils.decryptText(aClip.getTitle()));
			}
		}
		return clips;
	}
	private ClipDto crypt(ClipDto clip, int action) {
		if(clip == null) {
			return clip;
		}
		if(action == ACTION_ENCRYPT) {
			clip.setTitle(AihatUtils.encryptText(clip.getTitle()));
		} else if(action  == ACTION_DECRYPT) {
			clip.setTitle(AihatUtils.decryptText(clip.getTitle()));
		}
		
		return clip;
	}
	
	
	public List<ClipDto> getMostLiked(int nClips) throws DataAccessException {
		List<ClipDto> results = getSqlMapClientTemplate().queryForList("getMostLikedClips", nClips);
		loadRelatedDto(results);
		return crypt(results, ACTION_DECRYPT);
	}

	public SearchResultDto search(ClipSearchCriteria criteria) throws DataAccessException {
		if(criteria == null) {
			return SearchResultDto.EMPTY;
		}
		
		ClipSearchCriteria param = (ClipSearchCriteria) criteria.clone();
		if(AihatUtils.isExactKeyword(param.getTitle())) {
			param.setTitle(AihatUtils.getExactKeyword(param.getTitle()));
			param.setTitle(getSearchableString(param.getTitle()));
		} else {
			param.setTitle(getSQLSearchableString(param.getTitle()));
		}
		
		if(criteria.isForCounting()) {
			Long nResults = (Long) getSqlMapClientTemplate().queryForObject("countClip", param);
			return new SearchResultDto(nResults.intValue());
		} else {
			List<ClipDto> results = getSqlMapClientTemplate().queryForList("searchClip", param);
			loadRelatedDto(results);
			return new SearchResultDto(crypt(results, ACTION_DECRYPT));
		}
	}

	public ClipDto get(Integer id) throws DataAccessException {
		ClipDto result = (ClipDto) getSqlMapClientTemplate().queryForObject("getClip", id);
		loadRelatedDto(result);
		return crypt(result, ACTION_DECRYPT);
	}

	public ClipDto insert(ClipDto dto) throws DataAccessException {
		dto.setTitle(dto.getTitle().trim());
		dto.setTitleSearch(getSearchableString(dto.getTitle()));
		crypt(dto, ACTION_ENCRYPT);
		getSqlMapClientTemplate().insert("insertClip", dto);
		dto.setId(getLastInsertId());
		return crypt(dto, ACTION_DECRYPT);
	}

	public void updateDeleted(List<Integer> ids) throws DataAccessException {
		if(AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updateClipsDeleted", ids);
	}

	public void update(ClipDto dto)  throws DataAccessException{
		dto.setTitleSearch(getSearchableString(dto.getTitle()));
		crypt(dto, ACTION_ENCRYPT);
		getSqlMapClientTemplate().update("updateClip", dto);
		crypt(dto, ACTION_DECRYPT);
	}

	public void insertClipSinger(Integer clipId, Integer singerId, Integer order) throws DataAccessException {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("clipId", clipId);
		param.put("singerId", singerId);
		param.put("order", order);
		getSqlMapClientTemplate().insert("insertClipSinger", param);
	}

	public void insertClipComposer(Integer clipId, Integer composerId, Integer order) throws DataAccessException {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("clipId", clipId);
		param.put("composerId", composerId);
		param.put("order", order);
		getSqlMapClientTemplate().insert("insertClipComposer", param);
	}

	public void insertClipGenre(Integer clipId, Integer genreId, Integer order) throws DataAccessException {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("clipId", clipId);
		param.put("genreId", genreId);
		param.put("order", order);
		getSqlMapClientTemplate().insert("insertClipGenre", param);
	}

	public void clearClipSinger(Integer clipId) throws DataAccessException {
		getSqlMapClientTemplate().delete("clearClipSinger", clipId);
		
	}

	public void clearClipComposer(Integer clipId) throws DataAccessException {
		getSqlMapClientTemplate().delete("clearClipComposer", clipId);
	}

	public void clearClipGenre(Integer clipId) throws DataAccessException {
		getSqlMapClientTemplate().delete("clearClipGenre", clipId);
	}
	
	private void loadRelatedDto(ClipDto dto) throws DataAccessException {
		List<SingerDto> singers = getSqlMapClientTemplate().queryForList("loadSingerForClip", dto.getId());
		List<ComposerDto> composers = getSqlMapClientTemplate().queryForList("loadComposerForClip", dto.getId());
		List<GenreDto> genres = getSqlMapClientTemplate().queryForList("loadGenreForClip", dto.getId());
		
		dto.setSingers(singers);
		dto.setComposers(composers);
		dto.setGenres(genres);
	}
	
	private void loadRelatedDto(List<ClipDto> dtos) throws DataAccessException {
		if(!AihatUtils.isEmpty(dtos)) {
			for(ClipDto aDto : dtos) {
				loadRelatedDto(aDto);
			}
		}
	}

	public void addView(ClipDto clip, UserDto user) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clip", clip);
		params.put("user", user);
		getSqlMapClientTemplate().insert("addClipView", params);
	}

	public boolean checkLiked(ClipDto clip, UserDto user)
			throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clip", clip);
		params.put("user", user);
		long result = (Long) getSqlMapClientTemplate().queryForObject("checkLikedForClip", params);
		return result == 1;
	}

	public void addLiked(ClipDto clip, UserDto user) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clip", clip);
		params.put("user", user);
		getSqlMapClientTemplate().insert("addClipLiked", params);
	}

	public void deleteUserLikeClip(ClipDto clip, UserDto user)
			throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clip", clip);
		params.put("user", user);
		getSqlMapClientTemplate().delete("deleteUserLikeClip", params);
	}

	public List<ClipDto> getAllClipsInDB() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("getAllClipInDB");
	}
	public void updateTitle(ClipDto dto) throws DataAccessException {
		getSqlMapClientTemplate().update("updateClipTitle", dto);
	}
}

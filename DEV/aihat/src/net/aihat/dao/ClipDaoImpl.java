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
	public List<ClipDto> getMostLiked(int nClips) throws DataAccessException {
		List<ClipDto> result = getSqlMapClientTemplate().queryForList("getMostLikedClips", nClips);
		loadRelatedDto(result);
		return result;
	}

	public SearchResultDto search(ClipSearchCriteria criteria) throws DataAccessException {
		if(criteria == null) {
			return SearchResultDto.EMPTY;
		}
		
		ClipSearchCriteria param = (ClipSearchCriteria) criteria.clone();
		param.setTitle(getSQLSearchableString(param.getTitle()));
		
		if(criteria.isForCounting()) {
			Long nResults = (Long) getSqlMapClientTemplate().queryForObject("countClip", param);
			return new SearchResultDto(nResults.intValue());
		} else {
			List<ClipDto> results = getSqlMapClientTemplate().queryForList("searchClip", param);
			loadRelatedDto(results);
			return new SearchResultDto(results);
		}
	}

	public ClipDto get(Integer id) throws DataAccessException {
		ClipDto result = (ClipDto) getSqlMapClientTemplate().queryForObject("getClip", id);
		loadRelatedDto(result);
		return result;
	}

	public ClipDto insert(ClipDto dto) throws DataAccessException {
		dto.setTitleSearch(getSearchableString(dto.getTitle()));
		getSqlMapClientTemplate().insert("insertClip", dto);
		dto.setId(getLastInsertId());
		return dto;
	}

	public void updateDeleted(List<Integer> ids) throws DataAccessException {
		if(AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updateClipsDeleted", ids);
	}

	public void update(ClipDto dto)  throws DataAccessException{
		dto.setTitleSearch(getSearchableString(dto.getTitle()));
		getSqlMapClientTemplate().update("updateClip", dto);
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
}

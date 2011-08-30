package net.aihat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aihat.criteria.PlaylistSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.SearchResultDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;

public class PlaylistDaoImpl extends BaseDao implements PlaylistDao {
	public PlaylistDto get(Integer id) throws DataAccessException {
		return (PlaylistDto) getSqlMapClientTemplate().queryForObject("getPlaylist", id);
	}

	public SearchResultDto search(PlaylistSearchCriteria criteria) throws DataAccessException {
		if(criteria == null) {
			return SearchResultDto.EMPTY;
		}
		
		PlaylistSearchCriteria param = (PlaylistSearchCriteria) criteria.clone();
		param.setName(getSQLSearchableString(param.getName()));
		
		if(criteria.isForCounting()) {
			Long nResults = (Long) getSqlMapClientTemplate().queryForObject("countPlaylist", param);
			return new SearchResultDto(nResults.intValue());
		} else {
			List<PlaylistDto> results = getSqlMapClientTemplate().queryForList("searchPlaylist", param);
			return new SearchResultDto(results);
		}
	}

	public PlaylistDto insert(PlaylistDto dto) throws DataAccessException {
		dto.setNameSearch(getSearchableString(dto.getName()));
		getSqlMapClientTemplate().insert("insertPlaylist", dto);
		dto.setId(getLastInsertId());
		return dto;
	}

	public void updateDeleted(List<Integer> ids) throws DataAccessException {
		if (AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updatePlaylistDeleted", ids);
	}
	
	public void update(PlaylistDto dto) throws DataAccessException {
		dto.setNameSearch(getSearchableString(dto.getName()));
		getSqlMapClientTemplate().update("updatePlaylist", dto);
	}

	public void addClip(PlaylistDto playlist, ClipDto clip, boolean isMainClip) throws DataAccessException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("playlist", playlist);
		param.put("clip", clip);
		param.put("isMainClip", isMainClip);
		getSqlMapClientTemplate().insert("addClipToPlaylist", param);		
	}

	public void removeClip(PlaylistDto playlist, ClipDto clip) throws DataAccessException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("playlist", playlist);
		param.put("clip", clip);
		getSqlMapClientTemplate().delete("removeClipFromPlaylist", param);
	}

	public ClipDto getMainClip(PlaylistDto playlist) throws DataAccessException {
		return (ClipDto) getSqlMapClientTemplate().queryForObject("getMailClipForPlaylist", playlist);
	}

	public void updateClipOrder(PlaylistDto playlist, ClipDto clip) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("playlist", playlist);
		params.put("clip", clip);
		getSqlMapClientTemplate().update("updateClipOrderInPlaylist", params);
	}
}

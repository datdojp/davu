package net.aihat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aihat.criteria.SingerSearchCriteria;
import net.aihat.dto.SearchResultDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;

public class SingerDaoImpl extends BaseDao implements SingerDao {
	public SingerDto get(Integer id) throws DataAccessException {
		return (SingerDto) getSqlMapClientTemplate().queryForObject(
				"getSinger", id);
	}

	public SearchResultDto search(SingerSearchCriteria criteria) throws DataAccessException {
		if(criteria == null) {
			return SearchResultDto.EMPTY;
		}
		
		SingerSearchCriteria param = (SingerSearchCriteria) criteria.clone();
		if(AihatUtils.isExactKeyword(param.getName())) {
			param.setName(AihatUtils.getExactKeyword(param.getName()));
			param.setName(getSearchableString(param.getName()));
		} else {
			param.setName(getSQLSearchableString(param.getName()));
		}
		
		if(criteria.isForCounting()) {
			Long nResults = (Long) getSqlMapClientTemplate().queryForObject("countSinger", param);
			return new SearchResultDto(nResults.intValue());
		} else {
			List<SingerDto> results = getSqlMapClientTemplate().queryForList("searchSinger", param);
			return new SearchResultDto(results);
		}
	}

	public SingerDto insert(SingerDto dto) throws DataAccessException {
		dto.setName(dto.getName().trim());
		dto.setNameSearch(getSearchableString(dto.getName()));
		// dto.setNameGet(getGetableString(dto.getName()));
		getSqlMapClientTemplate().insert("insertSinger", dto);
		dto.setId(getLastInsertId());
		return dto;
	}

	public void updateDeleted(List<Integer> ids) throws DataAccessException {
		if (AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updateSingerDeleted", ids);
	}

	public void update(SingerDto dto) throws DataAccessException {
		dto.setNameSearch(getSearchableString(dto.getName()));
		// dto.setNameGet(getGetableString(dto.getName()));
		getSqlMapClientTemplate().update("updateSinger", dto);
	}

	public void updateImage(SingerDto dto) throws DataAccessException {
		getSqlMapClientTemplate().update("updateSingerImage", dto);
	}

	public List<SingerDto> getAllSimpleSingers() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("getAllSimpleSingers");
	}

	public SingerDto getSingerByName(String name) throws DataAccessException {
		List<SingerDto> results = getSqlMapClientTemplate().queryForList(
				"getSingerByName", name);
		
		//we have to do this because in MySQL, it doesn't care about Vietnamese signs when comparing 2 string
		for(SingerDto aSinger : results) {
			if(aSinger.getName().equals(name)) {
				return aSinger;
			}
		}
		
		return null;
	}

	public void insertUserFollowSinger(UserDto user, SingerDto singer) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("singer", singer);
		getSqlMapClientTemplate().insert("insertUserFollowSinger", params);
	}

	public void deleteUserFollowSinger(UserDto user, SingerDto singer) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("singer", singer);
		getSqlMapClientTemplate().delete("deleteUserFollowSinger", params);
	}

	public boolean checkFollower(UserDto user, SingerDto singer)
			throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("singer", singer);
		long result = (Long) getSqlMapClientTemplate().queryForObject("checkFollowerForSinger", params);
		return result == 1;
	}
	
	public void insertUserLikeSinger(UserDto user, SingerDto singer) throws DataAccessException {
		Map<String, Object> params =  new HashMap<String, Object>();
		params.put("user", user);
		params.put("singer", singer);
		getSqlMapClientTemplate().insert("insertUserLikeSinger", params);
	}
	public void deleteUserLikeSinger(UserDto user, SingerDto singer) throws DataAccessException {
		Map<String, Object> params =  new HashMap<String, Object>();
		params.put("user", user);
		params.put("singer", singer);
		getSqlMapClientTemplate().delete("deleteUserLikeSinger", params);
	}
	public boolean checkLiked(UserDto user, SingerDto singer) throws DataAccessException {
		Map<String, Object> params =  new HashMap<String, Object>();
		params.put("user", user);
		params.put("singer", singer);
		long result = (Long) getSqlMapClientTemplate().queryForObject("checkLikedForSinger", params);
		return result == 1;
	}
}

package net.aihat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aihat.criteria.GenreSearchCriteria;
import net.aihat.dto.GenreDto;
import net.aihat.dto.SearchResultDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;

public class GenreDaoImpl extends BaseDao implements GenreDao {
	public GenreDto get(Integer id) throws DataAccessException {
		return (GenreDto) getSqlMapClientTemplate().queryForObject("getGenre", id);
	}

	public SearchResultDto search(GenreSearchCriteria criteria) throws DataAccessException {
		if(criteria == null) {
			return SearchResultDto.EMPTY;
		}
		
		GenreSearchCriteria param = (GenreSearchCriteria) criteria.clone();
		param.setName(getSQLSearchableString(param.getName()));
		
		if(criteria.isForCounting()) {
			Long nResults = (Long) getSqlMapClientTemplate().queryForObject("countGenre", param);
			return new SearchResultDto(nResults.intValue());
		} else {
			List<GenreDto> results = getSqlMapClientTemplate().queryForList("searchGenre", param);
			return new SearchResultDto(results);
		}
	}

	public GenreDto insert(GenreDto dto) throws DataAccessException {
		dto.setNameViSearch(getSearchableString(dto.getNameVi()));
		dto.setNameEnSearch(getSearchableString(dto.getNameEn()));
//		dto.setNameViGet(getGetableString(dto.getNameVi()));
		getSqlMapClientTemplate().insert("insertGenre", dto);
		dto.setId(getLastInsertId());
		return dto;
	}

	public void updateDeleted(List<Integer> ids) throws DataAccessException {
		if(AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updateGenreDeleted", ids);
	}

	public void update(GenreDto dto) throws DataAccessException {
		dto.setNameViSearch(getSearchableString(dto.getNameVi()));
		dto.setNameEnSearch(getSearchableString(dto.getNameEn()));
//		dto.setNameViGet(getGetableString(dto.getNameVi()));
		getSqlMapClientTemplate().update("updateGenre", dto);
	}

	public List<GenreDto> getAllSimpleGenres(String language) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("getAllSimpleGenres", language);
	}

	public GenreDto getGenreByName(String name, String language) throws DataAccessException {
		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("name", getGetableString(name));
		param.put("name", name);
		param.put("language", language);
		return (GenreDto) getSqlMapClientTemplate().queryForObject("getGenreByName", param);
	}
}

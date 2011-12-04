package net.aihat.dao;

import java.util.List;
import net.aihat.criteria.ComposerSearchCriteria;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.SearchResultDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;

public class ComposerDaoImpl extends BaseDao implements ComposerDao {
	public ComposerDto get(Integer id) throws DataAccessException {
		return (ComposerDto) getSqlMapClientTemplate().queryForObject("getComposer", id);
	}

	public SearchResultDto search(ComposerSearchCriteria criteria) throws DataAccessException {
		if(criteria == null) {
			return SearchResultDto.EMPTY;
		}
		
		ComposerSearchCriteria param = (ComposerSearchCriteria) criteria.clone();
		if(AihatUtils.isExactKeyword(param.getName())) {
			param.setName(AihatUtils.getExactKeyword(param.getName()));
			param.setName(getSearchableString(param.getName()));
		} else {
			param.setName(getSQLSearchableString(param.getName()));
		}
		
		if(criteria.isForCounting()) {
			Long nResults = (Long) getSqlMapClientTemplate().queryForObject("countComposer", param);
			return new SearchResultDto(nResults.intValue());
		} else {
			List<ComposerDto> results = getSqlMapClientTemplate().queryForList("searchComposer", param);
			return new SearchResultDto(results);
		}
		
	}

	public ComposerDto insert(ComposerDto dto) throws DataAccessException {
		dto.setName(dto.getName().trim());
		dto.setNameSearch(getSearchableString(dto.getName()));
//		dto.setNameGet(getGetableString(dto.getName()));
		getSqlMapClientTemplate().insert("insertComposer", dto);
		dto.setId(getLastInsertId());
		return dto;
	}

	public void updateDeleted(List<Integer> ids) throws DataAccessException {
		if(AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updateComposerDeleted", ids);
	}

	public void update(ComposerDto dto) throws DataAccessException {
		dto.setNameSearch(getSearchableString(dto.getName()));
//		dto.setNameGet(getGetableString(dto.getName()));
		getSqlMapClientTemplate().update("updateComposer", dto);
	}

	public void updateImage(ComposerDto dto) throws DataAccessException {
		getSqlMapClientTemplate().update("updateComposerImage", dto);
	}

	public List<ComposerDto> getAllSimpleComposers() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("getAllSimpleComposers");
	}

	public ComposerDto getComposerByName(String name) throws DataAccessException {
//		return (ComposerDto) getSqlMapClientTemplate().queryForObject("getComposerByName", getGetableString(name));
		return (ComposerDto) getSqlMapClientTemplate().queryForObject("getComposerByName", name);
	}
}

package net.aihat.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import net.aihat.criteria.FailedSearchCriteria;
import net.aihat.dto.FailedSearchDto;

public class FailedSearchDaoImpl extends BaseDao implements FailedSearchDao {
	public List<FailedSearchDto> search(FailedSearchCriteria criteria) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("searchFailedSearch", criteria);
	}

	public FailedSearchDto insert(FailedSearchDto dto) throws DataAccessException {
		getSqlMapClientTemplate().insert("insertFailedSearch", dto);
		dto.setId(getLastInsertId());
		return dto;
	}

	
}

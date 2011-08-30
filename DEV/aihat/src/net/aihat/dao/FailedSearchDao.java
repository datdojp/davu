package net.aihat.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import net.aihat.criteria.FailedSearchCriteria;
import net.aihat.dto.FailedSearchDto;

public interface FailedSearchDao {
	public List<FailedSearchDto> search(FailedSearchCriteria criteria) throws DataAccessException;
	public FailedSearchDto insert(FailedSearchDto dto) throws DataAccessException;
}

package net.aihat.service;

import java.util.Date;
import java.util.List;

import net.aihat.criteria.SortCriterion;
import net.aihat.dto.FailedSearchDto;

import org.springframework.dao.DataAccessException;

public interface FailedSearchService {
	public void logFailedSearch(String keyword, Integer userId) throws DataAccessException;
	public List<FailedSearchDto> search(Date fromDate, Date toDate, SortCriterion sortCriterion) throws DataAccessException;
}

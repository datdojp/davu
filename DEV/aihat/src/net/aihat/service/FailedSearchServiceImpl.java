package net.aihat.service;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import net.aihat.criteria.FailedSearchCriteria;
import net.aihat.criteria.SortCriterion;
import net.aihat.dto.FailedSearchDto;
import net.aihat.dto.UserDto;

public class FailedSearchServiceImpl extends BaseService implements FailedSearchService {
	@Transactional(rollbackFor=DataAccessException.class)
	public void logFailedSearch(String keyword, Integer userId) throws DataAccessException {
		FailedSearchDto dto = new FailedSearchDto();
		dto.setKeyword(keyword);
		UserDto user = new UserDto();
		user.setId(userId);
		dto.setUser(user);
		getFailedSearchDao().insert(dto);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public List<FailedSearchDto> search(Date fromDate, Date toDate, SortCriterion sortCriterion) throws DataAccessException {
		FailedSearchCriteria criteria = new FailedSearchCriteria();
		criteria.setFromDate(fromDate);
		criteria.setToDate(toDate);
		criteria.setSortCriterion(sortCriterion);
		return getFailedSearchDao().search(criteria);
	}

}

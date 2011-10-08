package net.aihat.dao;

import java.util.List;

import net.aihat.criteria.ClipCommentSearchCriteria;
import net.aihat.dto.ClipCommentDto;
import org.springframework.dao.DataAccessException;

public interface ClipCommentDao {
	public List<ClipCommentDto> search(ClipCommentSearchCriteria criteria) throws DataAccessException;
	public ClipCommentDto insert(ClipCommentDto clipComment) throws DataAccessException;
	public void delete(ClipCommentDto clipComment) throws DataAccessException;
}

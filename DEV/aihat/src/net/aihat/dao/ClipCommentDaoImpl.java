package net.aihat.dao;

import java.util.List;

import net.aihat.criteria.ClipCommentSearchCriteria;
import net.aihat.dto.ClipCommentDto;
import org.springframework.dao.DataAccessException;

public class ClipCommentDaoImpl extends BaseDao implements ClipCommentDao {
	public List<ClipCommentDto> search(ClipCommentSearchCriteria criteria) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("searchCommentOfClip", criteria);
	}

	public ClipCommentDto insert(ClipCommentDto clipComment) throws DataAccessException {
		return (ClipCommentDto) getSqlMapClientTemplate().insert("insertCommentOfClip", clipComment);
	}

	public void delete(ClipCommentDto clipComment) throws DataAccessException {
		getSqlMapClientTemplate().delete("deleteCommentOfClip", clipComment);
	}

}

package net.aihat.dao;

import java.util.ArrayList;
import java.util.List;

import net.aihat.criteria.ClipCommentSearchCriteria;
import net.aihat.dto.ClipCommentDto;

import org.springframework.dao.DataAccessException;

public class ClipCommentDaoImpl extends BaseDao implements ClipCommentDao {
	public List<ClipCommentDto> search(ClipCommentSearchCriteria criteria) throws DataAccessException {
		List<ClipCommentDto> results = getSqlMapClientTemplate().queryForList("searchCommentOfClip", criteria);
		if(results == null) {
			return new ArrayList<ClipCommentDto>();
		}
		return results;
	}

	public ClipCommentDto insert(ClipCommentDto clipComment) throws DataAccessException {
		getSqlMapClientTemplate().insert("insertCommentOfClip", clipComment);
		clipComment.setId(getLastInsertId());
		ClipCommentDto returned = getTimeAndUserName(clipComment.getId());
		clipComment.getUser().setName(returned.getUser().getName());
		clipComment.setTime(returned.getTime());
		return clipComment;
	}
	
	private ClipCommentDto getTimeAndUserName(int id) throws DataAccessException {
		ClipCommentDto clipComment = (ClipCommentDto) getSqlMapClientTemplate().queryForObject("getClipCommentTimeAndUserName", id);
		if(clipComment == null) {
			return new ClipCommentDto();
		}
		return clipComment;
	}

	public void delete(int id) throws DataAccessException {
		getSqlMapClientTemplate().delete("deleteCommentOfClip", id);
	}

}

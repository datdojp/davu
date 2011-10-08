package net.aihat.service;

import java.util.Date;
import java.util.List;

import net.aihat.criteria.ClipCommentSearchCriteria;
import net.aihat.dto.ClipCommentDto;
import net.aihat.dto.ClipDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatConstants;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class ClipCommentServiceImpl extends BaseService implements ClipCommentService {
	@Transactional(rollbackFor=DataAccessException.class)
	public List<ClipCommentDto> getAllCommentOfClip(int clipId) throws DataAccessException {
		ClipCommentSearchCriteria criteria = new ClipCommentSearchCriteria();
		criteria.setClip(new ClipDto());
		criteria.getClip().setId(clipId);
		return getClipCommentDao().search(criteria);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public List<ClipCommentDto> getCommentOfClipAfter(int clipId, Date fromTime) throws DataAccessException {
		ClipCommentSearchCriteria criteria = new ClipCommentSearchCriteria();
		criteria.setClip(new ClipDto());
		criteria.getClip().setId(clipId);
		criteria.setFromTime(fromTime);
		return getClipCommentDao().search(criteria);
	}
	
	@Transactional(rollbackFor=DataAccessException.class)
	public ClipCommentDto addNewComment(int userId, int clipId, String content) throws DataAccessException {
		ClipCommentDto clipComment = new ClipCommentDto();
		clipComment.setUser(new UserDto());
		clipComment.getUser().setId(userId);
		clipComment.setClip(new ClipDto());
		clipComment.getClip().setId(clipId);
		clipComment.setContent(content);
		return getClipCommentDao().insert(clipComment);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void deleteComment(int userId, int clipId, Date time) throws DataAccessException {
		ClipCommentDto clipComment = new ClipCommentDto();
		clipComment.setUser(new UserDto());
		clipComment.getUser().setId(userId);
		clipComment.setClip(new ClipDto());
		clipComment.getClip().setId(clipId);
		clipComment.setTime(time);
		getClipCommentDao().delete(clipComment);
	}

}

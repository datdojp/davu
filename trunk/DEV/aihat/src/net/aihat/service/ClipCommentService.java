package net.aihat.service;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import net.aihat.dto.ClipCommentDto;

public interface ClipCommentService {
	public List<ClipCommentDto> getAllCommentOfClip(int clipId) throws DataAccessException;
	public List<ClipCommentDto> getCommentOfClipAfter(int clipId, Date fromTime) throws DataAccessException;
	public ClipCommentDto addNewComment(int userId, int clipId, String content) throws DataAccessException;
	public void deleteComment(int userId, int clipId, Date time) throws DataAccessException;
}

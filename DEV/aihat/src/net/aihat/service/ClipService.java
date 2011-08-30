package net.aihat.service;

import java.util.List;

import net.aihat.dto.ClipDto;

import org.springframework.dao.DataAccessException;

public interface ClipService {
	public List<ClipDto> getFeaturedClips(int nFeaturedClips) throws DataAccessException;
	public ClipDto uploadClip(ClipDto clip) throws DataAccessException;
	public void deleteClips(List<ClipDto> clips) throws DataAccessException;
	public ClipDto getClip(Integer id) throws DataAccessException;
	public void updateClip(ClipDto clip) throws DataAccessException;
	public void addView(Integer clipId, Integer userId) throws DataAccessException;
	public void addView(List<Integer> clipIds, Integer userId) throws DataAccessException;
	public void addLiked(Integer clipId, Integer userId) throws DataAccessException;
	public void removeLiked(Integer clipId, Integer userId) throws DataAccessException;
}

package net.aihat.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import net.aihat.dto.FeaturedClipDto;

public interface FeaturedClipService {
	public List<FeaturedClipDto> getAllFeaturedClips() throws DataAccessException;
	public void addFeaturedClip(int id, int order) throws DataAccessException;
	public void updateFeaturedClips(List<FeaturedClipDto> featuredClips) throws DataAccessException;
	public void deleteFeaturedClip(int clipId) throws DataAccessException;
}

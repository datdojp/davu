package net.aihat.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import net.aihat.dto.FeaturedClipDto;

public interface FeaturedClipDao {
	public void insert(FeaturedClipDto featuredClip) throws DataAccessException;
	public List<FeaturedClipDto> search() throws DataAccessException;
	public void update(FeaturedClipDto featuredClip) throws DataAccessException;
	public void delete(FeaturedClipDto featuredClip) throws DataAccessException;
}

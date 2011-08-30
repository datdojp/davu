package net.aihat.service;

import java.util.List;

import net.aihat.dto.SingerDto;

import org.springframework.dao.DataAccessException;

public interface SingerService {
	public SingerDto createSinger(SingerDto singer) throws DataAccessException;
	public void deleteSingers(List<SingerDto> singers) throws DataAccessException;
	public SingerDto getSinger(Integer id) throws DataAccessException;
	public void updateSinger(SingerDto singer) throws DataAccessException;
	public void updateSingerImage(SingerDto singer) throws DataAccessException;
	public List<SingerDto> getAllSimpleSingers() throws DataAccessException;
	public void addFollower(Integer userId, Integer singerId) throws DataAccessException;
	public void removeFollower(Integer userId, Integer singerId) throws DataAccessException;
	public boolean checkFollower(Integer userId, Integer singerId) throws DataAccessException;
	public void addLiked(Integer userId, Integer singerId) throws DataAccessException;
	public void removeLiked(Integer userId, Integer singerId) throws DataAccessException;
	public boolean checkLiked(Integer userId, Integer singerId) throws DataAccessException;
}

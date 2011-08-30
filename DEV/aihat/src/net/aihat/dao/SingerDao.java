package net.aihat.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import net.aihat.criteria.SingerSearchCriteria;
import net.aihat.dto.SearchResultDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;

public interface SingerDao {
	public SingerDto get(Integer id) throws DataAccessException;
	public SearchResultDto search(SingerSearchCriteria criteria) throws DataAccessException;
	public SingerDto insert(SingerDto dto) throws DataAccessException;
	public void updateDeleted(List<Integer> ids) throws DataAccessException;
	public void update(SingerDto dto) throws DataAccessException;
	public void updateImage(SingerDto dto) throws DataAccessException;
	public List<SingerDto> getAllSimpleSingers() throws DataAccessException;
	public SingerDto getSingerByName(String name) throws DataAccessException;
	public void insertUserFollowSinger(UserDto user, SingerDto singer) throws DataAccessException;
	public void deleteUserFollowSinger(UserDto user, SingerDto singer) throws DataAccessException;
	public boolean checkFollower(UserDto user, SingerDto singer) throws DataAccessException;
	public void insertUserLikeSinger(UserDto user, SingerDto singer) throws DataAccessException;
	public void deleteUserLikeSinger(UserDto user, SingerDto singer) throws DataAccessException;
	public boolean checkLiked(UserDto user, SingerDto singer) throws DataAccessException;
}

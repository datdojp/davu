package net.aihat.dao;

import java.util.List;

import net.aihat.criteria.UserSearchCriteria;
import net.aihat.dto.SearchResultDto;
import net.aihat.dto.UserDto;

import org.springframework.dao.DataAccessException;

public interface UserDao {
	public UserDto get(Integer id) throws DataAccessException;
	public UserDto get(String mail) throws DataAccessException;
	public SearchResultDto search(UserSearchCriteria criteria) throws DataAccessException;
	public void updateLastLogin(Integer id) throws DataAccessException;
	public void updateLanguage(Integer id, String language) throws DataAccessException;
	public void updateDeleted(List<Integer> ids) throws DataAccessException;
	public void updateBlocked(List<Integer> ids) throws DataAccessException;
	public void updateUnblocked(List<Integer> ids) throws DataAccessException;
	public void updateImage(UserDto dto) throws DataAccessException;
	public List<UserDto> findFollower(Integer userId) throws DataAccessException;
	public void insertUserFollowUploader(UserDto following, UserDto followed) throws DataAccessException;
	public void deleteUserFollowUploader(UserDto following, UserDto followed) throws DataAccessException;
	public boolean checkFollower(UserDto following, UserDto followed) throws DataAccessException;
	public UserDto insert(UserDto user) throws DataAccessException;
	public void update(UserDto user) throws DataAccessException;
}

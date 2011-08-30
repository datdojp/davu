package net.aihat.service;

import java.util.Date;
import java.util.List;
import net.aihat.dto.UserDto;
import org.springframework.dao.DataAccessException;

public interface UserService {
	public UserDto login(String mail, String password) throws DataAccessException;
	public void updateLanguage(Integer id, String language) throws DataAccessException;
	public void deleteUsers(List<UserDto> users) throws DataAccessException;
	public void blockUsers(List<UserDto> users) throws DataAccessException;
	public void unblockUsers(List<UserDto> users) throws DataAccessException;
	public UserDto getUser(Integer id) throws DataAccessException;
	public void updateUserImage(UserDto user) throws DataAccessException;
	public void resetPassword(List<UserDto> users) throws DataAccessException;
	public void updatePassword(UserDto user, String newPassword) throws DataAccessException;
	public List<UserDto> findFollower(Integer userId) throws DataAccessException;
	public void addFollower(Integer followingId, Integer followedId) throws DataAccessException;
	public void removeFollower(Integer followingId, Integer followedId) throws DataAccessException;
	public boolean checkFollower(Integer followingId, Integer followedId) throws DataAccessException;
	public void registerNewUser(String mail, String rawPassword, String name, String sex, Date birthday) throws DataAccessException;
	public void updateUser(Integer id, String name, String sex, Date birthday) throws DataAccessException;
}

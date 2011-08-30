package net.aihat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aihat.criteria.UserSearchCriteria;
import net.aihat.dto.SearchResultDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;

public class UserDaoImpl extends BaseDao implements UserDao {
	public UserDto get(Integer id) throws DataAccessException {
		return (UserDto) getSqlMapClientTemplate().queryForObject("getUserById", id);
	}
	
	public UserDto get(String mail) throws DataAccessException {
		return (UserDto) getSqlMapClientTemplate().queryForObject("getUserByMail", mail);
	}
	
	public SearchResultDto search(UserSearchCriteria criteria) throws DataAccessException {
		if(criteria == null) {
			return SearchResultDto.EMPTY;
		}
		
		UserSearchCriteria param = (UserSearchCriteria) criteria.clone();
		param.setMail(getSQLSearchableString(param.getMail()));
		
		if(criteria.isForCounting()) {
			Long nResults = (Long) getSqlMapClientTemplate().queryForObject("countUser", param);
			return new SearchResultDto(nResults.intValue());
		} else {
			List<UserDto> results = getSqlMapClientTemplate().queryForList("searchUser", param);
			return new SearchResultDto(results);
		}
	}

	public void updateLastLogin(Integer id) throws DataAccessException {
		getSqlMapClientTemplate().update("updateLastLogin", id);
	}

	public void updateLanguage(Integer id, String language) throws DataAccessException {
		Map param = new HashMap();
		param.put("id", id);
		param.put("language", language);
		getSqlMapClientTemplate().update("updateLanguage", param);
	}

	public void updateDeleted(List<Integer> ids) throws DataAccessException {
		if(AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updateUserDeleted", ids);
	}

	public void updateBlocked(List<Integer> ids) throws DataAccessException {
		if(AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updateUserBlocked", ids);
	}

	public void updateUnblocked(List<Integer> ids) throws DataAccessException {
		if(AihatUtils.isEmpty(ids)) {
			return;
		}
		getSqlMapClientTemplate().update("updateUserUnblocked", ids);
	}

	public void updateImage(UserDto dto) throws DataAccessException {
		getSqlMapClientTemplate().update("updateUserImage", dto);
	}

	public List<UserDto> findFollower(Integer userId) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("findFollower", userId);
	}

	public void insertUserFollowUploader(UserDto following, UserDto followed) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("following", following);
		params.put("followed", followed);
		getSqlMapClientTemplate().insert("insertUserFollowUploader", params);
	}

	public void deleteUserFollowUploader(UserDto following, UserDto followed) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("following", following);
		params.put("followed", followed);
		getSqlMapClientTemplate().delete("deleteUserFollowUploader", params);
		
	}

	public boolean checkFollower(UserDto following, UserDto followed) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("following", following);
		params.put("followed", followed);
		long result = (Long) getSqlMapClientTemplate().queryForObject("checkFollowerForUploader", params);
		return result == 1;
	}

	public UserDto insert(UserDto user) throws DataAccessException {
		getSqlMapClientTemplate().insert("insertUser", user);
		user.setId(getLastInsertId());
		return user;
	}

	public void update(UserDto user) throws DataAccessException {
		getSqlMapClientTemplate().update("updateUser", user);
	}
}

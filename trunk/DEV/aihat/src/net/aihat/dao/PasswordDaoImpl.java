package net.aihat.dao;

import org.springframework.dao.DataAccessException;

import net.aihat.dto.PasswordDto;

public class PasswordDaoImpl extends BaseDao implements PasswordDao {
	public PasswordDto get(Integer id) throws DataAccessException {
		return (PasswordDto) getSqlMapClientTemplate().queryForObject("getPassword", id);
	}

	public void update(PasswordDto dto) throws DataAccessException {
		getSqlMapClientTemplate().update("updatePassword", dto);		
	}

	public PasswordDto insert(PasswordDto password) throws DataAccessException {
		getSqlMapClientTemplate().insert("insertPassword", password);
		password.setId(getLastInsertId());
		return password;
	}
}

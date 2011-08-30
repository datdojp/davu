package net.aihat.dao;

import org.springframework.dao.DataAccessException;

import net.aihat.dto.PasswordDto;

public interface PasswordDao {
	public PasswordDto get(Integer id) throws DataAccessException;
	public void update(PasswordDto dto) throws DataAccessException;
	public PasswordDto insert(PasswordDto password) throws DataAccessException;
}

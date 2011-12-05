package net.aihat.dao;

import java.util.List;

import net.aihat.dto.HomepageTabDto;

import org.springframework.dao.DataAccessException;

public interface HomepageTabDao {
	public List<HomepageTabDto> getAll() throws DataAccessException;
	public void delete(int id) throws DataAccessException;
	public HomepageTabDto insert(HomepageTabDto dto) throws DataAccessException;
	public void update(HomepageTabDto dto) throws DataAccessException;
}

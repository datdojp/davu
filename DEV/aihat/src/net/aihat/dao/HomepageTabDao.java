package net.aihat.dao;

import java.util.List;

import net.aihat.dto.HomepageTabDto;

import org.springframework.dao.DataAccessException;

public interface HomepageTabDao {
	public List<HomepageTabDto> getAllHomepageTab() throws DataAccessException;
}

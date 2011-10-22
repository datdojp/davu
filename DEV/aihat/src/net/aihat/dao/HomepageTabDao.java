package net.aihat.dao;

import java.util.List;

import net.aihat.dto.HomePageTabDto;

import org.springframework.dao.DataAccessException;

public interface HomepageTabDao {
	public List<HomePageTabDto> getAllHomepageTab() throws DataAccessException;
}

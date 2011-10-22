package net.aihat.service;

import java.util.List;
import net.aihat.dto.HomePageTabDto;
import org.springframework.dao.DataAccessException;

public interface HomepageTabService {
	public List<HomePageTabDto> getAllHomepageTab() throws DataAccessException;
	public HomePageTabDto loadHomepageTabContent(HomePageTabDto homePageTab) throws DataAccessException;
}

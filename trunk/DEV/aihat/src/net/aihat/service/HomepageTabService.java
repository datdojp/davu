package net.aihat.service;

import java.util.List;
import net.aihat.dto.HomepageTabDto;
import org.springframework.dao.DataAccessException;

public interface HomepageTabService {
	public List<HomepageTabDto> getAllHomepageTab() throws DataAccessException;
	public HomepageTabDto loadHomepageTabContent(HomepageTabDto homePageTab) throws DataAccessException;
}

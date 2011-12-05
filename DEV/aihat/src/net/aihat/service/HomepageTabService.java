package net.aihat.service;

import java.util.List;
import net.aihat.dto.HomepageTabDto;
import org.springframework.dao.DataAccessException;

public interface HomepageTabService {
	public List<HomepageTabDto> getAllHomepageTab() throws DataAccessException;
	public HomepageTabDto loadHomepageTabContent(HomepageTabDto homePageTab) throws DataAccessException;
	public void deleteHomepageTab(int tabId) throws DataAccessException;
	public HomepageTabDto createOrUpdateHomepageTab(Integer id, Integer order, String titleVi, String titleEn, String genres, String topSingers,
			String topPlaylists, String recommendedClips, String topUploaders) throws DataAccessException;
}

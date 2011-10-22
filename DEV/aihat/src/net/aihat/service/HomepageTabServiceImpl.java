package net.aihat.service;

import java.util.ArrayList;
import java.util.List;

import net.aihat.criteria.GenreSearchCriteria;
import net.aihat.dto.HomePageTabDto;
import net.aihat.utils.AihatUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class HomepageTabServiceImpl extends BaseService implements HomepageTabService {
	@Transactional(rollbackFor=DataAccessException.class)
	public List<HomePageTabDto> getAllHomepageTab() throws DataAccessException {
		return getHomepageTabDao().getAllHomepageTab();
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public HomePageTabDto loadHomepageTabContent(HomePageTabDto homePageTab) throws DataAccessException {
		if(homePageTab != null) {
			//load listGenres
			if(!AihatUtils.isEmpty(homePageTab.getGenres())) {
				String[] splitted = homePageTab.getGenres().split(" ");
				GenreSearchCriteria criteria = new GenreSearchCriteria();
				criteria.setIds(new ArrayList<Integer>());
				for(String aGenreId : splitted) {
					if(!AihatUtils.isEmpty(aGenreId)) {
						criteria.getIds().add(Integer.parseInt(aGenreId));
					}
				}
				homePageTab.setListGenres(getGenreDao().search(criteria).getResults());
			}
			//load listTopSingers
			if(!AihatUtils.isEmpty(homePageTab.getTopSingers())) {
			}
			//load listTopPlaylists
			//load listRecommendedClips
			//load listTopUploaders
		}
		return homePageTab;
	}

}

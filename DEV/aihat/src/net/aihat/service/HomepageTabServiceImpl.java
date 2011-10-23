package net.aihat.service;

import java.util.ArrayList;
import java.util.List;

import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.criteria.GenreSearchCriteria;
import net.aihat.criteria.PagingCriterion;
import net.aihat.criteria.PlaylistSearchCriteria;
import net.aihat.criteria.SingerSearchCriteria;
import net.aihat.criteria.SortCriterion;
import net.aihat.criteria.UserSearchCriteria;
import net.aihat.dto.HomePageTabDto;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;
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
			SingerSearchCriteria singerSearchCriteria = new SingerSearchCriteria();
			if(!AihatUtils.isEmpty(homePageTab.getTopSingers())) {
				String[] splitted = homePageTab.getTopSingers().split(" ");
				singerSearchCriteria.setIds(new ArrayList<Integer>());
				for(String aSingerId : splitted) {
					if(!AihatUtils.isEmpty(aSingerId)) {
						singerSearchCriteria.getIds().add(Integer.parseInt(aSingerId));
					}
				}
			} else {
				singerSearchCriteria.setPagingCriterion(new PagingCriterion());
				singerSearchCriteria.getPagingCriterion().setOffset(0l);
				singerSearchCriteria.getPagingCriterion().setRowCount(Long.parseLong(BeanUtils.getConfig("client.homepage.nTopSingers")));
				singerSearchCriteria.setSortCriterion(new SortCriterion());
				singerSearchCriteria.getSortCriterion().setColumnName("nViews");
				singerSearchCriteria.getSortCriterion().setOrder(SortCriterion.ORDER_DESCENDING);
			}
			homePageTab.setListTopSingers(getSingerDao().search(singerSearchCriteria).getResults());
			
			//load listTopPlaylists
			PlaylistSearchCriteria playlistSearchCriteria = new PlaylistSearchCriteria();
			if(!AihatUtils.isEmpty(homePageTab.getTopPlaylists())) {
				String[] splitted = homePageTab.getTopPlaylists().split(" ");
				playlistSearchCriteria.setIds(new ArrayList<Integer>());
				for(String aPlaylistId : splitted) {
					if(!AihatUtils.isEmpty(aPlaylistId)) {
						playlistSearchCriteria.getIds().add(Integer.parseInt(aPlaylistId));
					}
				}
			} else {
				playlistSearchCriteria.setPagingCriterion(new PagingCriterion());
				playlistSearchCriteria.getPagingCriterion().setOffset(0l);
				playlistSearchCriteria.getPagingCriterion().setRowCount(Long.parseLong(BeanUtils.getConfig("playlistSearchCriteria")));
				playlistSearchCriteria.setSortCriterion(new SortCriterion());
				playlistSearchCriteria.getSortCriterion().setColumnName("nViews");
				playlistSearchCriteria.getSortCriterion().setOrder(SortCriterion.ORDER_DESCENDING);
			}
			homePageTab.setListTopPlaylists(getPlaylistDao().search(playlistSearchCriteria).getResults());
			
			//load listRecommendedClips
			if(!AihatUtils.isEmpty(homePageTab.getRecommendedClips())) {
				String[] splitted = homePageTab.getRecommendedClips().split(" ");
				ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
				clipSearchCriteria.setIds(new ArrayList<Integer>());
				for(String aRecommededClipId : splitted) {
					if(!AihatUtils.isEmpty(aRecommededClipId)) {
						clipSearchCriteria.getIds().add(Integer.parseInt(aRecommededClipId));
					}
				}
				
				homePageTab.setListRecommendedClips(getClipDao().search(clipSearchCriteria).getResults());
			}
			
			//load listTopUploaders
			UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
			if(!AihatUtils.isEmpty(homePageTab.getTopUploaders())) {
				String[] splitted = homePageTab.getTopUploaders().split(" ");
				userSearchCriteria.setIds(new ArrayList<Integer>());
				for(String anUploaderId : splitted) {
					userSearchCriteria.getIds().add(Integer.parseInt(anUploaderId));
				}
			} else {
				userSearchCriteria.setPagingCriterion(new PagingCriterion());
				userSearchCriteria.getPagingCriterion().setOffset(0l);
				userSearchCriteria.getPagingCriterion().setRowCount(Long.parseLong(BeanUtils.getConfig("client.homepate.nTopUploaders")));
			}
			homePageTab.setListTopUploaders(getUserDao().search(userSearchCriteria).getResults());
		}
		return homePageTab;
	}

}

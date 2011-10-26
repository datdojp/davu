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
import net.aihat.dto.HomepageTabDto;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class HomepageTabServiceImpl extends BaseService implements HomepageTabService {
	@Transactional(rollbackFor=DataAccessException.class)
	public List<HomepageTabDto> getAllHomepageTab() throws DataAccessException {
		return getHomepageTabDao().getAllHomepageTab();
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public HomepageTabDto loadHomepageTabContent(HomepageTabDto homePageTab) throws DataAccessException {
		if(homePageTab != null) {
			List<Integer> genreIds = new ArrayList<Integer>();
			
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
				genreIds.addAll(criteria.getIds());
				homePageTab.setListGenres(getGenreDao().search(criteria).getResults());
			}
			
			//load listTopSingers
			if(!AihatUtils.isEmpty(homePageTab.getTopSingers())) {
				String[] splitted = homePageTab.getTopSingers().split(" ");
				SingerSearchCriteria singerSearchCriteria = new SingerSearchCriteria();
				singerSearchCriteria.setIds(new ArrayList<Integer>());
				for(String aSingerId : splitted) {
					if(!AihatUtils.isEmpty(aSingerId)) {
						singerSearchCriteria.getIds().add(Integer.parseInt(aSingerId));
					}
				}
				homePageTab.setListTopSingers(getSingerDao().search(singerSearchCriteria).getResults());
			}
			
			//load listTopPlaylists
			if(!AihatUtils.isEmpty(homePageTab.getTopPlaylists())) {
				String[] splitted = homePageTab.getTopPlaylists().split(" ");
				PlaylistSearchCriteria playlistSearchCriteria = new PlaylistSearchCriteria();
				playlistSearchCriteria.setIds(new ArrayList<Integer>());
				for(String aPlaylistId : splitted) {
					if(!AihatUtils.isEmpty(aPlaylistId)) {
						playlistSearchCriteria.getIds().add(Integer.parseInt(aPlaylistId));
					}
				}
				homePageTab.setListTopPlaylists(getPlaylistDao().search(playlistSearchCriteria).getResults());
			}
			
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
			if(!AihatUtils.isEmpty(homePageTab.getTopUploaders())) {
				String[] splitted = homePageTab.getTopUploaders().split(" ");
				UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
				userSearchCriteria.setIds(new ArrayList<Integer>());
				for(String anUploaderId : splitted) {
					userSearchCriteria.getIds().add(Integer.parseInt(anUploaderId));
				}
				homePageTab.setListTopUploaders(getUserDao().search(userSearchCriteria).getResults());
			}
			
			
			//load listTopViewClips
			ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
			clipSearchCriteria.setGenreIds(genreIds);
			clipSearchCriteria.setPagingCriterion(new PagingCriterion());
			clipSearchCriteria.getPagingCriterion().setOffset(0l);
			clipSearchCriteria.getPagingCriterion().setRowCount(Long.parseLong(BeanUtils.getConfig("client.homepage.nTopViewClips")));
			clipSearchCriteria.setSortCriterion(new SortCriterion());
			clipSearchCriteria.getSortCriterion().setColumnName("nViews");
			clipSearchCriteria.getSortCriterion().setOrder(SortCriterion.ORDER_DESCENDING);
			homePageTab.setListTopViewClips(getClipDao().search(clipSearchCriteria).getResults());
			
			//load listNewUploadedClips
			clipSearchCriteria = new ClipSearchCriteria();
			clipSearchCriteria.setGenreIds(genreIds);
			clipSearchCriteria.setPagingCriterion(new PagingCriterion());
			clipSearchCriteria.getPagingCriterion().setOffset(0l);
			clipSearchCriteria.getPagingCriterion().setRowCount(Long.parseLong(BeanUtils.getConfig("client.homepage.nNewUploadedClips")));
			clipSearchCriteria.setSortCriterion(new SortCriterion());
			clipSearchCriteria.getSortCriterion().setColumnName("createdTime");
			clipSearchCriteria.getSortCriterion().setOrder(SortCriterion.ORDER_DESCENDING);
			homePageTab.setListNewUploadedClips(getClipDao().search(clipSearchCriteria).getResults());
		}
		homePageTab.setLoaded(true);
		return homePageTab;
	}

}

package net.aihat.service;

import java.util.ArrayList;
import net.aihat.dto.PlaylistDto;
import java.util.List;
import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.criteria.GenreSearchCriteria;
import net.aihat.criteria.PagingCriterion;
import net.aihat.criteria.PlaylistSearchCriteria;
import net.aihat.criteria.SingerSearchCriteria;
import net.aihat.criteria.SortCriterion;
import net.aihat.criteria.UserSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.HomepageTabDto;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class HomepageTabServiceImpl extends BaseService implements HomepageTabService {
	@Transactional(rollbackFor=DataAccessException.class)
	public List<HomepageTabDto> getAllHomepageTab() throws DataAccessException {
		return getHomepageTabDao().getAll();
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public HomepageTabDto loadHomepageTabContent(HomepageTabDto homePageTab) throws DataAccessException {
		if(homePageTab != null) {
			List<Integer> genreIds = new ArrayList<Integer>();
			
			//load listGenres
			if(!AihatUtils.isEmpty(homePageTab.getGenres())) {
				String[] splitted = homePageTab.getGenres().split(",");
				GenreSearchCriteria criteria = new GenreSearchCriteria();
				criteria.setIds(new ArrayList<Integer>());
				for(String aGenreId : splitted) {
					if(!AihatUtils.isEmpty(aGenreId)) {
						criteria.getIds().add(Integer.parseInt(aGenreId.trim()));
					}
				}
				genreIds.addAll(criteria.getIds());
				homePageTab.setListGenres(getGenreDao().search(criteria).getResults());
			}
			
			//load listTopSingers
			if(!AihatUtils.isEmpty(homePageTab.getTopSingers())) {
				String[] splitted = homePageTab.getTopSingers().split(",");
				SingerSearchCriteria singerSearchCriteria = new SingerSearchCriteria();
				singerSearchCriteria.setIds(new ArrayList<Integer>());
				for(String aSingerId : splitted) {
					if(!AihatUtils.isEmpty(aSingerId)) {
						singerSearchCriteria.getIds().add(Integer.parseInt(aSingerId.trim()));
					}
				}
				homePageTab.setListTopSingers(getSingerDao().search(singerSearchCriteria).getResults());
			}
			
			//load listTopPlaylists
			if(!AihatUtils.isEmpty(homePageTab.getTopPlaylists())) {
				String[] splitted = homePageTab.getTopPlaylists().split(",");
				PlaylistSearchCriteria playlistSearchCriteria = new PlaylistSearchCriteria();
				playlistSearchCriteria.setIds(new ArrayList<Integer>());
				for(String aPlaylistId : splitted) {
					if(!AihatUtils.isEmpty(aPlaylistId)) {
						playlistSearchCriteria.getIds().add(Integer.parseInt(aPlaylistId.trim()));
					}
				}
				homePageTab.setListTopPlaylists(getPlaylistDao().search(playlistSearchCriteria).getResults());
				for(PlaylistDto aPl : homePageTab.getListTopPlaylists()) {
					ClipDto mainClip = getPlaylistDao().getMainClip(aPl);
					aPl.setMainClip(mainClip);
				}
			}
			
			//load listRecommendedClips
			if(!AihatUtils.isEmpty(homePageTab.getRecommendedClips())) {
				String[] splitted = homePageTab.getRecommendedClips().split(",");
				ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
				clipSearchCriteria.setIds(new ArrayList<Integer>());
				for(String aRecommededClipId : splitted) {
					if(!AihatUtils.isEmpty(aRecommededClipId)) {
						clipSearchCriteria.getIds().add(Integer.parseInt(aRecommededClipId.trim()));
					}
				}
				homePageTab.setListRecommendedClips(getClipDao().search(clipSearchCriteria).getResults());
			}
			
			//load listTopUploaders
			if(!AihatUtils.isEmpty(homePageTab.getTopUploaders())) {
				String[] splitted = homePageTab.getTopUploaders().split(",");
				UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
				userSearchCriteria.setIds(new ArrayList<Integer>());
				for(String anUploaderId : splitted) {
					userSearchCriteria.getIds().add(Integer.parseInt(anUploaderId.trim()));
				}
				homePageTab.setListTopUploaders(getUserDao().search(userSearchCriteria).getResults());
			}
			
			
			//load listTopViewClips
			ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
			clipSearchCriteria.setGenreIds(genreIds);
			clipSearchCriteria.setFetchTodayViews(true);
			clipSearchCriteria.setPagingCriterion(new PagingCriterion());
			clipSearchCriteria.getPagingCriterion().setOffset(0l);
			clipSearchCriteria.getPagingCriterion().setRowCount(Long.parseLong(BeanUtils.getConfig("client.homepage.nTopViewClips")));
			clipSearchCriteria.setSortCriterion(new SortCriterion());
			clipSearchCriteria.getSortCriterion().setColumnName("nTodayViews");
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
	
	@Transactional(rollbackFor=DataAccessException.class)	
	public void deleteHomepageTab(int tabId) throws DataAccessException {
		getHomepageTabDao().delete(tabId);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public HomepageTabDto createOrUpdateHomepageTab(Integer id, Integer order, String titleVi,
			String titleEn, String genres, String topSingers,
			String topPlaylists, String recommendedClips, String topUploaders)
			throws DataAccessException {
		HomepageTabDto dto = new HomepageTabDto();
		dto.setId(id);
		dto.setOrder(order);
		dto.setTitleVi(titleVi);
		dto.setTitleEn(titleEn);
		dto.setGenres(genres);
		dto.setTopSingers(topSingers);
		dto.setTopPlaylists(topPlaylists);
		dto.setRecommendedClips(recommendedClips);
		dto.setTopUploaders(topUploaders);
		
		if(id == null) {
			return getHomepageTabDao().insert(dto);
		} else {
			getHomepageTabDao().update(dto);
			return dto;
		}
	}
}

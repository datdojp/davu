package net.aihat.service;

import java.util.List;

import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.criteria.ComposerSearchCriteria;
import net.aihat.criteria.GenreSearchCriteria;
import net.aihat.criteria.PagingCriterion;
import net.aihat.criteria.PlaylistSearchCriteria;
import net.aihat.criteria.SingerSearchCriteria;
import net.aihat.criteria.SortCriterion;
import net.aihat.criteria.UserSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.SearchResultDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class SearchServiceImpl extends BaseService implements SearchService {
	@Transactional(rollbackFor=DataAccessException.class)
	public SearchResultDto searchSingers(Integer id, String name, String userMail, Integer logginedUserId, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting, Integer followedBy) throws DataAccessException {
		SingerSearchCriteria criteria = new SingerSearchCriteria();

		if(AihatUtils.isValidId(id)) {
			criteria.setId(id);
		}
		if(!AihatUtils.isEmpty(name)) {
			criteria.setName(name);
		}

		if(!AihatUtils.isEmpty(userMail)) {
			criteria.setUser(new UserDto());
			criteria.getUser().setMail(userMail);
		}

		criteria.setSortCriterion(sortCriterion);
		criteria.setPagingCriterion(pagingCriterion);
		criteria.setForCounting(forCounting);
		criteria.setFollowedBy(followedBy);
		
		SearchResultDto result = getSingerDao().search(criteria);
		
		//check if each singer is followed by the current logged-in user
		if(AihatUtils.isValidId(logginedUserId)) {
			UserDto user = new UserDto();
			user.setId(logginedUserId);
			if(!AihatUtils.isEmpty(result.getResults())) {
				for(Object obj : result.getResults()) {
					SingerDto aSinger = (SingerDto) obj;
					aSinger.setFollowed(getSingerDao().checkFollower(user, aSinger));
					aSinger.setLiked(getSingerDao().checkLiked(user, aSinger));
				}
			}
		}
		
		return result;
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public SearchResultDto searchComposers(Integer id, String name, String userMail, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting) throws DataAccessException {
		ComposerSearchCriteria criteria = new ComposerSearchCriteria();

		if(AihatUtils.isValidId(id)) {
			criteria.setId(id);
		}

		if(!AihatUtils.isEmpty(name)) {
			criteria.setName(name);
		}

		if(!AihatUtils.isEmpty(userMail)) {
			criteria.setUser(new UserDto());
			criteria.getUser().setMail(userMail);
		}

		criteria.setSortCriterion(sortCriterion);
		criteria.setPagingCriterion(pagingCriterion);
		criteria.setForCounting(forCounting);

		return getComposerDao().search(criteria);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public SearchResultDto searchGenres(Integer id, String name, String language, String userMail, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting) throws DataAccessException {
		GenreSearchCriteria criteria = new GenreSearchCriteria();

		if(AihatUtils.isValidId(id)) {
			criteria.setId(id);
		}

		if(!AihatUtils.isEmpty(name)) {
			criteria.setName(name);
		}

		if(!AihatUtils.isEmpty(language)) {
			criteria.setLanguage(language);
		}

		if(!AihatUtils.isEmpty(userMail)) {
			criteria.setUser(new UserDto());
			criteria.getUser().setMail(userMail);
		}

		criteria.setSortCriterion(sortCriterion);
		criteria.setPagingCriterion(pagingCriterion);
		criteria.setForCounting(forCounting);

		return getGenreDao().search(criteria);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public SearchResultDto searchUsers(Integer id, String mail, Boolean isBlocked, Boolean isAdmin, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting, Integer followedBy) throws DataAccessException {
		UserSearchCriteria criteria = new UserSearchCriteria();

		if(AihatUtils.isValidId(id)) {
			criteria.setId(id);
		}

		if(!AihatUtils.isEmpty(mail)) {
			criteria.setMail(mail);
		}

		if(isBlocked != null) {
			criteria.setBlocked(isBlocked);
		}

		if(isAdmin != null) {
			criteria.setAdmin(isAdmin);
		}

		criteria.setSortCriterion(sortCriterion);
		criteria.setPagingCriterion(pagingCriterion);
		criteria.setForCounting(forCounting);
		criteria.setFollowedBy(followedBy);

		return getUserDao().search(criteria);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public SearchResultDto searchClips(Integer id, String title, Integer singerId, Integer composerId, Integer genreId, Integer playlistId, 
			String userMail, Integer logginedUserId, Boolean official, Boolean hasDuplicate, SortCriterion sortCriterion,
			PagingCriterion pagingCriterion, boolean forCounting, Integer likedBy, Integer notifiedUserId) throws DataAccessException {
		ClipSearchCriteria criteria = new ClipSearchCriteria();

		if(AihatUtils.isValidId(id)) {
			criteria.setId(id);
		}

		if(!AihatUtils.isEmpty(title)) {
			criteria.setTitle(title);
		}

		if(AihatUtils.isValidId(singerId)) {
			SingerDto singer = new SingerDto();
			singer.setId(singerId);
			criteria.setSinger(singer);
		}

		if(AihatUtils.isValidId(composerId)) {
			ComposerDto composer = new ComposerDto();
			composer.setId(composerId);
			criteria.setComposer(composer);
		}

		if(AihatUtils.isValidId(genreId)) {
			GenreDto genre = new GenreDto();
			genre.setId(genreId);
			criteria.setGenre(genre);
		}
		
		if(AihatUtils.isValidId(playlistId)) {
			PlaylistDto playlist = new PlaylistDto();
			playlist.setId(playlistId);
			criteria.setPlaylist(playlist);
		}
		
		if(!AihatUtils.isEmpty(userMail)) {
			criteria.setUser(new UserDto());
			criteria.getUser().setMail(userMail);
		}

		if(AihatUtils.isValidId(likedBy)) {
			UserDto user = new UserDto();
			user.setId(likedBy);
			criteria.setLikedBy(user);
		}
		
		if(AihatUtils.isValidId(notifiedUserId)) {
			UserDto notifiedUser = new UserDto();
			notifiedUser.setId(notifiedUserId);
			criteria.setNotifiedUser(notifiedUser);
		}
		
		criteria.setOfficial(official);
		criteria.setHasDuplicate(hasDuplicate);
		criteria.setSortCriterion(sortCriterion);
		criteria.setPagingCriterion(pagingCriterion);
		criteria.setForCounting(forCounting);

		SearchResultDto result = getClipDao().search(criteria);
		
		//check if each singer is liked by the current logged-in user
		if(AihatUtils.isValidId(logginedUserId)) {
			UserDto user = new UserDto();
			user.setId(logginedUserId);
			if(!AihatUtils.isEmpty(result.getResults())) {
				for(Object obj : result.getResults()) {
					ClipDto aClip = (ClipDto) obj;
					aClip.setLiked(getClipDao().checkLiked(aClip, user));	
				}
			}
		}
		
		return result;
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public SearchResultDto searchPlaylists(Integer id, String name, Integer userId, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting) throws DataAccessException {
		PlaylistSearchCriteria criteria = new PlaylistSearchCriteria();

		if(AihatUtils.isValidId(id)) {
			criteria.setId(id);
		}
		if(!AihatUtils.isEmpty(name)) {
			criteria.setName(name);
		}

		if(AihatUtils.isValidId(userId)) {
			criteria.setUser(new UserDto());
			criteria.getUser().setId(userId);
		}

		criteria.setSortCriterion(sortCriterion);
		criteria.setPagingCriterion(pagingCriterion);
		criteria.setForCounting(forCounting);
		
		SearchResultDto result = getPlaylistDao().search(criteria);
		
		//set the main clip
		List<PlaylistDto> playlists = result.getResults();
		if(!AihatUtils.isEmpty(playlists)) {
			for(PlaylistDto aPlaylist : playlists) {
				ClipDto mainClip = getPlaylistDao().getMainClip(aPlaylist);
				aPlaylist.setMainClip(mainClip);
			}
		}
		
		return result;
	}
}

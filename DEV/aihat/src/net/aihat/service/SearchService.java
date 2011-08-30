package net.aihat.service;

import net.aihat.criteria.PagingCriterion;
import net.aihat.criteria.SortCriterion;
import net.aihat.dto.SearchResultDto;

import org.springframework.dao.DataAccessException;

public interface SearchService {
	public SearchResultDto searchClips(Integer id, String title, Integer singerId, Integer composerId, Integer genreId, Integer playlistId, String userMail, Integer logginedUserId, Boolean official, Boolean hasDuplicate, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting, Integer likedBy, Integer notifiedUserId) throws DataAccessException;
	public SearchResultDto searchSingers(Integer id, String name, String userMail, Integer logginedUserId, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting, Integer followedBy) throws DataAccessException;
	public SearchResultDto searchComposers(Integer id, String name, String userMail, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting) throws DataAccessException;
	public SearchResultDto searchGenres(Integer id, String name, String language, String userMail, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting) throws DataAccessException;
	public SearchResultDto searchUsers(Integer id, String mail, Boolean isBlocked, Boolean isAdmin, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting, Integer followedBy) throws DataAccessException;
	public SearchResultDto searchPlaylists(Integer id, String name, Integer userId, SortCriterion sortCriterion, PagingCriterion pagingCriterion, boolean forCounting) throws DataAccessException;
}
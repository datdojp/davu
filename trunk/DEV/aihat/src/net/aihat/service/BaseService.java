package net.aihat.service;

import java.io.Serializable;

import net.aihat.dao.ClipCommentDao;
import net.aihat.dao.ClipDao;
import net.aihat.dao.ComposerDao;
import net.aihat.dao.FailedSearchDao;
import net.aihat.dao.FeaturedClipDao;
import net.aihat.dao.GenreDao;
import net.aihat.dao.HomepageTabDao;
import net.aihat.dao.PasswordDao;
import net.aihat.dao.PlaylistDao;
import net.aihat.dao.SingerDao;
import net.aihat.dao.UserDao;

public abstract class BaseService implements Serializable {
	private ClipDao clipDao;
	private SingerDao singerDao;
	private ComposerDao composerDao;
	private GenreDao genreDao;
	private UserDao userDao;
	private PasswordDao passwordDao;
	private FailedSearchDao failedSearchDao;
	private PlaylistDao playlistDao;
	private FeaturedClipDao featuredClipDao;
	private ClipCommentDao clipCommentDao;
	private HomepageTabDao homepageTabDao;
	
	//getter setter
	public ClipDao getClipDao() {
		return clipDao;
	}

	public void setClipDao(ClipDao clipDao) {
		this.clipDao = clipDao;
	}

	public SingerDao getSingerDao() {
		return singerDao;
	}

	public void setSingerDao(SingerDao singerDao) {
		this.singerDao = singerDao;
	}

	public ComposerDao getComposerDao() {
		return composerDao;
	}

	public void setComposerDao(ComposerDao composerDao) {
		this.composerDao = composerDao;
	}

	public GenreDao getGenreDao() {
		return genreDao;
	}

	public void setGenreDao(GenreDao genreDao) {
		this.genreDao = genreDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PasswordDao getPasswordDao() {
		return passwordDao;
	}

	public void setPasswordDao(PasswordDao passwordDao) {
		this.passwordDao = passwordDao;
	}

	public FailedSearchDao getFailedSearchDao() {
		return failedSearchDao;
	}

	public void setFailedSearchDao(FailedSearchDao failedSearchDao) {
		this.failedSearchDao = failedSearchDao;
	}

	public PlaylistDao getPlaylistDao() {
		return playlistDao;
	}

	public void setPlaylistDao(PlaylistDao playlistDao) {
		this.playlistDao = playlistDao;
	}

	public FeaturedClipDao getFeaturedClipDao() {
		return featuredClipDao;
	}

	public void setFeaturedClipDao(FeaturedClipDao featuredClipDao) {
		this.featuredClipDao = featuredClipDao;
	}

	public ClipCommentDao getClipCommentDao() {
		return clipCommentDao;
	}

	public void setClipCommentDao(ClipCommentDao clipCommentDao) {
		this.clipCommentDao = clipCommentDao;
	}

	public HomepageTabDao getHomepageTabDao() {
		return homepageTabDao;
	}

	public void setHomepageTabDao(HomepageTabDao homepageTabDao) {
		this.homepageTabDao = homepageTabDao;
	}
	
}

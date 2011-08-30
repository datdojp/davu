package net.aihat.criteria;

import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;

public class ClipSearchCriteria extends BaseCriteria {
	private String title;
	private SingerDto singer;
	private ComposerDto composer;
	private GenreDto genre;
	private PlaylistDto playlist;
	private UserDto user;
	private Boolean official;
	private Boolean hasDuplicate;
	private UserDto likedBy;
	private UserDto notifiedUser;
	
	//getter setter
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public SingerDto getSinger() {
		return singer;
	}
	public void setSinger(SingerDto singer) {
		this.singer = singer;
	}
	public ComposerDto getComposer() {
		return composer;
	}
	public void setComposer(ComposerDto composer) {
		this.composer = composer;
	}
	public GenreDto getGenre() {
		return genre;
	}
	public void setGenre(GenreDto genre) {
		this.genre = genre;
	}
	public PlaylistDto getPlaylist() {
		return playlist;
	}
	public void setPlaylist(PlaylistDto playlist) {
		this.playlist = playlist;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public Boolean getOfficial() {
		return official;
	}
	public void setOfficial(Boolean official) {
		this.official = official;
	}
	public Boolean isHasDuplicate() {
		return hasDuplicate;
	}
	public void setHasDuplicate(Boolean hasDuplicate) {
		this.hasDuplicate = hasDuplicate;
	}
	
	public Boolean getHasDuplicate() {
		return hasDuplicate;
	}
	public UserDto getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(UserDto likedBy) {
		this.likedBy = likedBy;
	}
	public UserDto getNotifiedUser() {
		return notifiedUser;
	}
	public void setNotifiedUser(UserDto notifiedUser) {
		this.notifiedUser = notifiedUser;
	}
	
}

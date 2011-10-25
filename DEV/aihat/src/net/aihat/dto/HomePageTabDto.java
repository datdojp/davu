package net.aihat.dto;

import java.util.List;

public class HomePageTabDto extends BaseDto {
	private Integer order;
	private String titleVi;
	private String titleEn;
	private String genres;
	private List<GenreDto> listGenres;
	private	String topSingers;
	private List<SingerDto> listTopSingers;
	private String topPlaylists;
	private List<PlaylistDto> listTopPlaylists;
	private String recommendedClips;
	private List<ClipDto> listRecommendedClips;
	private String topUploaders;
	private List<UserDto> listTopUploaders;
	
	//transient
	private boolean loaded = false;
	private List<ClipDto> listTopViewClips;
	private List<ClipDto> listNewUploadedClips;
	
	//TODO: implement it
	public String forLog() {
		// TODO Auto-generated method stub
		return null;
	}

	//getter setter
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getTitleVi() {
		return titleVi;
	}

	public void setTitleVi(String titleVi) {
		this.titleVi = titleVi;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public List<GenreDto> getListGenres() {
		return listGenres;
	}

	public void setListGenres(List<GenreDto> listGenres) {
		this.listGenres = listGenres;
	}

	public String getTopSingers() {
		return topSingers;
	}

	public void setTopSingers(String topSingers) {
		this.topSingers = topSingers;
	}

	public List<SingerDto> getListTopSingers() {
		return listTopSingers;
	}

	public void setListTopSingers(List<SingerDto> listTopSingers) {
		this.listTopSingers = listTopSingers;
	}

	public String getTopPlaylists() {
		return topPlaylists;
	}

	public void setTopPlaylists(String topPlaylists) {
		this.topPlaylists = topPlaylists;
	}

	public List<PlaylistDto> getListTopPlaylists() {
		return listTopPlaylists;
	}

	public void setListTopPlaylists(List<PlaylistDto> listTopPlaylists) {
		this.listTopPlaylists = listTopPlaylists;
	}

	public String getRecommendedClips() {
		return recommendedClips;
	}

	public void setRecommendedClips(String recommendedClips) {
		this.recommendedClips = recommendedClips;
	}

	public List<ClipDto> getListRecommendedClips() {
		return listRecommendedClips;
	}

	public void setListRecommendedClips(List<ClipDto> listRecommendedClips) {
		this.listRecommendedClips = listRecommendedClips;
	}

	public String getTopUploaders() {
		return topUploaders;
	}

	public void setTopUploaders(String topUploaders) {
		this.topUploaders = topUploaders;
	}

	public List<UserDto> getListTopUploaders() {
		return listTopUploaders;
	}

	public void setListTopUploaders(List<UserDto> listTopUploaders) {
		this.listTopUploaders = listTopUploaders;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public List<ClipDto> getListTopViewClips() {
		return listTopViewClips;
	}

	public void setListTopViewClips(List<ClipDto> listTopViewClips) {
		this.listTopViewClips = listTopViewClips;
	}

	public List<ClipDto> getListNewUploadedClips() {
		return listNewUploadedClips;
	}

	public void setListNewUploadedClips(List<ClipDto> listNewUploadedClips) {
		this.listNewUploadedClips = listNewUploadedClips;
	}
	
}

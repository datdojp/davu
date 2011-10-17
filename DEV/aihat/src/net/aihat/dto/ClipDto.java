package net.aihat.dto;

import java.util.List;
import net.aihat.utils.AihatUtils;

public class ClipDto extends BaseUserCareDto {
	private String title;
	private String titleSearch;
	private List<SingerDto> singers;
	private List<ComposerDto> composers;
	private List<GenreDto> genres;
	private String link;
	private Boolean official;
	private Integer nViews;
	private Integer nFans;
	private List<ClipCommentDto> comments;
	
	//this field is to determine that singer is liked by current logged-in user
	private Boolean liked = Boolean.FALSE;
	
	//order of the clip in the current displayed playlist
	private Integer orderInPlaylist;

	//clip 's links
	public String getEmbeddedLink() {
		if(!AihatUtils.isEmpty(link)) {
			return "http://www.youtube.com/v/" + link;
		} else {
			return "";
		}
	}
	public String getThumbnailLink() {
		if(!AihatUtils.isEmpty(link)) {
			return "http://img.youtube.com/vi/" + link + "/0.jpg";
		} else {
			return "";
		}
	}
	
	//utils
	public String getAllSingersForDisplay() {
		String result = "";
		if(!AihatUtils.isEmpty(singers)) {
			boolean isFirst = true;
			for(SingerDto aSinger : singers) {
				if(aSinger != null) {
					if(!isFirst) {
						result += ", ";
					}
					result += aSinger.getName();
				}
			}
		}
		return result;
	}
	public String getAllComposersForDisplay() {
		String result = "";
		if(!AihatUtils.isEmpty(composers)) {
			boolean isFirst = true;
			for(ComposerDto aComposer : composers) {
				if(aComposer != null) {
					if(!isFirst) {
						result += ", ";
					}
					result += aComposer.getName();
				}
			}
		}
		return result;
	}
	
	//getters, setters
	public String getTitle() {
		return title;
	}
	public String getTitleEscapeDoubleQuote() {
		if(AihatUtils.isEmpty(title)) {
			return title;
		}
		return title.replace("\"", "\\\"");
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleSearch() {
		return titleSearch;
	}
	public void setTitleSearch(String titleSearch) {
		this.titleSearch = titleSearch;
	}
	public List<SingerDto> getSingers() {
		return singers;
	}
	public void setSingers(List<SingerDto> singers) {
		this.singers = singers;
	}
	public List<ComposerDto> getComposers() {
		return composers;
	}
	public void setComposers(List<ComposerDto> composers) {
		this.composers = composers;
	}
	public List<GenreDto> getGenres() {
		return genres;
	}
	public void setGenres(List<GenreDto> genres) {
		this.genres = genres;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getnFans() {
		return nFans;
	}
	public void setnFans(Integer nFans) {
		this.nFans = nFans;
	}
	
	public Boolean getOfficial() {
		return official;
	}
	public void setOfficial(Boolean official) {
		this.official = official;
	}
	public Integer getnViews() {
		return nViews;
	}
	public void setnViews(Integer nViews) {
		this.nViews = nViews;
	}
	
	public Boolean getLiked() {
		return liked;
	}
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}
	public String forLog() {
		String result = "";
		if(getId() != null) {
			result = result + "id:'" + getId() + "' ";
		}
		if(getTitle() != null) {
			result = result + "title:'" + getTitle() + "' ";
		}
		return result;
	}
	public Integer getOrderInPlaylist() {
		return orderInPlaylist;
	}
	public void setOrderInPlaylist(Integer orderInPlaylist) {
		this.orderInPlaylist = orderInPlaylist;
	}
	public List<ClipCommentDto> getComments() {
		return comments;
	}
	public void setComments(List<ClipCommentDto> comments) {
		this.comments = comments;
	}
}

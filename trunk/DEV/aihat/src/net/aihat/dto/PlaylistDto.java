package net.aihat.dto;

public class PlaylistDto extends BaseUserCareDto {
	private String name;
	private String nameSearch;
	private Integer nClips;
	private ClipDto mainClip;
	
	//not from DB
	private boolean editable = false;
	
	public String forLog() {
		String result = "";
		if(getId() != null) {
			result = result + "id:'" + getId() + "' ";
		}
		if(getName() != null) {
			result = result + "name:'" + getName() + "' ";
		}
		return result;
	}
	
	
	//getter setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getnClips() {
		return nClips;
	}

	public void setnClips(Integer nClips) {
		this.nClips = nClips;
	}

	public String getNameSearch() {
		return nameSearch;
	}

	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}
	
	public ClipDto getMainClip() {
		return mainClip;
	}

	public void setMainClip(ClipDto mainClip) {
		this.mainClip = mainClip;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}

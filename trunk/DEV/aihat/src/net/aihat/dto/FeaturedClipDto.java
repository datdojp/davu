package net.aihat.dto;

public class FeaturedClipDto extends BaseDto {
	//data
	private ClipDto clip;
	private Integer order;
	
	//override
	public String forLog() {
		String result = "";
		try {
			result = "[clipId=" + clip.getId() + ",order=" + order+"]";
		} catch (Exception e) {
			result = "Unable to generate log for FeaturedClipDto";
		}
		return result;
	}

	//getter setter
	public ClipDto getClip() {
		return clip;
	}

	public void setClip(ClipDto clip) {
		this.clip = clip;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}

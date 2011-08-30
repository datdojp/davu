package net.aihat.message;

public class AihatMessage {
	public static final String TYPE_ERROR = "error";
	public static final String TYPE_INFO = "info";
	public static final String TYPE_WARNING = "warning";
	
	private String content;
	private String type;
	
	public AihatMessage(String content, String type) {
		super();
		this.content = content;
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}

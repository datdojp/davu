package net.aihat.criteria;

public class PagingCriterion {
	private Long offset;
	private Long rowCount;
	private Long maxOffset;
	
	public PagingCriterion() {}
	public PagingCriterion(Long offset, Long rowCount) {
		this.offset = offset;
		this.rowCount = rowCount;
	}
	
	//getter setter
	public Long getOffset() {
		return offset;
	}
	public void setOffset(Long offset) {
		this.offset = offset;
	}
	public Long getRowCount() {
		return rowCount;
	}
	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}
	public Long getMaxOffset() {
		return maxOffset;
	}
	public void setMaxOffset(Long maxOffset) {
		this.maxOffset = maxOffset;
	}
	
}

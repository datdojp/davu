package net.aihat.criteria;

import net.aihat.utils.AihatUtils;


public class SortCriterion {
	public static final String ORDER_ASCENDING = "asc";
	public static final String ORDER_DESCENDING = "desc";
	
	private String columnName;
	private String order;
	
	//constructor
	public SortCriterion() {}
	
	public SortCriterion(String columnName, String order) {
		super();
		this.columnName = columnName;
		this.order = order;
	}

	//getter setter
	public String getColumnName() {
		return columnName;
	}
	
	public String getSqlColumnName() {
		if(!AihatUtils.isEmpty(columnName)
				&& columnName.indexOf(".") >= 0
				&& columnName.indexOf("`") < 0) {
			return "`" + columnName + "`";
		} else {
			return columnName;
		}
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
}

package net.aihat.criteria;

import java.io.Serializable;
import java.util.List;

public abstract class BaseCriteria implements Serializable, Cloneable {
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	private Integer id;
	private List<Integer> ids;
	private SortCriterion sortCriterion;
	private PagingCriterion pagingCriterion;
	private boolean forCounting;

	public SortCriterion getSortCriterion() {
		return sortCriterion;
	}

	public void setSortCriterion(SortCriterion sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PagingCriterion getPagingCriterion() {
		return pagingCriterion;
	}

	public void setPagingCriterion(PagingCriterion pagingCriterion) {
		this.pagingCriterion = pagingCriterion;
	}

	public boolean isForCounting() {
		return forCounting;
	}

	public void setForCounting(boolean forCounting) {
		this.forCounting = forCounting;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}

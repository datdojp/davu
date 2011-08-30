package net.aihat.dto;

import java.util.ArrayList;
import java.util.List;

import net.aihat.utils.AihatUtils;

public class SearchResultDto {
	private List results;
	private long nResults;
	public static final SearchResultDto EMPTY = new SearchResultDto();
	
	public SearchResultDto() {
		this.results = new ArrayList();
		this.nResults = 0;
	}
	
	public SearchResultDto(List results, long nResults) {
		this.results = results;
		this.nResults = nResults;
	}
	
	public SearchResultDto(List results) {
		this.results = results;
		if(AihatUtils.isEmpty(this.results)) {
			this.nResults = 0;
		} else {
			this.nResults = this.results.size();
		}
	}
	
	public SearchResultDto(long nResults) {
		this.results = new ArrayList();
		this.nResults = nResults;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	public long getnResults() {
		return nResults;
	}

	public void setnResults(long nResults) {
		this.nResults = nResults;
	}
}

package net.aihat.bean.client;

import java.util.List;

import net.aihat.bean.BaseBean;
import net.aihat.service.ClipService;
import net.aihat.service.ComposerService;
import net.aihat.service.FailedSearchService;
import net.aihat.service.GenreService;
import net.aihat.service.PlaylistService;
import net.aihat.service.SearchService;
import net.aihat.service.SingerService;
import net.aihat.service.UserService;
import net.aihat.utils.BeanUtils;

public abstract class BaseClientBean extends BaseBean {
	protected void cleanAllFields() {}
	
	private SearchService searchService;
	private ClipService clipService;
	private SingerService singerService;
	private ComposerService composerService;
	private PlaylistService playlistService;
	private GenreService genreService;
	private UserService userService;
	private FailedSearchService failedSearchService;

	//init
	private boolean isInitialized = false;
	public String init() {
		isInitialized = true;
		return null;
	}
	
	//name of the bean that call method of this bean by ajax or direct request
	protected String referenceBeanName;
	public void setReferenceBeanName(String referenceBeanName) {
		this.referenceBeanName = referenceBeanName;
	}
	protected BaseClientBean getReferenceBean() {
		referenceBeanName = BeanUtils.getRequest().getParameter("referenceBeanName");
		return (BaseClientBean) BeanUtils.getContextBean(referenceBeanName);
	}
	
	//utils
	protected abstract List getCurrentDtoList();
	protected void handleGeneralError(Throwable err) {
		getLogger().error(err);
		addErrorMessage("CM0000");
	}
	
	//getter setter
	public ClipService getClipService() {
		return clipService;
	}
	public void setClipService(ClipService clipService) {
		this.clipService = clipService;
	}
	public SearchService getSearchService() {
		return searchService;
	}
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	public SingerService getSingerService() {
		return singerService;
	}
	public void setSingerService(SingerService singerService) {
		this.singerService = singerService;
	}
	public ComposerService getComposerService() {
		return composerService;
	}
	public void setComposerService(ComposerService composerService) {
		this.composerService = composerService;
	}
	public PlaylistService getPlaylistService() {
		return playlistService;
	}
	public void setPlaylistService(PlaylistService playlistService) {
		this.playlistService = playlistService;
	}
	public GenreService getGenreService() {
		return genreService;
	}
	public void setGenreService(GenreService genreService) {
		this.genreService = genreService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getReferenceBeanName() {
		return referenceBeanName;
	}
	public boolean isInitialized() {
		return isInitialized;
	}
	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}
	public FailedSearchService getFailedSearchService() {
		return failedSearchService;
	}
	public void setFailedSearchService(FailedSearchService failedSearchService) {
		this.failedSearchService = failedSearchService;
	}
	
}

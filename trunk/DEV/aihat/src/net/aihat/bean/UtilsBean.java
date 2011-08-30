package net.aihat.bean;

import java.util.List;

import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.service.ComposerService;
import net.aihat.service.GenreService;
import net.aihat.service.SingerService;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class UtilsBean extends BaseBean {
	public UtilsBean() {
		logger = Logger.getLogger(UtilsBean.class);
	}
	
	public String getBeanName() {
		return "utilsBean";
	}

	private SingerService singerService;
	private ComposerService composerService;
	private GenreService genreService;
	
	private List<SingerDto> tempSingerList;
	private List<ComposerDto> tempComposerList;
	private List<GenreDto> tempGenreList;
	
	public String init() {
		return null;
	}
	
	//utils
	public List<SingerDto> getAllSingers() {
		if(tempSingerList == null) {
			return singerService.getAllSimpleSingers();
		} else {
			return tempSingerList;
		}
	}
	public List<ComposerDto> getAllComposers() {
		if(tempComposerList == null) {
			return composerService.getAllSimpleComposers();
		} else {
			return tempComposerList;
		}
	}
	public List<GenreDto> getAllGenres() {
		if(tempGenreList == null) {
			String language;
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null || AihatUtils.isEmpty(profile.getLanguage())) {
				language = AihatConstants.LANGUAGE_VIETNAMES;
			} else {
				language = profile.getLanguage();
			}

			return genreService.getAllSimpleGenres(language);
		} else {
			return tempGenreList;
		}
	}
	
	public String getCurrentLanguage() {
		return BeanUtils.getCurrentLanguage();
	}
	
	protected void cleanAllFields() {
		//DO NOTHING
	}
	
	//getter setter
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
	public GenreService getGenreService() {
		return genreService;
	}
	public void setGenreService(GenreService genreService) {
		this.genreService = genreService;
	}
	public List<SingerDto> getTempSingerList() {
		return tempSingerList;
	}
	public void setTempSingerList(List<SingerDto> tempSingerList) {
		this.tempSingerList = tempSingerList;
	}
	public List<ComposerDto> getTempComposerList() {
		return tempComposerList;
	}
	public void setTempComposerList(List<ComposerDto> tempComposerList) {
		this.tempComposerList = tempComposerList;
	}
	public List<GenreDto> getTempGenreList() {
		return tempGenreList;
	}
	public void setTempGenreList(List<GenreDto> tempGenreList) {
		this.tempGenreList = tempGenreList;
	}
}

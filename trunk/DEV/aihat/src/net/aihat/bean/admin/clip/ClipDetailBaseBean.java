package net.aihat.bean.admin.clip;

import java.util.ArrayList;

import net.aihat.bean.BaseBean;
import net.aihat.dto.ClipDto;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.SingerDto;
import net.aihat.service.ClipService;
import net.aihat.utils.AihatUtils;

public abstract class ClipDetailBaseBean extends BaseBean {
	private ClipService clipService;
	private ClipDto clip = new ClipDto();
	private Integer singerId_1;
	private Integer singerId_2;
	private Integer singerId_3;
	private Integer composerId_1;
	private Integer composerId_2;
	private Integer composerId_3;
	private Integer genreId_1;
	private Integer genreId_2;
	private Integer genreId_3;

	//function to store related dto 's ids into clip
	protected void storeRelatedDtos() {
		clip.setSingers(new ArrayList<SingerDto>());
		clip.setComposers(new ArrayList<ComposerDto>());
		clip.setGenres(new ArrayList<GenreDto>());

		//add singer to dto
		SingerDto aSinger = new SingerDto();
		aSinger.setId(singerId_1);
		clip.getSingers().add(aSinger);
		if(AihatUtils.isValidId(singerId_2)) {
			aSinger = new SingerDto();
			aSinger.setId(singerId_2);
			clip.getSingers().add(aSinger);
		}
		if(AihatUtils.isValidId(singerId_3)) {
			aSinger = new SingerDto();
			aSinger.setId(singerId_3);
			clip.getSingers().add(aSinger);
		}

		//add composer to dto
		ComposerDto aComposer = new ComposerDto();
		aComposer.setId(composerId_1);
		clip.getComposers().add(aComposer);
		if(AihatUtils.isValidId(composerId_2)) {
			aComposer = new ComposerDto();
			aComposer.setId(composerId_2);
			clip.getComposers().add(aComposer);
		}
		if(AihatUtils.isValidId(composerId_3)) {
			aComposer = new ComposerDto();
			aComposer.setId(composerId_3);
			clip.getComposers().add(aComposer);
		}

		//add genre to dto
		GenreDto aGenre = new GenreDto();
		aGenre.setId(genreId_1);
		clip.getGenres().add(aGenre);
		if(AihatUtils.isValidId(genreId_2)) {
			aGenre = new GenreDto();
			aGenre.setId(genreId_2);
			clip.getGenres().add(aGenre);
		}
		if(AihatUtils.isValidId(genreId_3)) {
			aGenre = new GenreDto();
			aGenre.setId(genreId_3);
			clip.getGenres().add(aGenre);
		}
	}

	//clean all fields
	protected void cleanAllFields() {
		clip = new ClipDto();
		singerId_1 = singerId_2 = singerId_3 = null;
		composerId_1 = composerId_2 = composerId_3 = null;
		genreId_1 = genreId_2 = genreId_3 = null;
	}
	
	//getter setter
	public ClipService getClipService() {
		return clipService;
	}
	public void setClipService(ClipService clipService) {
		this.clipService = clipService;
	}
	public ClipDto getClip() {
		return clip;
	}
	public void setClip(ClipDto clip) {
		this.clip = clip;
	}
	public Integer getSingerId_1() {
		return singerId_1;
	}
	public void setSingerId_1(Integer singerId_1) {
		this.singerId_1 = singerId_1;
	}
	public Integer getSingerId_2() {
		return singerId_2;
	}
	public void setSingerId_2(Integer singerId_2) {
		this.singerId_2 = singerId_2;
	}
	public Integer getSingerId_3() {
		return singerId_3;
	}
	public void setSingerId_3(Integer singerId_3) {
		this.singerId_3 = singerId_3;
	}
	public Integer getComposerId_1() {
		return composerId_1;
	}
	public void setComposerId_1(Integer composerId_1) {
		this.composerId_1 = composerId_1;
	}
	public Integer getComposerId_2() {
		return composerId_2;
	}
	public void setComposerId_2(Integer composerId_2) {
		this.composerId_2 = composerId_2;
	}
	public Integer getComposerId_3() {
		return composerId_3;
	}
	public void setComposerId_3(Integer composerId_3) {
		this.composerId_3 = composerId_3;
	}
	public Integer getGenreId_1() {
		return genreId_1;
	}
	public void setGenreId_1(Integer genreId_1) {
		this.genreId_1 = genreId_1;
	}
	public Integer getGenreId_2() {
		return genreId_2;
	}
	public void setGenreId_2(Integer genreId_2) {
		this.genreId_2 = genreId_2;
	}
	public Integer getGenreId_3() {
		return genreId_3;
	}
	public void setGenreId_3(Integer genreId_3) {
		this.genreId_3 = genreId_3;
	}
}

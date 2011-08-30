package net.aihat.bean.admin.genre;

import net.aihat.bean.BaseBean;
import net.aihat.dto.GenreDto;
import net.aihat.dto.UserDto;
import net.aihat.service.GenreService;
import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public abstract class GenreDetailBaseBean extends BaseBean {
	private GenreService genreService;
	private GenreDto genre = new GenreDto();

	//store(insert/update) in db and save the avatar
	protected void storeGenre() {
		try {
			//insert into database
			if(genre.isNew()) {
				genre.setUser(new UserDto());
				genre.getUser().setId(BeanUtils.getUserProfileBean().getProfile().getId());
				genre = genreService.createGenre(genre);
			} else {
				genreService.updateGenre(genre);
			}

			//clear data, prepare for next genre
			cleanAllFields();
			addInfoMessage("Creating new genre completed successfully.");
		} catch (DataAccessException e2) {
			logger.error(genre.forLog(), e2);
			addErrorMessage("Unable to create new genre. Please try again later.");
		}
	}

	//clean all fields
	protected void cleanAllFields() {
		genre = new GenreDto();
	}

	//getter setter
	public GenreService getGenreService() {
		return genreService;
	}

	public void setGenreService(GenreService genreService) {
		this.genreService = genreService;
	}

	public GenreDto getGenre() {
		return genre;
	}

	public void setGenre(GenreDto genre) {
		this.genre = genre;
	}
}

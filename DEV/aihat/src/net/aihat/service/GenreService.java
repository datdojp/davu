package net.aihat.service;

import java.util.List;
import net.aihat.dto.GenreDto;
import org.springframework.dao.DataAccessException;

public interface GenreService {
	public GenreDto createGenre(GenreDto Genre) throws DataAccessException;
	public void deleteGenres(List<GenreDto> Genres) throws DataAccessException;
	public GenreDto getGenre(Integer id) throws DataAccessException;
	public void updateGenre(GenreDto Genre) throws DataAccessException;
	public List<GenreDto> getAllSimpleGenres(String language) throws DataAccessException;
}

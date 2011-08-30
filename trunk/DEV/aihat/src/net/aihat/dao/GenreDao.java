package net.aihat.dao;

import java.util.List;

import net.aihat.criteria.GenreSearchCriteria;
import net.aihat.dto.GenreDto;
import net.aihat.dto.SearchResultDto;

import org.springframework.dao.DataAccessException;

public interface GenreDao {
	public GenreDto get(Integer id) throws DataAccessException;
	public SearchResultDto search(GenreSearchCriteria criteria) throws DataAccessException;
	public GenreDto insert(GenreDto dto) throws DataAccessException;
	public void updateDeleted(List<Integer> ids) throws DataAccessException;
	public void update(GenreDto dto) throws DataAccessException;
	public List<GenreDto> getAllSimpleGenres(String language) throws DataAccessException;
	public GenreDto getGenreByName(String name, String language) throws DataAccessException;
}

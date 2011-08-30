package net.aihat.dao;

import java.util.List;
import net.aihat.criteria.ComposerSearchCriteria;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.SearchResultDto;

import org.springframework.dao.DataAccessException;

public interface ComposerDao {
	public ComposerDto get(Integer id) throws DataAccessException;
	public SearchResultDto search(ComposerSearchCriteria criteria) throws DataAccessException;
	public ComposerDto insert(ComposerDto dto) throws DataAccessException;
	public void updateDeleted(List<Integer> ids) throws DataAccessException;
	public void update(ComposerDto dto) throws DataAccessException;
	public void updateImage(ComposerDto dto) throws DataAccessException;
	public List<ComposerDto> getAllSimpleComposers() throws DataAccessException;
	public ComposerDto getComposerByName(String name) throws DataAccessException;
}

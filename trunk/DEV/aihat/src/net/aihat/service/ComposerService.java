package net.aihat.service;

import java.util.List;
import net.aihat.dto.ComposerDto;
import org.springframework.dao.DataAccessException;

public interface ComposerService {
	public ComposerDto createComposer(ComposerDto composer) throws DataAccessException;
	public void deleteComposers(List<ComposerDto> composers) throws DataAccessException;
	public ComposerDto getComposer(Integer id) throws DataAccessException;
	public void updateComposer(ComposerDto composer) throws DataAccessException;
	public void updateComposerImage(ComposerDto composer) throws DataAccessException;
	public List<ComposerDto> getAllSimpleComposers() throws DataAccessException;
}

package net.aihat.service;

import java.util.ArrayList;
import java.util.List;

import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.GenreDto;
import net.aihat.utils.AihatUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class GenreServiceImpl extends BaseService implements GenreService {
	@Transactional(rollbackFor=DataAccessException.class)
	public GenreDto createGenre(GenreDto genre) throws DataAccessException {
		return getGenreDao().insert(genre);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void deleteGenres(List<GenreDto> genres) throws DataAccessException {
			if(AihatUtils.isEmpty(genres)) {
				return;
			}
			
			//get list of IDs for genres and clips
			List<Integer> ids = new ArrayList<Integer>(genres.size());
			List<Integer> clipIds = new ArrayList<Integer>();
			ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
			for(GenreDto aGenre : genres) {
				ids.add(aGenre.getId());
				
				clipSearchCriteria.setGenre(aGenre);
				List<ClipDto> clips = getClipDao().search(clipSearchCriteria).getResults();
				if(!AihatUtils.isEmpty(clips)) {
					for(ClipDto aClip : clips) {
						clipIds.add(aClip.getId());
					}
				}
			}

			//update deleted for clips
			if(!AihatUtils.isEmpty(clipIds)) {
				getClipDao().updateDeleted(clipIds);
			}
			
			//update deleted for singers
			getGenreDao().updateDeleted(ids);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public GenreDto getGenre(Integer id) throws DataAccessException {
		return getGenreDao().get(id);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateGenre(GenreDto Genre) throws DataAccessException {
		getGenreDao().update(Genre);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public List<GenreDto> getAllSimpleGenres(String language) throws DataAccessException {
		return getGenreDao().getAllSimpleGenres(language);
	}
}

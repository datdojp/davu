package net.aihat.service;

import java.util.ArrayList;
import java.util.List;

import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.ComposerDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class ComposerServiceImpl extends BaseService implements ComposerService {
	private String defaultImage;
	public String getDefaultImage() {
		return defaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public ComposerDto createComposer(ComposerDto composer) throws DataAccessException {
		if(AihatUtils.isEmpty(composer.getImage())) {
			composer.setImage(defaultImage);
		}
		return getComposerDao().insert(composer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void deleteComposers(List<ComposerDto> composers) throws DataAccessException {
			if(AihatUtils.isEmpty(composers)) {
				return;
			}
			
			//get list of IDs for composers and clips
			List<Integer> composerIds = new ArrayList<Integer>(composers.size());
			List<Integer> clipIds = new ArrayList<Integer>();
			ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
			for(ComposerDto aComposer : composers) {
				composerIds.add(aComposer.getId());
				
				clipSearchCriteria.setComposer(aComposer);
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
			getComposerDao().updateDeleted(composerIds);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public ComposerDto getComposer(Integer id) throws DataAccessException {
		return getComposerDao().get(id);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateComposer(ComposerDto composer) throws DataAccessException {
		getComposerDao().update(composer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateComposerImage(ComposerDto composer) throws DataAccessException {
		getComposerDao().updateImage(composer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public List<ComposerDto> getAllSimpleComposers() throws DataAccessException {
		return getComposerDao().getAllSimpleComposers();
	}
}

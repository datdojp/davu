package net.aihat.service;

import java.util.ArrayList;
import java.util.List;

import net.aihat.dto.ClipDto;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class ClipServiceImpl extends BaseService implements ClipService {
	@Transactional(rollbackFor=DataAccessException.class)
	public List<ClipDto> getFeaturedClips(int nFeaturedClips) throws DataAccessException {
		return getClipDao().getMostLiked(nFeaturedClips);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public ClipDto uploadClip(ClipDto clip) throws DataAccessException {
			//insert clip
			clip = getClipDao().insert(clip);
			
			//insert singer, composer, genre that relates to clip
			addClipRelated(clip);
			
			return clip;
	}

	protected void addClipRelated(ClipDto clip) throws DataAccessException {
		if(!AihatUtils.isEmpty(clip.getSingers())) {
			for(SingerDto aSinger : clip.getSingers()) {
				getClipDao().insertClipSinger(clip.getId(), aSinger.getId(), clip.getSingers().indexOf(aSinger));
			}
		}
		if(!AihatUtils.isEmpty(clip.getComposers())) {
			for(ComposerDto aComposer : clip.getComposers()) {
				getClipDao().insertClipComposer(clip.getId(), aComposer.getId(), clip.getComposers().indexOf(aComposer));
			}
		}
		if(!AihatUtils.isEmpty(clip.getGenres())) {
			for(GenreDto aGenre : clip.getGenres()) {
				getClipDao().insertClipGenre(clip.getId(), aGenre.getId(), clip.getGenres().indexOf(aGenre));
			}
		}
	}
	
	private void clearClipRelated(Integer clipId) throws DataAccessException {
		getClipDao().clearClipSinger(clipId);
		getClipDao().clearClipComposer(clipId);
		getClipDao().clearClipGenre(clipId);
	}
	
	@Transactional(rollbackFor=DataAccessException.class)
	public void deleteClips(List<ClipDto> clips) throws DataAccessException {
		if(AihatUtils.isEmpty(clips)) {
			return;
		}
		List<Integer> ids = new ArrayList<Integer>(clips.size());
		for(ClipDto aClip : clips) {
			ids.add(aClip.getId());
		}

		getClipDao().updateDeleted(ids);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public ClipDto getClip(Integer id) {
		return getClipDao().get(id);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateClip(ClipDto clip) throws DataAccessException {
			getClipDao().update(clip);
			clearClipRelated(clip.getId());
			addClipRelated(clip);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void addView(Integer clipId, Integer userId)
			throws DataAccessException {
		List<Integer> clipIds = new ArrayList<Integer>(1);
		clipIds.add(clipId);
		addView(clipIds, userId);
		
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void addView(List<Integer> clipIds, Integer userId)
			throws DataAccessException {
		UserDto user = new UserDto();
		user.setId(userId);
		for(Integer anId : clipIds) {
			ClipDto clip = new ClipDto();
			clip.setId(anId);
			getClipDao().addView(clip, user);
		}
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void addLiked(Integer clipId, Integer userId) throws DataAccessException {
		ClipDto clip = new ClipDto();
		clip.setId(clipId);
		UserDto user = new UserDto();
		user.setId(userId);
		getClipDao().addLiked(clip, user);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void removeLiked(Integer clipId, Integer userId)
			throws DataAccessException {
		ClipDto clip = new ClipDto();
		clip.setId(clipId);
		UserDto user = new UserDto();
		user.setId(userId);
		getClipDao().deleteUserLikeClip(clip, user);
	}
}

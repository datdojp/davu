package net.aihat.service;

import java.util.List;
import net.aihat.dto.ClipDto;
import net.aihat.dto.FeaturedClipDto;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class FeaturedClipServiceImpl extends BaseService implements FeaturedClipService {
	@Transactional(rollbackFor=DataAccessException.class)
	public List<FeaturedClipDto> getAllFeaturedClips() throws DataAccessException {
		List<FeaturedClipDto> results = getFeaturedClipDao().search();
		for(FeaturedClipDto aFC : results) {
			aFC.setClip(getClipDao().get(aFC.getClip().getId()));
		}
		return results;
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void addFeaturedClip(int id, int order) throws DataAccessException {
		FeaturedClipDto featuredClip = new FeaturedClipDto();
		featuredClip.setClip(new ClipDto());
		featuredClip.getClip().setId(id);
		featuredClip.setOrder(order);
		getFeaturedClipDao().insert(featuredClip);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateFeaturedClips(List<FeaturedClipDto> featuredClips) throws DataAccessException {
		for(FeaturedClipDto aFC : featuredClips) {
			getFeaturedClipDao().update(aFC);
		}
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void deleteFeaturedClip(int clipId) throws DataAccessException {
		FeaturedClipDto featuredClip = new FeaturedClipDto();
		featuredClip.setClip(new ClipDto());
		featuredClip.getClip().setId(clipId);
		getFeaturedClipDao().delete(featuredClip);
	}
}

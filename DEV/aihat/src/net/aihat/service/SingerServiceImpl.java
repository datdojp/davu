package net.aihat.service;

import java.util.ArrayList;
import java.util.List;
import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class SingerServiceImpl extends BaseService implements SingerService {
	private String defaultImage;
	
	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public SingerDto createSinger(SingerDto singer) throws DataAccessException {
		if(AihatUtils.isEmpty(singer.getImage())) {
			singer.setImage(defaultImage);
		}
		return getSingerDao().insert(singer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void deleteSingers(List<SingerDto> singers) throws DataAccessException {
		if(AihatUtils.isEmpty(singers)) {
			return;
		}

		//get list of IDs for singers and clips
		List<Integer> singerIds = new ArrayList<Integer>(singers.size());
		List<Integer> clipIds = new ArrayList<Integer>();
		ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
		for(SingerDto aSinger : singers) {
			singerIds.add(aSinger.getId());
			
			clipSearchCriteria.setSinger(aSinger);
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
		getSingerDao().updateDeleted(singerIds);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public SingerDto getSinger(Integer id) throws DataAccessException {
		return getSingerDao().get(id);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateSinger(SingerDto singer) throws DataAccessException {
		getSingerDao().update(singer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateSingerImage(SingerDto singer) throws DataAccessException {
		getSingerDao().updateImage(singer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public List<SingerDto> getAllSimpleSingers() throws DataAccessException {
		return getSingerDao().getAllSimpleSingers();
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void addFollower(Integer userId, Integer singerId) throws DataAccessException {
		UserDto user = new UserDto();
		user.setId(userId);
		SingerDto singer = new SingerDto();
		singer.setId(singerId);
		getSingerDao().insertUserFollowSinger(user, singer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void removeFollower(Integer userId, Integer singerId) throws DataAccessException {
		UserDto user = new UserDto();
		user.setId(userId);
		SingerDto singer = new SingerDto();
		singer.setId(singerId);
		getSingerDao().deleteUserFollowSinger(user, singer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public boolean checkFollower(Integer userId, Integer singerId) throws DataAccessException {
		UserDto user = new UserDto();
		user.setId(userId);
		SingerDto singer = new SingerDto();
		singer.setId(singerId);
		return getSingerDao().checkFollower(user, singer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void addLiked(Integer userId, Integer singerId) throws DataAccessException {
		UserDto user = new UserDto();
		user.setId(userId);
		SingerDto singer = new SingerDto();
		singer.setId(singerId);
		getSingerDao().insertUserLikeSinger(user, singer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void removeLiked(Integer userId, Integer singerId) throws DataAccessException {
		UserDto user = new UserDto();
		user.setId(userId);
		SingerDto singer = new SingerDto();
		singer.setId(singerId);
		getSingerDao().deleteUserLikeSinger(user, singer);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public boolean checkLiked(Integer userId, Integer singerId) throws DataAccessException {
		UserDto user = new UserDto();
		user.setId(userId);
		SingerDto singer = new SingerDto();
		singer.setId(singerId);
		return getSingerDao().checkLiked(user, singer);
	}
}

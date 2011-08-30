package net.aihat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.aihat.criteria.ClipSearchCriteria;
import net.aihat.criteria.ComposerSearchCriteria;
import net.aihat.criteria.GenreSearchCriteria;
import net.aihat.criteria.PlaylistSearchCriteria;
import net.aihat.criteria.SingerSearchCriteria;
import net.aihat.dto.ClipDto;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.PasswordDto;
import net.aihat.dto.PlaylistDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl extends BaseService implements UserService {
	private String defaultImage;
	
	@Transactional(rollbackFor=DataAccessException.class)
	public UserDto login(String mail, String password) throws DataAccessException {
			//get user profile
			UserDto user = getUserDao().get(mail);
			if(user == null) {
				return null;
			}

			//get user 's password
			if(!user.getPassword().getEncrypted().equals(AihatUtils.encryptPassword(password))) {
				return null;
			}

			//update last login
			getUserDao().updateLastLogin(user.getId());

			return user;
	}
	
	@Transactional(rollbackFor=DataAccessException.class)
	public void updateLanguage(Integer id, String language) throws DataAccessException {
		getUserDao().updateLanguage(id, language);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void deleteUsers(List<UserDto> users) throws DataAccessException {
		if(AihatUtils.isEmpty(users)) {
			return;
		}

		//get list of IDs for users and related object (clip, singer, composer, genre)
		List<Integer> userIds = new ArrayList<Integer>(users.size());
		List<Integer> clipIds = new ArrayList<Integer>();
		List<Integer> singerIds = new ArrayList<Integer>();
		List<Integer> composerIds = new ArrayList<Integer>();
		List<Integer> playlistIds = new ArrayList<Integer>();
		List<Integer> genreIds = new ArrayList<Integer>();
		ClipSearchCriteria clipSearchCriteria = new ClipSearchCriteria();
		SingerSearchCriteria singerSearchCriteria = new SingerSearchCriteria();
		ComposerSearchCriteria composerSearchCriteria = new ComposerSearchCriteria();
		PlaylistSearchCriteria playlistSearchCriteria = new PlaylistSearchCriteria();
		GenreSearchCriteria genreSearchCriteria = new GenreSearchCriteria();
		for(UserDto aUser : users) {
			userIds.add(aUser.getId());

			//clip
			clipSearchCriteria.setUser(aUser);
			List<ClipDto> clips = getClipDao().search(clipSearchCriteria).getResults();
			if(!AihatUtils.isEmpty(clips)) {
				for(ClipDto aClip : clips) {
					clipIds.add(aClip.getId());
				}
			}
			
			//singer
			singerSearchCriteria.setUser(aUser);
			List<SingerDto> singers = getSingerDao().search(singerSearchCriteria).getResults();
			if(!AihatUtils.isEmpty(singers)) {
				for(SingerDto aSinger : singers) {
					singerIds.add(aSinger.getId());
				}
			}
			
			//composer
			composerSearchCriteria.setUser(aUser);
			List<ComposerDto> composers = getComposerDao().search(composerSearchCriteria).getResults();
			if(!AihatUtils.isEmpty(composers)) {
				for(ComposerDto aComposer : composers) {
					composerIds.add(aComposer.getId());
				}
			}
			
			//playlist
			playlistSearchCriteria.setUser(aUser);
			List<PlaylistDto> playlists = getPlaylistDao().search(playlistSearchCriteria).getResults();
			if(!AihatUtils.isEmpty(playlists)) {
				for(PlaylistDto aPlaylist : playlists) {
					playlistIds.add(aPlaylist.getId());
				}
			}
			
			//genre
			genreSearchCriteria.setUser(aUser);
			List<GenreDto> genres = getGenreDao().search(genreSearchCriteria).getResults();
			if(!AihatUtils.isEmpty(genres)) {
				for(GenreDto aGenre : genres) {
					genreIds.add(aGenre.getId());
				}
			}
		}

		//update deleted for clips
		if(!AihatUtils.isEmpty(clipIds)) {
			getClipDao().updateDeleted(clipIds);
		}
		//update deleted for singers
		if(!AihatUtils.isEmpty(singerIds)) {
			getSingerDao().updateDeleted(singerIds);
		}
		
		//update deleted for composers
		if(!AihatUtils.isEmpty(composerIds)) {
			getComposerDao().updateDeleted(composerIds);
		}
		//update deleted for playlist
		if(!AihatUtils.isEmpty(playlistIds)) {
			getPlaylistDao().updateDeleted(playlistIds);
		}
		//update deleted for genres
		if(!AihatUtils.isEmpty(genreIds)) {
			getGenreDao().updateDeleted(genreIds);
		}
		
		//update deleted for users
		getUserDao().updateDeleted(userIds);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void blockUsers(List<UserDto> users) throws DataAccessException {
		changeBlockedStatus(users, true);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void unblockUsers(List<UserDto> users) throws DataAccessException {
		changeBlockedStatus(users, false);
	}

	private void changeBlockedStatus(List<UserDto> users, boolean isBlocked) {
		if(AihatUtils.isEmpty(users)) {
			return;
		}

		//get id list
		List<Integer> userIds = new ArrayList<Integer>(users.size());
		for(UserDto aUser : users) {
			userIds.add(aUser.getId());
		}
		
		//update blocked status
		if(isBlocked) {
			getUserDao().updateBlocked(userIds);
		} else {
			getUserDao().updateUnblocked(userIds);
		}
	}
	
	@Transactional(rollbackFor=DataAccessException.class)
	public UserDto getUser(Integer id) throws DataAccessException {
		return getUserDao().get(id);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateUserImage(UserDto user) throws DataAccessException {
		getUserDao().updateImage(user);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void resetPassword(List<UserDto> users) throws DataAccessException {
		for(UserDto aUser : users) {
			PasswordDto password = aUser.getPassword();
			password.setEncrypted(AihatUtils.encryptPassword(AihatUtils.getRandomPassword()));
			getPasswordDao().update(password);
		}
	}
	
	@Transactional(rollbackFor=DataAccessException.class)
	public void updatePassword(UserDto user, String newPassword) throws DataAccessException {
		PasswordDto password = user.getPassword();
		password.setEncrypted(AihatUtils.encryptPassword(newPassword));
		getPasswordDao().update(password);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public List<UserDto> findFollower(Integer userId) throws DataAccessException {
		return getUserDao().findFollower(userId);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void addFollower(Integer followingId, Integer followedId) throws DataAccessException {
		UserDto following = new UserDto();
		following.setId(followingId);
		UserDto followed = new UserDto();
		followed.setId(followedId);
		getUserDao().insertUserFollowUploader(following, followed);
		
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void removeFollower(Integer followingId, Integer followedId) throws DataAccessException {
		UserDto following = new UserDto();
		following.setId(followingId);
		UserDto followed = new UserDto();
		followed.setId(followedId);
		getUserDao().deleteUserFollowUploader(following, followed);
	
	}
	
	@Transactional(rollbackFor=DataAccessException.class)
	public boolean checkFollower(Integer followingId, Integer followedId) throws DataAccessException {
		UserDto following = new UserDto();
		following.setId(followingId);
		UserDto followed = new UserDto();
		followed.setId(followedId);
		return getUserDao().checkFollower(following, followed);
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void registerNewUser(String mail, String rawPassword, String name, String sex, Date birthday) throws DataAccessException {
		//insert password
		PasswordDto password = new PasswordDto();
		password.setEncrypted(AihatUtils.encryptPassword(rawPassword));
		getPasswordDao().insert(password);
		
		//insert user
		UserDto user = new UserDto();
		user.setMail(mail);
		user.setPassword(password);
		user.setImage(defaultImage);
		user.setAdmin(Boolean.FALSE);
		user.setName(name);
		user.setSex(sex);
		user.setBirthday(birthday);
		getUserDao().insert(user);
	}

	//getter setter
	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	@Transactional(rollbackFor=DataAccessException.class)
	public void updateUser(Integer id, String name, String sex, Date birthday) throws DataAccessException {
		UserDto user = new UserDto();
		user.setId(id);
		user.setName(name);
		user.setSex(sex);
		user.setBirthday(birthday);
		getUserDao().update(user);
	}
	
	
}
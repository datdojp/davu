package net.aihat.bean.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import net.aihat.criteria.PagingCriterion;
import net.aihat.criteria.SortCriterion;
import net.aihat.dto.ClipDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class MyProfileBean extends MultiTabPagingBean {
	public MyProfileBean() {
		logger = Logger.getLogger(MyProfileBean.class);
		displayTab = MYPLAYLISTS_TAB;
	}
	
	public String getBeanName() {
		return "myProfileBean";
	}

	private final String MYPLAYLISTS_TAB = "myplaylists";
	private final String LIKED_CLIPS_TAB = "likedClips";
	private final String FOLLOWED_SINGERS_TAB = "likedSingers";
	private final String NOTIFIED_CLIPS_TAB = "newClips";
	
	private String name;
	private String sex;
	private int birthdayDD;
	private int birthdayMM;
	private int birthdayYY;
	private String oldPassword;
	private UploadedFile avatarFile;
	private String newPassword;
	private String confirmNewPassword;
	
	public String init() {
		super.init();
		if(BeanUtils.getUserProfileBean().getLoggedIn()) {
			reselectCurrentTab();
		}
		return null;
	}
	
	public void loadUserInfo() {
		UserDto profile = BeanUtils.getUserProfileBean().getProfile();
		if(profile != null) {
			name = profile.getName();
			sex = profile.getSex();
			Calendar birthday = Calendar.getInstance();
			birthday.setTime(profile.getBirthday());
			birthdayDD = birthday.get(Calendar.DAY_OF_MONTH);
			birthdayMM = birthday.get(Calendar.MONTH);
			birthdayYY = birthday.get(Calendar.YEAR);
		}
	}
	
	/**
	 * CHANGE PASSWORD
	 */
	public synchronized String update() {
		try {
			//check logged-in
			UserDto profile = BeanUtils.getUserProfileBean().getProfile();
			if(profile == null) {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
				return null;
			}
			
			//check name
			if(AihatUtils.isEmpty(name)) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0021"));
				return null;
			}

			//check birthday
			String yyyymmdd = "" +
					birthdayYY + 
					(birthdayMM < 10 ? "0"+birthdayMM : birthdayMM) +
					(birthdayDD < 10 ? "0"+birthdayDD : birthdayDD);
			Date birthday = null;
			try {
				birthday = AihatConstants.SDF_YYYYMMDD.parse(yyyymmdd);
			} catch (ParseException ex) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0022"));
				return null;
			}
			
			if(!AihatUtils.isEmpty(oldPassword) ||
					!AihatUtils.isEmpty(newPassword) ||
					!AihatUtils.isEmpty(confirmNewPassword)) {
				//check empty
				if(AihatUtils.isEmpty(oldPassword) ||
						AihatUtils.isEmpty(newPassword) ||
						AihatUtils.isEmpty(confirmNewPassword)) {
					addErrorMessage(BeanUtils.getBundleMsg("CM0001"));
					return null;
				}
				
				//check old password
				if(!AihatUtils.encryptPassword(oldPassword).equals(profile.getPassword().getEncrypted())) {
					addErrorMessage(BeanUtils.getBundleMsg("CM0002"));
					return null;
				}
				
				//check new password = confirm password
				if(!newPassword.equals(confirmNewPassword)) {
					addErrorMessage(BeanUtils.getBundleMsg("CM0003"));
					return null;
				}
				
				//check if new password is safe enough
				if(!AihatUtils.checkPasswordSafeEnough(newPassword)) {
					addErrorMessage(BeanUtils.getBundleMsg("CM0004"));
					return null;
				}
				
				//all is ok. update password
				getUserService().updatePassword(profile, newPassword);
				oldPassword = null;
				newPassword = null;
				confirmNewPassword = null;
			}
			getUserService().updateUser(BeanUtils.getLogginUserId(), name, sex, birthday);
			
			//update avatar
			changeAvatar();
			
			//okie, display infor message
			addInfoMessage(BeanUtils.getBundleMsg("CM0005"));
		} catch (Throwable err) {
			handleGeneralError(err);
		}
		return null;
	}
	/**
	 * END OF CHANGE PASSWORD
	 */
	
	/**
	 * CHANGE AVATAR
	 */
	public void changeAvatar() {
		try {
			if(avatarFile != null) {
				try {
					UserDto user = BeanUtils.getUserProfileBean().getProfile();
					String avatarFilename = AihatUtils.getImageFilename(user) + "_" + Calendar.getInstance().getTimeInMillis() + "." + AihatUtils.getFileExtension(avatarFile.getName());
					FileOutputStream fileOutputStream;
					BufferedOutputStream bufferedOutputStream;
					byte[] fileData = avatarFile.getBytes();
					
					//save the file to server
					File file = new File(BeanUtils.getServletContext().getRealPath("") + "/img/" + avatarFilename);
					if(!file.exists()) {
						file.createNewFile();
					}
					fileOutputStream = new FileOutputStream(file);
					bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
					bufferedOutputStream.write(fileData);
					bufferedOutputStream.close();
		
					//save the file to backup folder
					File backupFile = new File(getConfigurationService().getImageBackupFolder() + avatarFilename);
					if(!backupFile.exists()) {
						backupFile.createNewFile();
					}
					fileOutputStream = new FileOutputStream(backupFile);
					bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
					bufferedOutputStream.write(fileData);
					bufferedOutputStream.close();
		
					//remove the old image if it is existing
					if(!AihatUtils.isEmpty(user.getImage())) {
						File oldDile = new File(BeanUtils.getServletContext().getRealPath("") + "/img/" + user.getImage());
						if(oldDile.exists()) {
							oldDile.delete();
						}
						
						File oldBackupFile = new File(getConfigurationService().getImageBackupFolder() + user.getImage());
						if(oldBackupFile.exists()) {
							oldBackupFile.delete();
						}
					}
					user.setImage(avatarFilename);
					getUserService().updateUserImage(user);
				} catch (IOException ex) {
					logger.error(ex);
					addErrorMessage(BeanUtils.getBundleMsg("CM0006"));
				}
			}
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	/**
	 * END OF CHANGE AVATAR
	 */
	
	/**
	 * INIT
	 */
	protected void initTabPagingMap() {
		tabPagingMap.put(MYPLAYLISTS_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
		tabPagingMap.put(LIKED_CLIPS_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
		tabPagingMap.put(FOLLOWED_SINGERS_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
		tabPagingMap.put(NOTIFIED_CLIPS_TAB, new PagingCriterion(0l, getConfigurationService().getnRowsPerPage()));
	}
	protected void initTabDataMap() {
		tabDataMap.put(MYPLAYLISTS_TAB, new ArrayList());
		tabDataMap.put(LIKED_CLIPS_TAB, new ArrayList());
		tabDataMap.put(FOLLOWED_SINGERS_TAB, new ArrayList());
		tabDataMap.put(NOTIFIED_CLIPS_TAB, new ArrayList());
	}
	protected void initTabDataCountMap() {
		tabDataCountMap.put(MYPLAYLISTS_TAB, 0l);
		tabDataCountMap.put(LIKED_CLIPS_TAB, 0l);
		tabDataCountMap.put(FOLLOWED_SINGERS_TAB, 0l);
		tabDataCountMap.put(NOTIFIED_CLIPS_TAB, 0l);
	}
	/**
	 * END OF INIT
	 */
	
	/**
	 * SELECT TAB
	 */
	public synchronized void selectMyPlaylistsTab(AjaxBehaviorEvent e) {
		try {
			displayTab = MYPLAYLISTS_TAB;
			UserDto user = BeanUtils.getUserProfileBean().getProfile();
			if(user != null && AihatUtils.isValidId(user.getId())) {
				tabDataMap.put(displayTab,
					getSearchService().searchPlaylists(null, null, user.getId(),
							new SortCriterion("name", SortCriterion.ORDER_ASCENDING),
							tabPagingMap.get(displayTab), false).getResults()
					);
			} else {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
			}
			
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public synchronized void selectLikedClipstab(AjaxBehaviorEvent e) {
		try {
			displayTab = LIKED_CLIPS_TAB;
			UserDto user = BeanUtils.getUserProfileBean().getProfile();
			if(user != null && AihatUtils.isValidId(user.getId())) {
				tabDataMap.put(displayTab,
						getSearchService().searchClips(null, null, null, null, null, null, null, user.getId(), null, null, 
							new SortCriterion("title", SortCriterion.ORDER_ASCENDING), 
							tabPagingMap.get(displayTab), false, user.getId(), null).getResults()
					);
			} else {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
			}
			
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	public synchronized void selectFollowedSingersTab(AjaxBehaviorEvent e) {
		try {
			displayTab = FOLLOWED_SINGERS_TAB;
			UserDto user = BeanUtils.getUserProfileBean().getProfile();
			if(user != null && AihatUtils.isValidId(user.getId())) {
				tabDataMap.put(displayTab,
					getSearchService().searchSingers(null, null, null, null, 
							new SortCriterion("name", SortCriterion.ORDER_ASCENDING), 
							tabPagingMap.get(displayTab), false, user.getId()).getResults()
					);
				List<SingerDto> followedSingers = tabDataMap.get(displayTab);
				for(SingerDto aSinger : followedSingers) {
					aSinger.setFollowed(true);
				}
			} else {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
			}
			
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void selectNotifiedClipsTab(AjaxBehaviorEvent e) {
		try {
			displayTab = NOTIFIED_CLIPS_TAB;
			UserDto user = BeanUtils.getUserProfileBean().getProfile();
			if(user != null && AihatUtils.isValidId(user.getId())) {
				tabDataMap.put(displayTab,
					getSearchService().searchClips(null, null, null, null, null, null, null, user.getId(), null, null, 
							new SortCriterion("title", SortCriterion.ORDER_ASCENDING), 
							tabPagingMap.get(displayTab), false, null, user.getId()).getResults()
					);
				for(ClipDto aClip : (List<ClipDto>)tabDataMap.get(displayTab)) {
					for(SingerDto aSinger : aClip.getSingers()) {
						aSinger.setFollowed(getSingerService().checkFollower(BeanUtils.getLogginUserId(), aSinger.getId()));
					}
					aClip.getUser().setFollowed(getUserService().checkFollower(BeanUtils.getLogginUserId(), aClip.getUser().getId()));
				}
			} else {
				addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
			}
			
			updateCurrentCounting();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	protected void reselectCurrentTab() {
		if(MYPLAYLISTS_TAB.equals(displayTab)) {
			selectMyPlaylistsTab(null);
		} else if(LIKED_CLIPS_TAB.equals(displayTab)) {
			selectLikedClipstab(null);
		} else if(FOLLOWED_SINGERS_TAB.equals(displayTab)) {
			selectFollowedSingersTab(null);
		} else if(NOTIFIED_CLIPS_TAB.equals(displayTab)) {
			selectNotifiedClipsTab(null);
		}
	}
	/**
	 * END OF SELECT TAB
	 */

	/**
	 * UPDATE COUNTING
	 */
	protected long updateCurrentCounting() {
		UserDto user = BeanUtils.getUserProfileBean().getProfile();
		if(user != null && AihatUtils.isValidId(user.getId())) {
			if(MYPLAYLISTS_TAB.equals(displayTab)) {
				tabDataCountMap.put(displayTab,
					getSearchService().searchPlaylists(null, null, user.getId(),
							null, null, true).getnResults()
					);
			} else if(LIKED_CLIPS_TAB.equals(displayTab)) {
				tabDataCountMap.put(displayTab,
					getSearchService().searchClips(null, null, null, null, null, null, null, user.getId(), null, null, 
							null, null, true, user.getId(), null).getnResults()
					);
			} else if(FOLLOWED_SINGERS_TAB.equals(displayTab)) {
				tabDataCountMap.put(displayTab,
					getSearchService().searchSingers(null, null, null, null, 
							null, null, true, user.getId()).getnResults()
					);
			} else if(NOTIFIED_CLIPS_TAB.equals(displayTab)) {
				tabDataCountMap.put(displayTab,
					getSearchService().searchClips(null, null, null, null, null, null, null, user.getId(), null, null, 
							null, null, true, null, user.getId()).getnResults()
					);
			} 
		} else {
			addErrorMessage(BeanUtils.getBundleMsg("common_please_login"));
		}
		return getCurrentCounting();
	}
	/**
	 * END OF UPDATE COUNTING
	 */
	
	//getter setter
	public UploadedFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(UploadedFile avatarFile) {
		this.avatarFile = avatarFile;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getBirthdayDD() {
		return birthdayDD;
	}

	public void setBirthdayDD(int birthdayDD) {
		this.birthdayDD = birthdayDD;
	}

	public int getBirthdayMM() {
		return birthdayMM;
	}

	public void setBirthdayMM(int birthdayMM) {
		this.birthdayMM = birthdayMM;
	}

	public int getBirthdayYY() {
		return birthdayYY;
	}

	public void setBirthdayYY(int birthdayYY) {
		this.birthdayYY = birthdayYY;
	}
	
}

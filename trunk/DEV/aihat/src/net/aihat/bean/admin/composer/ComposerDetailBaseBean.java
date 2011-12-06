package net.aihat.bean.admin.composer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import net.aihat.bean.BaseBean;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.UserDto;
import net.aihat.service.ComposerService;
import net.aihat.service.ConfigurationService;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.springframework.dao.DataAccessException;
import net.aihat.service.ConfigurationService;

public abstract class ComposerDetailBaseBean extends BaseBean {
	private ComposerService composerService;
	private ComposerDto composer = new ComposerDto();
	private UploadedFile avatarFile;

	//store(insert/update) in db and save the avatar
	protected void storeComposer() {
		try {
			//insert into database
			if(composer.isNew()) {
				composer.setUser(new UserDto());
				composer.getUser().setId(BeanUtils.getUserProfileBean().getProfile().getId());
				composer = composerService.createComposer(composer);
			} else {
				composerService.updateComposer(composer);
			}

			//create/edit success --> clear composer-list for reference
			BeanUtils.clearComposerMasterData();
			
			//save file to disk and update image filename into db
			if(avatarFile != null) {
				String avatarFilename = AihatUtils.getImageFilename(composer) + "_" + Calendar.getInstance().getTimeInMillis() + "." + AihatUtils.getFileExtension(avatarFile.getName());
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
				File backupFile = new File(ConfigurationService.getImageBackupFolder() + avatarFilename);
				if(!backupFile.exists()) {
					backupFile.createNewFile();
				}
				fileOutputStream = new FileOutputStream(backupFile);
				bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
				bufferedOutputStream.write(fileData);
				bufferedOutputStream.close();

				//remove the old image if it is existing
				if(!AihatUtils.isEmpty(composer.getImage())) {
					if(!BeanUtils.getConfig("service.defaultImage").equals(composer.getImage())) {
						File oldDile = new File(BeanUtils.getServletContext().getRealPath("") + "/img/" + composer.getImage());
						if(oldDile.exists()) {
							oldDile.delete();
						}
					}
					
					File oldBackupFile = new File(ConfigurationService.getImageBackupFolder() + composer.getImage());
					if(oldBackupFile.exists()) {
						oldBackupFile.delete();
					}
				}
				composer.setImage(avatarFilename);
				composerService.updateComposerImage(composer);
			}

			//clear data, prepare for next composer
			cleanAllFields();
			addInfoMessage("Creating new composer completed successfully.");
		}  catch (IOException e3) {
			logger.error(composer.forLog(), e3);
			addErrorMessage("Unable to write the uploaded avatar to disk. Please try again");
		} catch (DataAccessException e2) {
			logger.error(composer.forLog(), e2);
			addErrorMessage("Unable to create new composer. Please try again later.");
		}
	}

	//clean all fields
	protected void cleanAllFields() {
		composer = new ComposerDto();
		avatarFile = null;
	}

	//getter setter
	public UploadedFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(UploadedFile avatarFile) {
		this.avatarFile = avatarFile;
	}

	public ComposerService getComposerService() {
		return composerService;
	}

	public void setComposerService(ComposerService composerService) {
		this.composerService = composerService;
	}

	public ComposerDto getComposer() {
		return composer;
	}

	public void setComposer(ComposerDto composer) {
		this.composer = composer;
	}

}

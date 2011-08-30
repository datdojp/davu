package net.aihat.bean.admin.singer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import net.aihat.bean.BaseBean;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.service.SingerService;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.springframework.dao.DataAccessException;

public abstract class SingerDetailBaseBean extends BaseBean {
	private SingerService singerService;
	private SingerDto singer = new SingerDto();
	private UploadedFile avatarFile;

	//store(insert/update) in db and save the avatar
	protected void storeSinger() {
		try {
			//insert into database
			if(singer.isNew()) {
				singer.setUser(new UserDto());
				singer.getUser().setId(BeanUtils.getUserProfileBean().getProfile().getId());
				singer = singerService.createSinger(singer);
			} else {
				singerService.updateSinger(singer);
			}

			//create/edit success --> clear singer-list for reference
			BeanUtils.clearSingerMasterData();
			
			//save file to disk and update image filename into db
			if(avatarFile != null) {
				String avatarFilename = AihatUtils.getImageFilename(singer) + "_" + Calendar.getInstance().getTimeInMillis() + "." + AihatUtils.getFileExtension(avatarFile.getName());
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
				if(!AihatUtils.isEmpty(singer.getImage())) {
					File oldDile = new File(BeanUtils.getServletContext().getRealPath("") + "/img/" + singer.getImage());
					if(oldDile.exists()) {
						oldDile.delete();
					}
					
					File oldBackupFile = new File(getConfigurationService().getImageBackupFolder() + singer.getImage());
					if(oldBackupFile.exists()) {
						oldBackupFile.delete();
					}
				}
				singer.setImage(avatarFilename);
				singerService.updateSingerImage(singer);
			}

			//clear data, prepare for next singer
			cleanAllFields();
			addInfoMessage("Creating new singer completed successfully.");
		}  catch (IOException e3) {
			logger.error(singer.forLog(), e3);
			addErrorMessage("Unable to write the uploaded avatar to disk. Please try again");
		} catch (DataAccessException e2) {
			logger.error(singer.forLog(), e2);
			addErrorMessage("Unable to create new singer. Please try again later.");
		}
	}

	//clean all fields
	protected void cleanAllFields() {
		singer = new SingerDto();
		avatarFile = null;
	}

	//getter setter
	public SingerService getSingerService() {
		return singerService;
	}
	public void setSingerService(SingerService singerService) {
		this.singerService = singerService;
	}
	public SingerDto getSinger() {
		return singer;
	}
	public void setSinger(SingerDto singer) {
		this.singer = singer;
	}

	public UploadedFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(UploadedFile avatarFile) {
		this.avatarFile = avatarFile;
	}
}

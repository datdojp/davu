package net.aihat.bean.admin.user;

import java.util.List;
import net.aihat.bean.admin.DataTableCareBaseBean;
import net.aihat.bean.admin.clip.ManageClipBean;
import net.aihat.bean.admin.composer.ManageComposerBean;
import net.aihat.bean.admin.genre.ManageGenreBean;
import net.aihat.bean.admin.singer.ManageSingerBean;
import net.aihat.dto.UserDto;
import net.aihat.service.UserService;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ManageUserBean extends DataTableCareBaseBean {
	public ManageUserBean() {
		logger = Logger.getLogger(ManageUserBean.class);
	}
	
	public String getBeanName() {
		return "manageUserBean";
	}
	
	private String mailCriterion;
	private int blockedStatusCriterion;
	private int adminCriterion;
	
	private String referencedMail;
	
	private UserService userService;
	
	public synchronized String search() {
		if(!AihatUtils.isValidId(getIdCriterion())) {
			setIdCriterion(null);
		}
		
		Boolean blockStatus = null;//blockedStatusCriterion == 0 --> both
		if(blockedStatusCriterion == 1) {//--> normal
			blockStatus = false;
		} else if(blockedStatusCriterion == 2) {//--> blocked
			blockStatus = true;
		}
		
		Boolean admin = null;//adminCriterion == 0 --> both
		if(adminCriterion == 1) {//--> client
			admin = false;
		} else if(adminCriterion == 2) {//--> admin
			admin = true;
		}
		
		setSearchResults(getSearchService().searchUsers(getIdCriterion(), mailCriterion, blockStatus, admin, getSortCriterion(), null, false, null).getResults());
		BeanUtils.reloadPage();
		return null;
	}

	//reference
	public synchronized String searchAllClips() {
		ManageClipBean manageClipBean = (ManageClipBean) BeanUtils.getContextBean("manageClipBean");
		return manageClipBean.searchReference(AihatConstants.REF_TYPE_USER, referencedMail);
	}
	public synchronized String searchAllSingers() {
		ManageSingerBean manageSingerBean = (ManageSingerBean) BeanUtils.getContextBean("manageSingerBean");
		return manageSingerBean.searchReference(AihatConstants.REF_TYPE_USER, referencedMail);
	}
	public synchronized String searchAllComposers() {
		ManageComposerBean manageComposerBean = (ManageComposerBean) BeanUtils.getContextBean("manageComposerBean");
		return manageComposerBean.searchReference(AihatConstants.REF_TYPE_USER, referencedMail);
	}
	public synchronized String searchAllGenres() {
		ManageGenreBean manageGenreBean = (ManageGenreBean) BeanUtils.getContextBean("manageGenreBean");
		return manageGenreBean.searchReference(AihatConstants.REF_TYPE_USER, referencedMail);
	}
	
	public synchronized String clear() {
		cleanAllFields();
		BeanUtils.reloadPage();
		return null;
	}

	protected void cleanAllFields() {
		setIdCriterion(null);
		mailCriterion = null;
		blockedStatusCriterion = 0;
		adminCriterion = 0;
		setSearchResults(null);
	}

	
	public synchronized String delete() {
		List<UserDto> deleteUsers = getSelectedObjects();
		
		if(!AihatUtils.isEmpty(deleteUsers)) {
			try {
				userService.deleteUsers(deleteUsers);
				addInfoMessage("Deletion completed successfully.");
			} catch (DataAccessException e) {
				logger.error(AihatUtils.getLogForCollection(deleteUsers), e);
				addErrorMessage("Unable to delete users. Please try again later");
			}
		} else {
			addErrorMessage("Please select user to delete.");
		}
		
		search();
		return null;
	}

	public synchronized String block() {
		List<UserDto> blockedUsers = getSelectedObjects();

		if(!AihatUtils.isEmpty(blockedUsers)) {
			try {
				userService.blockUsers(blockedUsers);
				addInfoMessage("User block completed successfully.");
			} catch (DataAccessException e) {
				logger.error(AihatUtils.getLogForCollection(blockedUsers), e);
				addErrorMessage("Unable to block users. Please try again later");
			}
		} else {
			addErrorMessage("Please select user to block.");
		}

		search();
		return null;
	}
	
	public synchronized String unblock() {
		List<UserDto> unblockedUsers = getSelectedObjects();

		if(!AihatUtils.isEmpty(unblockedUsers)) {
			try {
				userService.unblockUsers(unblockedUsers);
				addInfoMessage("User unblock completed successfully.");
			} catch (DataAccessException e) {
				logger.error(AihatUtils.getLogForCollection(unblockedUsers), e);
				addErrorMessage("Unable to unblock users. Please try again later");
			}
		} else {
			addErrorMessage("Please select user to unblock.");
		}

		search();
		return null;
	}
	
	public synchronized String resetPassword() {
		List<UserDto> passResetUsers = getSelectedObjects();

		if(!AihatUtils.isEmpty(passResetUsers)) {
			try {
				userService.resetPassword(passResetUsers);
				addInfoMessage("Password reset completed successfully.");
			} catch (DataAccessException e) {
				logger.error(AihatUtils.getLogForCollection(passResetUsers), e);
				addErrorMessage("Unable to reset users 's password. Please try again later");
			}
		} else {
			addErrorMessage("Please select user to reset password.");
		}

		search();
		return null;
	}
	
	public String searchReference(String referenceField, Object value) {
		//user is not referenced by any bean
		return null;
	}
	
	//getter setter
	public String getMailCriterion() {
		return mailCriterion;
	}

	public void setMailCriterion(String mailCriterion) {
		this.mailCriterion = mailCriterion;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public int getBlockedStatusCriterion() {
		return blockedStatusCriterion;
	}

	public void setBlockedStatusCriterion(int blockedStatusCriterion) {
		this.blockedStatusCriterion = blockedStatusCriterion;
	}

	public String getReferencedMail() {
		return referencedMail;
	}

	public void setReferencedMail(String referencedMail) {
		this.referencedMail = referencedMail;
	}

	public int getAdminCriterion() {
		return adminCriterion;
	}

	public void setAdminCriterion(int adminCriterion) {
		this.adminCriterion = adminCriterion;
	}
	
	
}

package net.aihat.bean.admin.genre;

import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;

public class ManageGenreCreateBean extends GenreDetailBaseBean {
	public ManageGenreCreateBean() {
		logger = Logger.getLogger(ManageGenreCreateBean.class);
	}
	
	public String getBeanName() {
		return "manageGenreCreateBean";
	}
	
	public synchronized String init() {
		return null;
	}
	
	public synchronized String create() {
		storeGenre();
		BeanUtils.reloadPage();
		return null;
	}
	
	public synchronized String clear() {
		cleanAllFields();
		BeanUtils.reloadPage();
		return null;
	}
}

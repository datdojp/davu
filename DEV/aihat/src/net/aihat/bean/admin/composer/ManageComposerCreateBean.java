package net.aihat.bean.admin.composer;

import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;

public class ManageComposerCreateBean extends ComposerDetailBaseBean {
	public ManageComposerCreateBean() {
		logger = Logger.getLogger(ManageComposerCreateBean.class);
	}
	
	public String getBeanName() {
		return "manageComposerCreateBean";
	}
	
	
	public String init() {
		return null;
	}
	
	public synchronized String create() {
		storeComposer();
		BeanUtils.reloadPage();
		return null;
	}
	
	public synchronized String clear() {
		cleanAllFields();
		BeanUtils.reloadPage();
		return null;
	}
}

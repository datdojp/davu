package net.aihat.bean.admin.singer;

import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class ManageSingerCreateBean extends SingerDetailBaseBean {
	public ManageSingerCreateBean() {
		logger = Logger.getLogger(ManageSingerCreateBean.class);
	}
	
	public String getBeanName() {
		return "manageSingerCreateBean";
	}
	
	
	public synchronized String init() {
		return null;
	}
	
	public synchronized String create() {
		storeSinger();
		BeanUtils.reloadPage();
		return null;
	}
	
	public synchronized String clear() {
		cleanAllFields();
		BeanUtils.reloadPage();
		return null;
	}
}

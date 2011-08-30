package net.aihat.bean;

import javax.faces.event.AjaxBehaviorEvent;

public interface PagingBean {
	public void nextPage(AjaxBehaviorEvent e);
	public void previousPage(AjaxBehaviorEvent e);
	public void firstPage(AjaxBehaviorEvent e);
	public void lastPage(AjaxBehaviorEvent e);
	public String getPageRange();
}

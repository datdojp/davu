package net.aihat.service;

import java.io.Serializable;

import net.aihat.utils.BeanUtils;

public class ConfigurationService implements Serializable {
	//getter setter
	public static String getImageBackupFolder() {
		return BeanUtils.getConfig("service.imageBackupFolder");
	}

	public static int getnHomepageClips() {
		return Integer.parseInt(BeanUtils.getConfig("service.homepage.nClips"));
	}

	public static int getnFeaturedClips() {
		return Integer.parseInt(BeanUtils.getConfig("client.nFeaturedClips"));
	}

	public static long getnRowsPerPage() {
		return Long.parseLong(BeanUtils.getConfig("client.nRowsPerPage"));
	}

	public static long getnFollowerToDisplay() {
		return Long.parseLong(BeanUtils.getConfig("client.nFollowerToDisplay"));
	}
	
//	<bean id="configurationService" class="net.aihat.service.ConfigurationService">
//		<property name="imageBackupFolder">
//			<value>${service.imageBackupFolder}</value>
//		</property>
//		<property name="nHomepageClips">
//			<value>${service.homepage.nClips}</value>
//		</property>
//		<property name="nFeaturedClips">
//			<value>${client.nFeaturedClips}</value>
//		</property>
//		<property name="nRowsPerPage">
//			<value>${client.nRowsPerPage}</value>
//		</property>
//		<property name="nFollowerToDisplay">
//			<value>${client.nFollowerToDisplay}</value>
//		</property>
//	</bean>
}

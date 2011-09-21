package net.aihat.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.aihat.dto.BaseDto;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;

import org.jasypt.digest.PooledStringDigester;

public class AihatUtils {
	
	//PASSWORD ENCRYPTION
	private static PooledStringDigester digester = new PooledStringDigester();
	static {
		digester.setPoolSize(7); 
		digester.setAlgorithm("SHA-1");
		digester.setIterations(2510);
	}
	public static String encryptPassword(String password) {
		return digester.digest(password);
	}
	public static boolean isPasswordMatch(String inputPassword, String encryptedPassword) {
		return AihatUtils.isEmpty(encryptedPassword) || digester.matches(inputPassword, encryptedPassword);
	}
	
	public static String getRandomPassword() {
		return Long.toString(Calendar.getInstance().getTimeInMillis());
	}
	
	public static boolean checkPasswordSafeEnough(String password) {
		//check empty
		if(AihatUtils.isEmpty(password)) {
			return false;
		}
		//password length >= 3
		if(password.length() < 3) {
			return false;
		}
		return true;
	}
	
	//VALIDATOR
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
	public static boolean isEmpty(List list) {
		return list == null || list.size() == 0;
	}
	public static boolean isValidId(Integer id) {
		return id != null && id > 0;
	}
	public static boolean isEmpty(Object[] arr) {
		return arr == null || arr.length == 0;
	}
	
	//FORMATTER
	public static String getLogForCollection(List dtos) {
		String log = "";
		if(!isEmpty(dtos)) {
			for(Object aDto : dtos) {
				log = log + "\n" + ((BaseDto)aDto).forLog();
			}
		}
		return log;
	}
	public static String getImageFilename(BaseDto dto) {
		if(dto == null || !isValidId(dto.getId())) {
			return "";
		}
		String result = "";
		if(dto instanceof SingerDto) {
			result = "sng";
		} else if(dto instanceof ComposerDto) {
			result = "cmp";
		} else if(dto instanceof UserDto) {
			result = "usr";
		} else {
			return "";
		}
		
		return result + dto.getId();
	}
	public static String getFileExtension(String filename) {
		if(isEmpty(filename)) {
			return "";
		}
		if(filename.indexOf(".") < 0 || filename.endsWith(".")) {
			return "";
		}
		return filename.substring(filename.lastIndexOf(".") + 1);
	}
	
	public static BaseDto getDtoFromList(Integer id, List list) {
		if(AihatUtils.isEmpty(list) || id == null) {
			return null;
		}
		
		List<BaseDto> listDto = list;
		for(BaseDto aDto : listDto) {
			if(id.equals(aDto.getId())) {
				return aDto;//found
			}
		}
		return null;//not found
	}
	public static int getDtoIndex(Integer id, List list) {
		if(AihatUtils.isEmpty(list)) {
			return -1;
		}
		int i = 0;
		List<BaseDto> listDto = list;
		for(BaseDto aDto : listDto) {
			if(id.equals(aDto.getId())) {
				return i; 
			}
			i++;
		}
		return -1;
	}
	public static int getDtoIndex(BaseDto dto, List list) {
		if(dto == null) {
			return -1;
		} else {
			return getDtoIndex(dto.getId(), list);
		}
	}
	public static List getSelectedObjects(List list) {
		List results = new ArrayList();
		if(!AihatUtils.isEmpty(list)) {
			List<BaseDto> listDto = list;
			for(BaseDto aDto : listDto) {
				if(aDto.isSelectedInDataTable()) {
					results.add(aDto);
				}
			}
		}
		return results;
	}
	
	public static List<Integer> getSelectedIds(List list) {
		List<Integer> results = new ArrayList<Integer>();
		if(!AihatUtils.isEmpty(list)) {
			List<BaseDto> listDto = list;
			for(BaseDto aDto : listDto) {
				if(aDto.isSelectedInDataTable()) {
					results.add(aDto.getId());
				}
			}
		}
		return results;
	}
}

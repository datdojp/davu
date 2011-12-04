package net.aihat.dao;

import java.io.Serializable;

import net.aihat.utils.AihatUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public abstract class BaseDao extends SqlMapClientDaoSupport implements Serializable, Cloneable {
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	protected String getSQLSearchableString(String input) {
		if(AihatUtils.isEmpty(input) || AihatUtils.isEmpty(input.trim())) {
			return "%";
		}
		String result = getSearchableString(input.trim());
		if(result.indexOf(" ") >= 0) {
			result = StringUtils.replace(result, " ", "% %");
		}
		result = "%" + result + "%";
		return result;
	}
	
	private static final String ALL_VIETNAMESE_CHARACTERS =
		"ĂÂÁÀẢÃẠẮẰẲẴẶẤẦẨẪẬ" +
		"ăâáàảãạắằẳẵặấầẩẫậ" +
		"ƠÔÓÒỎÕỌỚỜỞỠỢỐỒỔỖỘ" +
		"ơôóòỏõọớờởỡợốồổỗộ" +
		"ƯÚÙỦŨỤỨỪỬỮỰ" +
		"ưúùủũụứừửữự" + 
		"ÍÌỈĨỊ" +
		"íìỉĩị" +
		"ÊÉÈẺẼẸẾỀỂỄỆ" +
		"êéèẻẽẹếềểễệ" +
		"ÝỲỶỸỴ" +
		"ýỳỷỹỵ" +
		"Đ" +
		"đ";
	private static final String ALL_VIETNAMESE_CHARACTERS_WITHOUT_SIGN =
		"AAAAAAAAAAAAAAAAA" +
		"aaaaaaaaaaaaaaaaa" +
		"OOOOOOOOOOOOOOOOO" +
		"ooooooooooooooooo" +
		"UUUUUUUUUUU" +
		"uuuuuuuuuuu" + 
		"IIIII" +
		"iiiii" +
		"EEEEEEEEEEE" +
		"eeeeeeeeeee" +
		"YYYYY" +
		"yyyyy" +
		"D" +
		"d";
//	private static final String[] ALL_VIETNAMESE_CHARACTERS_IN_ASCII = {
//		"A(", "A^", "A'", "A`", "A?", "A~", "A.", "A('", "A(`", "A(?", "A(~", "A(.", "A^'", "A^`", "A^?", "A^~", "A^.",
//		"a(", "a^", "a'", "a`", "a?", "a~", "a.", "a('", "a(`", "a(?", "a(~", "a(.", "a^'", "a^`", "a^?", "a^~", "a^.",
//		"O*", "O^", "O'", "O`", "O?", "O~", "O.", "O*'", "O*`", "O*?", "O*~", "O*.", "O^'", "O^`", "O^?", "O^~", "O^.",
//		"o*", "o^", "o'", "o`", "o?", "o~", "o.", "o*'", "o*`", "o*?", "o*~", "o*.", "o^'", "o^`", "o^?", "o^~", "o^.",
//		"U*", "U'", "U`", "U?", "U~", "U.", "U*'", "U*`", "U*?", "U*~", "U*.",
//		"u*", "u'", "u`", "u?", "u~", "u.", "u*'", "u*`", "u*?", "u*~", "u*.", 
//		"I'", "I`", "I?", "I~", "I.",
//		"i'", "i`", "i?", "i~", "i.",
//		"E^", "E'", "E`", "E?", "E~", "E.", "E^'", "E^`", "E^?", "E^~", "E^.",
//		"e^", "e'", "e`", "e?", "e~", "e.", "e^'", "e^`", "e^?", "e^~", "e^.",
//		"Y'", "Y`", "Y?", "Y~", "Y.",
//		"y'", "y`", "y?", "y~", "y.",
//		"DD",
//		"dd" }; 
	protected String getSearchableString(String input) {
		if(AihatUtils.isEmpty(input)) {
			return input;
		}
		int length = input.length();
		String result = "";
		for(int i = 0; i < length; i++) {
			String aChar = input.substring(i, i+1);
			int idx = ALL_VIETNAMESE_CHARACTERS.indexOf(aChar);
			if(idx >= 0) {
				aChar = ALL_VIETNAMESE_CHARACTERS_WITHOUT_SIGN.substring(idx, idx + 1);
			}
			result = result + aChar;
		}
		return result.toLowerCase();
	}
	
//	protected String getGetableString(String input) {
//		if(AihatUtils.isEmpty(input)) {
//			return input;
//		}
//		int length = input.length();
//		String result = "";
//		for(int i = 0; i < length; i++) {
//			String aChar = input.substring(i, i+1);
//			int idx = ALL_VIETNAMESE_CHARACTERS.indexOf(aChar);
//			if(idx >= 0) {
//				aChar = ALL_VIETNAMESE_CHARACTERS_IN_ASCII[idx];
//			}
//			result = result + aChar;
//		}
//		return result.toLowerCase();
//	}
	
	public Integer getLastInsertId() {
		return (Integer) getSqlMapClientTemplate().queryForObject("getLastInsertId");
	}
}

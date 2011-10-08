package net.aihat.utils;

import java.text.SimpleDateFormat;

public class AihatConstants {
	//language
	public static final String LANGUAGE_ENGLISH = "en";
	public static final String LANGUAGE_VIETNAMES = "vi";
	public static final String DEFAULT_LANGUAGE = LANGUAGE_VIETNAMES;
	public static final String DEFAULT_COUNTRY = "VN";
	
	//search 's categories
	public static final String SEARCH_CATEGORY_CLIP = "clip";
	public static final String SEARCH_CATEGORY_SINGER = "singer";
	public static final String SEARCH_CATEGORY_COMPOSER = "composer";
	public static final String SEARCH_CATEGORY_GENRE = "genre";
	public static final String SEARCH_CATEGORY_UPLOADER = "uploader";
	
	//reference types
	public static String REF_TYPE_SINGER = "singer";
	public static String REF_TYPE_COMPOSER = "composer";
	public static String REF_TYPE_GENRE = "genre";
	public static String REF_TYPE_USER = "user";
	
	//import type
	public static final int IMPORT_TYPE_CLIPS = 0;
	public static final int IMPORT_TYPE_SINGER = 1;
	public static final int IMPORT_TYPE_COMPOSER = 2;
	
	//date format
	public static final SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat SDF_YYYYMMDD_HHMMSS_SSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	static {
		SDF_YYYYMMDD.setLenient(false);
		SDF_YYYYMMDD_HHMMSS_SSS.setLenient(false);
	}
}

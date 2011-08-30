package net.aihat.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.aihat.bean.admin.autoimport.AutoImportBean.Field;
import net.aihat.dto.ClipDto;
import net.aihat.dto.ComposerDto;
import net.aihat.dto.GenreDto;
import net.aihat.dto.SingerDto;
import net.aihat.dto.UserDto;
import net.aihat.exception.AutoImportException;
import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class AutoImportServiceImpl extends ClipServiceImpl implements AutoImportService {
	private String defaultImage;
	public String getDefaultImage() {
		return defaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
//	/**
//     * UTF-8 BOM (EF BB BF).
//     */
//    public static final byte[] UTF_8 = new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF};
//
//    /**
//     * UTF-16, little-endian (FF FE).
//     */
//    public static final byte[] UTF_16_LE = new byte[]{(byte)0xFF, (byte)0xFE};
//
//    /**
//     * UTF-16, big-endian (FE FF).
//     */
//    public static final byte[] UTF_16_BE = new byte[]{(byte)0xFE, (byte)0xFF};
//    
//    /**
//     * UTF-32, little-endian (FF FE 00 00).
//     */
//    public static final byte[] UTF_32_LE = new byte[]{(byte)0xFF, (byte)0xFE, (byte)0x00, (byte)0x00};
//
//    /**
//     * UTF-32, big-endian (00 00 FE FF).
//     */
//    public static final byte[] UTF_32_BE = new byte[]{(byte)0x00, (byte)0x00, (byte)0xFE, (byte)0xFF};
	
	@Transactional(rollbackFor=AutoImportException.class)
	public void importData(File tempFile, List<Field> selectedFields, int selectedImportType, Integer userId, String language) throws IOException, AutoImportException {
		FileInputStream fileInputStream = new FileInputStream(tempFile);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF8");
		BufferedReader br = new BufferedReader(inputStreamReader);
		int lineCount = 1;
		while(true) {
			String aLine = br.readLine();
			if(aLine == null) {
				break;
			}
			
			//remove the BOM character
			if(lineCount == 1 && !AihatUtils.isEmpty(aLine)) {
				String bom = aLine.substring(0, 1);
				byte[] bomBytes = bom.getBytes();
//				if(bomBytes == UTF_8 || bomBytes == UTF_16_LE || bomBytes == UTF_16_BE || bomBytes == UTF_32_LE ||	bomBytes == UTF_32_BE) {
				if(bomBytes[0] == 63) {
					aLine = aLine.substring(1);
				}
			}
			
			processALine(aLine, selectedFields, lineCount, userId, selectedImportType, language);
			lineCount++;
		}
	}

	private void processALine(String aLine, List<Field> selectedFields, int lineCount, Integer userId, int selectedImportType, String language) throws AutoImportException {
		if(AihatUtils.isEmpty(aLine)) {
			return;
		}
		String[] fieldValues = aLine.split("\t");
		if(fieldValues.length != selectedFields.size()) {
			throw new AutoImportException("Select " + selectedFields.size() + " fields but there is(are) " + fieldValues.length + " value(s) at line " + lineCount);
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		try {
			if(AihatConstants.IMPORT_TYPE_CLIPS == selectedImportType) {
				ClipDto clip = new ClipDto();
				for(int i = 0; i < fieldValues.length; i++) {
					String aFieldValue = fieldValues[i];
					if(AihatUtils.isEmpty(aFieldValue)) {
						continue;
					}
					String fieldName = selectedFields.get(i).getName();
					if("title".equals(fieldName)) {
						clip.setTitle(aFieldValue);
					} else if("singer".equals(fieldName)) {
						clip.setSingers(new ArrayList<SingerDto>());
						String[] singerNames = aFieldValue.split(",");
						if(!AihatUtils.isEmpty(singerNames)) {
							for(String aSingerName : singerNames) {
								SingerDto singer = getSingerDao().getSingerByName(aSingerName);
								if(singer == null) {
									throw new AutoImportException("Singer '" + aSingerName + "' is not available. Line " + lineCount);
								}
								clip.getSingers().add(singer);
							}
						}
					} else if("composer".equals(fieldName)) {
						clip.setComposers(new ArrayList<ComposerDto>());
						String[] composerNames = aFieldValue.split(",");
						if(!AihatUtils.isEmpty(composerNames)) {
							for(String aComposerName : composerNames) {
								ComposerDto composer = getComposerDao().getComposerByName(aComposerName);
								if(composer == null) {
									throw new AutoImportException("Composer '" + aComposerName + "' is not available. Line " + lineCount); 
								}
								clip.getComposers().add(composer);
							}
						}
					} else if("genre".equals(fieldName)) {
						clip.setGenres(new ArrayList<GenreDto>());
						String[] genreNames = aFieldValue.split(",");
						if(!AihatUtils.isEmpty(genreNames)) {
							for(String aGenreName : genreNames) {
								GenreDto genre = getGenreDao().getGenreByName(aGenreName, language);
								if(genre == null) {
									throw new AutoImportException("Genre '" + aGenreName + "' is not available. Line " + lineCount);
								}
								clip.getGenres().add(genre);
							}
						}
					} else if("link".equals(fieldName)) {
						clip.setLink(aFieldValue);
					} else if("official".equals(fieldName)) {
						if("yes".equals(aFieldValue)) {
							clip.setOfficial(true);
						}
					}
				}
				clip.setUser(new UserDto());
				clip.getUser().setId(userId);
				getClipDao().insert(clip);
				addClipRelated(clip);
			} else if(AihatConstants.IMPORT_TYPE_SINGER == selectedImportType) {
				SingerDto singer = new SingerDto();
				for(int i = 0; i < fieldValues.length; i++) {
					String aFieldValue = fieldValues[i];
					if(AihatUtils.isEmpty(aFieldValue)) {
						continue;
					}
					String fieldName = selectedFields.get(i).getName();
					if("name".equals(fieldName)) {
						singer.setName(aFieldValue);
					} else if("birthday".equals(fieldName)) {
						singer.setBirthday(simpleDateFormat.parse(aFieldValue));
					} else if("company".equals(fieldName)) {
						singer.setCompany(aFieldValue);
					} else if("website".equals(fieldName)) {
						singer.setWebsite(aFieldValue);
					} else if("country".equals(fieldName)) {
						singer.setCountry(aFieldValue);
					} else if("description".equals(fieldName)) {//description
						String description = aFieldValue.replace("\\n", "\n").replace("\\t", "\t");
						singer.setDescription(description);
					}
				}
				singer.setUser(new UserDto());
				singer.getUser().setId(userId);
				singer.setImage(defaultImage);//TODO test it
				getSingerDao().insert(singer);
			} else if(AihatConstants.IMPORT_TYPE_COMPOSER == selectedImportType) {
				ComposerDto composer = new ComposerDto();
				for(int i = 0; i < fieldValues.length; i++) {
					String aFieldValue = fieldValues[i];
					if(AihatUtils.isEmpty(aFieldValue)) {
						continue;
					}
					String fieldName = selectedFields.get(i).getName();
					if("name".equals(fieldName)) {
						composer.setName(aFieldValue);
					} else if("birthday".equals(fieldName)) {
						composer.setBirthday(simpleDateFormat.parse(aFieldValue));
					} else if("country".equals(fieldName)) {
						composer.setCountry(aFieldValue);
					} else if("description".equals(fieldName)) {
						String description = aFieldValue.replace("\\n", "\n").replace("\\t", "\t");
						composer.setDescription(description);
					}
				}
				composer.setUser(new UserDto());
				composer.getUser().setId(userId);
				composer.setImage(defaultImage);//TODO test it
				getComposerDao().insert(composer);
			}
		} catch (ParseException e) {
			throw new AutoImportException("Invalid date format at line " + lineCount + ". It must be dd.mm.yyyy", e);
		} catch (DataAccessException e) {
			throw new AutoImportException("Error at line " + lineCount, e);
		}
	}
}

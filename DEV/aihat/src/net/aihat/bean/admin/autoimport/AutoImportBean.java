package net.aihat.bean.admin.autoimport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.aihat.bean.BaseBean;
import net.aihat.exception.AutoImportException;
import net.aihat.service.AutoImportService;
import net.aihat.utils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class AutoImportBean extends BaseBean {
	public AutoImportBean() {
		logger = Logger.getLogger(AutoImportBean.class);	
	}
	public String getBeanName() {
		return "autoImportBean";
	}

	private UploadedFile uploadedFile;
	private int selectedImportType = -1;
	private Field[] clipFields;
	private Field[] singerFields;
	private Field[] composerFields;

	private AutoImportService autoImportService;

	public synchronized String upload() {
		try {
			//save the uploaded file to a temp file
			String tempFilename = Calendar.getInstance().getTimeInMillis() + ".tmp";
			File tempFile = new File(tempFilename);
			tempFile.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(uploadedFile.getBytes());
			bufferedOutputStream.close();

			//import
			List<Field> selectedFields = new ArrayList<AutoImportBean.Field>();
			Field[][] allFields = new Field[][] {clipFields, singerFields, composerFields};
			for(Field aField : allFields[selectedImportType]) {
				if(aField.isSelected()) {
					selectedFields.add(aField);
				}
			}
			Integer userId = BeanUtils.getUserProfileBean().getProfile().getId();
			String language = BeanUtils.getUserProfileBean().getProfile().getLanguage();
			autoImportService.importData(tempFile, selectedFields, selectedImportType, userId, language);

			//delete temp file
			tempFile.delete();

			addInfoMessage("Auto-import completed successfully.");
		} catch (IOException e) {
			logger.error("Auto-Import failed", e);
			addErrorMessage("Auto-Import failed. Please try again.");
		} catch (AutoImportException e) {
			logger.error("Auto-Import failed", e);
			addErrorMessage(e.getMessage());
		}

		BeanUtils.reloadPage();
		return null;
	}

	public synchronized String selectType() {
		cleanAllFields();
		BeanUtils.reloadPage();
		return null;
	}

	protected void cleanAllFields() {
		clipFields = new Field[] {
				new Field("title", "", true),
				new Field("singer", "if many singers sang the song, seperated by \",\"", false),
				new Field("composer", "if many composers composed the song, seperated by \",\"", false),
				new Field("genre", "if the song is classified in many genres, seperated by \",\"", false),
				new Field("link", "youtube id only", true),
				new Field("official", "yes/no", false)
		};
		singerFields = new Field[] {
				new Field("name", "", true),
				new Field("birthday", "dd.mm.yyyy", false),
				new Field("company", "", false),
				new Field("website", "", false),
				new Field("country", "international code, for example: vn, en, us, jp...", false),
				new Field("description", "replace all endlined by \"\\n\", all tabd by \t", false)
		};
		composerFields = new Field[] {
				new Field("name", "", true),
				new Field("birthday", "dd.mm.yyyy", false),
				new Field("country", "international code, for example: vn, en, us, jp...", false),
				new Field("description", "replace all endlined by \"\\n\", all tabd by \t", false)
		};
	}

	public class Field {
		private String name;
		private String note;
		private boolean isMandatory;
		private boolean selected;

		//constructor
		public Field() {}

		public Field(String name, String note, boolean isMandatory) {
			super();
			this.name = name;
			this.note = note;
			this.isMandatory = isMandatory;
			if(isMandatory) {
				selected = true;
			}
		}

		//getter setter
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public boolean isMandatory() {
			return isMandatory;
		}

		public void setMandatory(boolean isMandatory) {
			this.isMandatory = isMandatory;
		}
	}

	//getter setter
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public int getSelectedImportType() {
		return selectedImportType;
	}

	public void setSelectedImportType(int selectedImportType) {
		this.selectedImportType = selectedImportType;
	}

	public Field[] getClipFields() {
		return clipFields;
	}

	public void setClipFields(Field[] clipFields) {
		this.clipFields = clipFields;
	}

	public Field[] getSingerFields() {
		return singerFields;
	}

	public void setSingerFields(Field[] singerFields) {
		this.singerFields = singerFields;
	}

	public Field[] getComposerFields() {
		return composerFields;
	}

	public void setComposerFields(Field[] composerFields) {
		this.composerFields = composerFields;
	}

	public AutoImportService getAutoImportService() {
		return autoImportService;
	}

	public void setAutoImportService(AutoImportService autoImportService) {
		this.autoImportService = autoImportService;
	}

}

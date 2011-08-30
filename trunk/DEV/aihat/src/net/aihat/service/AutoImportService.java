package net.aihat.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import net.aihat.bean.admin.autoimport.AutoImportBean.Field;
import net.aihat.exception.AutoImportException;

public interface AutoImportService {
	public void importData(File tempFile, List<Field> selectedFields, int selectedImportType, Integer userId, String language) throws IOException, AutoImportException;
}

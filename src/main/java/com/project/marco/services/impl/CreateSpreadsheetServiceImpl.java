package com.project.marco.services.impl;
import com.project.marco.config.ConfigProperties;
import jxl.Workbook;
import com.project.marco.services.CreateSpreadsheetService;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

@Service
public class CreateSpreadsheetServiceImpl implements CreateSpreadsheetService {

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public HttpStatus createSpreadsheet() throws IOException, WriteException {
        String filename = configProperties.getFileXls();
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook =
                Workbook.createWorkbook(new File(filename), ws);
        WritableSheet s = workbook.createSheet("Folha1", 0);
        WritableSheet s1 = workbook.createSheet("Folha1", 0);
//        writeDataSheet(s);
//        writeImageSheet(s1);
        workbook.write();
        workbook.close();
        return HttpStatus.OK;
    }
}

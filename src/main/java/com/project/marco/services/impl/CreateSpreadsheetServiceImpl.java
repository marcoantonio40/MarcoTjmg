package com.project.marco.services.impl;

import com.project.marco.config.ConfigProperties;
import com.project.marco.services.CreateSpreadsheetService;
import com.project.marco.util.TjmgUtils;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import jxl.write.Number;
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

    @Autowired
    private TjmgUtils tjmgUtils;

    @Override
    public HttpStatus createSpreadsheet() {

        try{
            String filename = tjmgUtils.createNameFile(configProperties.getFileXls());

            WritableWorkbook workbook = setConfigSreadSheet(filename);
            WritableSheet s = workbook.createSheet("Folha1", 0);
            //WritableSheet s1 = workbook.createSheet("Folha1", 0);
            writeDataSheet(s);
            //writeImageSheet(s1);
            workbook.write();
            workbook.close();
            return HttpStatus.OK;
        } catch (Exception e){
            return HttpStatus.NOT_FOUND;
        }

    }

    private WritableWorkbook setConfigSreadSheet(String filename) throws IOException {
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(new File(filename), ws);
        return workbook;
    }

    private static void writeDataSheet(WritableSheet s) throws WriteException {

        WritableFont wf = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
        WritableCellFormat cf = new WritableCellFormat(wf);

        cf.setWrap(true);

        Label l = new Label(0,0,"Data");
        s.addCell(l);

        NumberFormat dt = new NumberFormat("##.####");
        WritableCellFormat cf1 = new WritableCellFormat(dt);
        Number numero = new Number(3,1, 1.1234,cf1);
        s.addCell(numero);
    }
}

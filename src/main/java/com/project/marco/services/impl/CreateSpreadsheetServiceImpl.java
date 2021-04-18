package com.project.marco.services.impl;

import com.project.marco.config.ConfigProperties;
import com.project.marco.repository.SavingsIndexRepository;
import com.project.marco.services.CreateSpreadsheetService;
import com.project.marco.services.SavingsIndexService;
import com.project.marco.util.TjmgUtils;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
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

    @Autowired
    private SavingsIndexService savingsIndexService;

    @Autowired
    private SavingsIndexRepository savingsIndexRepository;

    @Override
    public HttpStatus createSpreadsheet() {

        try{
            String filename = tjmgUtils.createNameFile(configProperties.getFileXls());

            WritableWorkbook workbook = setConfigSreadSheet(filename);
            WritableSheet sheet = workbook.createSheet("Folha1", 0);
            writeDataSheet(sheet);
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

    private void writeDataSheet(WritableSheet sheet) throws Exception {

        createHeader(sheet, 130518.08,47.46 );

        savingsIndexService.savingsIndex();

    }



    private static void createHeader(WritableSheet sheet, Double valorAtualizado, Double reais) throws WriteException {

        WritableFont wf = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
        WritableFont wfNumber = new WritableFont(WritableFont.ARIAL,10, WritableFont.NO_BOLD);

        WritableCellFormat cf = new WritableCellFormat(wf);
        WritableCellFormat cfNumber = new WritableCellFormat(wfNumber);

        cf.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
        cfNumber.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

        cf.setAlignment(Alignment.CENTRE);
        cfNumber.setAlignment(Alignment.RIGHT);

        cf.setVerticalAlignment(VerticalAlignment.CENTRE);

        cf.setWrap(true);
        cfNumber.setWrap(true);

        Label l1 = new Label(0,0,"Saldo atualizado em jun/94",cf);
        Label l2 = new Label(1,0,"Conversão de Cruzeiros reais para Reais (Divide-se por 2.750)",cf);
        Label l3 = new Label(2,0,"Base Legal",cf);
        Label l4 = new Label(0,1,valorAtualizado.toString(), cfNumber);
        Label l5 = new Label(1,1,reais.toString(), cfNumber);
        Label l6 = new Label(2,1,"Leis nº 8880, de 27/05/1994 e 9069, de 29/06/1995",cf);
        Label l7 = new Label(0,3,"Período de rendimento",cf);
        Label l8 = new Label(1,3,"Moeda Vigente no Período",cf);
        Label l9 = new Label(2,3,"Valor Base de Cálculo",cf);
        Label l10 = new Label(3,3,"Rendimento (%)",cf);
        Label l11 = new Label(4,3,"Rendimento a partir do valor base",cf);
        Label l12 = new Label(5,3,"Saldo Atualizado",cf);
        Label l13 = new Label(6,3,"% Correção Monetária",cf);
        Label l14 = new Label(7,3,"Valor Correção",cf);
        Label l15 = new Label(8,3,"Valor Atualizado",cf);

        sheet.mergeCells(2,0,5,0);
        sheet.mergeCells(2,1,5,1);

        sheet.setRowView(0, 1200);
        sheet.setRowView(3, 1000);
        sheet.setColumnView(0, 15);
        sheet.setColumnView(1, 18);
        sheet.setColumnView(2, 14);
        sheet.setColumnView(3, 14);
        sheet.setColumnView(4, 14);
        sheet.setColumnView(5, 14);
        sheet.setColumnView(6, 14);
        sheet.setColumnView(7, 14);
        sheet.setColumnView(8, 14);
        sheet.addCell(l1);
        sheet.addCell(l2);
        sheet.addCell(l3);
        sheet.addCell(l4);
        sheet.addCell(l5);
        sheet.addCell(l6);
        sheet.addCell(l7);
        sheet.addCell(l8);
        sheet.addCell(l9);
        sheet.addCell(l10);
        sheet.addCell(l11);
        sheet.addCell(l12);
        sheet.addCell(l13);
        sheet.addCell(l14);
        sheet.addCell(l15);
    }
}

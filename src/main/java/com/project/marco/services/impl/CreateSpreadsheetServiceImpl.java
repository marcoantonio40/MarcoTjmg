package com.project.marco.services.impl;

import com.project.marco.config.ConfigProperties;
import com.project.marco.services.CreateSpreadsheetService;
import com.project.marco.util.TjmgUtils;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Label;
import jxl.write.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.*;
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

    private static void writeDataSheet(WritableSheet sheet) throws WriteException {

        createHeader(sheet, 130518.08,47.46 );

//        WritableFont wf = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
//        WritableCellFormat cf = new WritableCellFormat(wf);
//
//        cf.setWrap(true);
//
//        Label l = new Label(0,0,"Data");
//        sheet.addCell(l);
//
//        NumberFormat dt = new NumberFormat("##.####");
//        WritableCellFormat cf1 = new WritableCellFormat(dt);
//        Number numero = new Number(3,1, 1.1234,cf1);
//        sheet.addCell(numero);
    }

    private static void createHeader(WritableSheet sheet, Double valorAtualizado, Double reais) throws WriteException {

        WritableFont wf = new WritableFont(WritableFont.ARIAL,11, WritableFont.BOLD);

        WritableCellFormat cf = new WritableCellFormat(wf);
        cf.setBorder(Border.ALL, BorderLineStyle.THICK);
        cf.setAlignment(Alignment.getAlignment(-2));

        cf.setWrap(true);

        Label l1 = new Label(0,0,"Saldo atualizado em jun/94",cf);
        Label l2 = new Label(1,0,"Conversão de Cruzeiros reais para Reais (Divide-se por 2.750)",cf);
        Label l3 = new Label(2,0,"Base Legal",cf);
        Label l4 = new Label(0,1,valorAtualizado.toString());
        Label l5 = new Label(1,1,reais.toString());
        Label l6 = new Label(2,1,"Leis no 8880, de 27/05/1994 e 9069, de 29/06/1995",cf);
        Label l7 = new Label(0,3,"Período de rendimento",cf);
        Label l8 = new Label(1,3,"Moeda Vigente no Período",cf);
        Label l9 = new Label(2,3,"Valor Base de Cálculo",cf);
        Label l10 = new Label(3,3,"Rendimento (%)",cf);
        Label l11 = new Label(4,3,"Rendimento a partir do valor base",cf);
        Label l12 = new Label(5,3,"Saldo Atualizado",cf);
        Label l13 = new Label(6,3,"% Correção Monetária",cf);
        Label l14 = new Label(7,3,"Valor Correção",cf);
        Label l15 = new Label(8,3,"Valor Atualizado",cf);
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

package com.project.marco.services.impl;

import com.project.marco.config.ConfigProperties;
import com.project.marco.model.RestatementEntity;
import com.project.marco.model.SavingsIndexEntity;
import com.project.marco.model.SavingsIndexId;
import com.project.marco.repository.RestatementRepository;
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
import jxl.write.Number;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CreateSpreadsheetServiceImpl implements CreateSpreadsheetService {

    private static final int MONTH_START_REAL = 6;
    private static final int YEAR_START_REAL = 1994;
    int index = 0;
    boolean firstIteration = true;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private TjmgUtils tjmgUtils;

    @Autowired
    private SavingsIndexService savingsIndexService;

    @Autowired
    private SavingsIndexRepository savingsIndexRepository;

    @Autowired
    private RestatementRepository restatementRepository;

    @Override
    public HttpStatus createSpreadsheet() {

        try {
            String filename = tjmgUtils.createNameFile(configProperties.getFileXls());

            WritableWorkbook workbook = setConfigSreadSheet(filename);
            WritableSheet sheet = workbook.createSheet("Folha1", 0);
            writeDataSheet(sheet);
            workbook.write();
            workbook.close();
            return HttpStatus.OK;
        } catch (Exception e) {
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

        createHeader(sheet, 130518.08);

        savingsIndexService.savingsIndex();

        makeCalc(sheet, 2014, 9);

    }

    private void makeCalc(WritableSheet sheet, int anoInicio, int mesInicio) throws Exception {

        try {
            List<RestatementEntity> restatements = restatementRepository.findAll();
            List<RestatementEntity> restatementsValid = restatements
                    .stream()
                    .filter(r -> r.getRestatementId().getYearFactor() >= anoInicio)
                    .filter(r -> !(r.getRestatementId().getYearFactor() == anoInicio && r.getRestatementId().getMes() < mesInicio))
                    .collect(Collectors.toList());


            List<SavingsIndexEntity> savingsIndexes = savingsIndexRepository.findAll();

            List<SavingsIndexEntity> savingsIndexReal = savingsIndexes
                    .stream()
                    .filter(s -> s.getSavingsIndexId().getAno() > 1993)
                    .filter(s -> !(s.getSavingsIndexId().getAno() == 1994 && s.getSavingsIndexId().getMes() < 6))
                    .collect(Collectors.toList());

            int row = 4;
            int numberRow;
            Double value = 1d;

            WritableCellFormat cellFormatCifra = new WritableCellFormat();
            cellFormatCifra.setAlignment(Alignment.CENTRE);
            WritableCell writableCell = sheet.getWritableCell(1, 1);
            NumberFormat format2 = new NumberFormat("#.##");
            WritableCellFormat cellFormat2 = new WritableCellFormat(format2);


            for (SavingsIndexEntity savingsIndexEntityAux : savingsIndexReal) {
                numberRow = row + 1;
                SavingsIndexId savingsIndexId = savingsIndexEntityAux.getSavingsIndexId();
                String data = savingsIndexId.getMes() + "/" + savingsIndexId.getAno();

                value = getTaxReferencial(value, savingsIndexId);

                if (!(savingsIndexId.getMes() == MONTH_START_REAL && savingsIndexId.getAno() == YEAR_START_REAL)) {
                    makeCalcFromReal(sheet, row, numberRow, value, cellFormatCifra, writableCell, cellFormat2, data);
                    if (savingsIndexId.getAno() >= anoInicio) {
                        int size = restatementsValid.size();
                        if(index< size){
                            makeCalcFromStartOfLawsuit(sheet, row, numberRow,
                                    value, cellFormatCifra, cellFormat2, data,
                                    restatementsValid.get(index).getFactorMes(),
                                    firstIteration, savingsIndexId.getAno(),
                                    savingsIndexId.getMes(), anoInicio, mesInicio);
                        }
                    }
                    row++;
                }

                value = savingsIndexEntityAux.getValue();
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    private Double getTaxReferencial(Double value, SavingsIndexId savingsIndexId) {
        if(savingsIndexId.getAno() == 2017){
            if(savingsIndexId.getMes() >= 10){
                value = 0.5d;
            }
        } else if(savingsIndexId.getAno() > 2017) {
            value = 0.5d;
        }
        return value;
    }

    private void makeCalcFromStartOfLawsuit(WritableSheet sheet, int row, int numberRow, Double value,
                                            WritableCellFormat cellFormatCifra, WritableCellFormat cellFormat2,
                                            String data, Double factorMes, boolean firstIteration, int anoFluxo,
                                            int mesFluxo, int anoInicio, int mesInicio) throws Exception {


        if (anoFluxo == anoInicio) {
            if (mesFluxo == mesInicio) {
                index++;
                makeCalcFromStartOfLawsuitFact(sheet, row, numberRow, value, cellFormatCifra, cellFormat2, data, factorMes, firstIteration);

            } else if (mesFluxo > mesInicio) {
                index++;
                firstIteration = false;
                makeCalcFromStartOfLawsuitFact(sheet, row, numberRow, value, cellFormatCifra, cellFormat2, data, factorMes, firstIteration);

            }
        } else {
            index++;
            firstIteration = false;
            makeCalcFromStartOfLawsuitFact(sheet, row, numberRow, value, cellFormatCifra, cellFormat2, data, factorMes, firstIteration);
        }

    }

    private void makeCalcFromStartOfLawsuitFact(WritableSheet sheet, int row, int numberRow,
                                                Double value, WritableCellFormat cellFormatCifra, WritableCellFormat cellFormat2,
                                                String data, Double factorMes, boolean firstIteration) throws Exception {

        try {
            Label label = new Label(0, row, data);
            sheet.addCell(label);

            label = new Label(1, row, "R$", cellFormatCifra);
            sheet.addCell(label);

            Formula formula;
            if (firstIteration) {
                formula = new Formula(2, row, "F" + (numberRow - 1) + "+0", cellFormat2);
                sheet.addCell(formula);
            } else {
                formula = new Formula(2, row, "I" + (numberRow - 1) + "+0", cellFormat2);
                sheet.addCell(formula);
            }

            Number number = new Number(3, row, value);
            sheet.addCell(number);

            formula = new Formula(4, row, "C" + numberRow + "*(D" + numberRow + "/100)", cellFormat2);
            sheet.addCell(formula);

            formula = new Formula(5, row, "E" + numberRow + "+C" + numberRow, cellFormat2);
            sheet.addCell(formula);

            if(!(factorMes == 0)){
                number = new Number(6, row, factorMes);
                sheet.addCell(number);

                formula = new Formula(7, row, "F" + numberRow + "*(G" + numberRow + "/100)", cellFormat2);
                sheet.addCell(formula);

                formula = new Formula(8, row, "F" + numberRow + "+H" + numberRow, cellFormat2);
                sheet.addCell(formula);
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    private void makeCalcFromReal(WritableSheet sheet, int row, int numberRow,
                                  Double value, WritableCellFormat cellFormatCifra, WritableCell writableCell,
                                  WritableCellFormat cellFormat2, String data) throws Exception {

        try{
            Label label = new Label(0, row, data);
            sheet.addCell(label);

            label = new Label(1, row, "R$", cellFormatCifra);
            sheet.addCell(label);

            Formula formula;
            if (row == 4) {
                formula = new Formula(2, row, writableCell.getContents(), cellFormat2);
                sheet.addCell(formula);
            } else {
                formula = new Formula(2, row, "F" + (numberRow - 1) + "+0", cellFormat2);
                sheet.addCell(formula);
            }

            Number number = new Number(3, row, value);
            sheet.addCell(number);

            formula = new Formula(4, row, "C" + numberRow + "*(D" + numberRow + "/100)", cellFormat2);
            sheet.addCell(formula);

            formula = new Formula(5, row, "E" + numberRow + "+C" + numberRow, cellFormat2);
            sheet.addCell(formula);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }


    }


    private static void createHeader(WritableSheet sheet, Double valorCruzeiro) throws Exception {

        try {
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableFont wfNumber = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);

            WritableCellFormat cf = new WritableCellFormat(wf);
            WritableCellFormat cfNumber = new WritableCellFormat(wfNumber);

            cf.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            cfNumber.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

            cf.setAlignment(Alignment.CENTRE);
            cfNumber.setAlignment(Alignment.RIGHT);

            cf.setVerticalAlignment(VerticalAlignment.CENTRE);

            cf.setWrap(true);
            cfNumber.setWrap(true);

            Label l1 = new Label(0, 0, "Saldo atualizado em jun/94", cf);
            Label l2 = new Label(1, 0, "Conversão de Cruzeiros reais para Reais (Divide-se por 2.750)", cf);
            Label l3 = new Label(2, 0, "Base Legal", cf);

            NumberFormat format = new NumberFormat("#.##");
            WritableCellFormat cellFormatNumber = new WritableCellFormat(format);
            Number number = new Number(0, 1, valorCruzeiro);
            sheet.addCell(number);
            Formula formula = new Formula(1, 1, "A2/2750", cellFormatNumber);
            sheet.addCell(formula);

            Label l6 = new Label(2, 1, "Leis nº 8880, de 27/05/1994 e 9069, de 29/06/1995", cf);
            Label l7 = new Label(0, 3, "Período de rendimento", cf);
            Label l8 = new Label(1, 3, "Moeda Vigente no Período", cf);
            Label l9 = new Label(2, 3, "Valor Base de Cálculo", cf);
            Label l10 = new Label(3, 3, "Rendimento (%)", cf);
            Label l11 = new Label(4, 3, "Rendimento a partir do valor base", cf);
            Label l12 = new Label(5, 3, "Saldo Atualizado", cf);
            Label l13 = new Label(6, 3, "% Correção Monetária", cf);
            Label l14 = new Label(7, 3, "Valor Correção", cf);
            Label l15 = new Label(8, 3, "Valor Atualizado", cf);

            sheet.mergeCells(2, 0, 5, 0);
            sheet.mergeCells(2, 1, 5, 1);

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
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
}

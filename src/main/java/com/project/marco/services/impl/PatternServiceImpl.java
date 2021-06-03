package com.project.marco.services.impl;

import com.project.marco.config.ConfigProperties;
import com.project.marco.model.RestatementEntity;
import com.project.marco.model.RestatementId;
import com.project.marco.repository.RestatementRepository;
import com.project.marco.services.CreateSpreadsheetService;
import com.project.marco.services.PatternService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class PatternServiceImpl implements PatternService {

    public static final int INIT_YEAR = 1964;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private RestatementRepository repository;

    @Autowired
    private CreateSpreadsheetService createSpreadsheetService;

    @Override
    public HttpStatus formatToPattern(int anoDocumento, int mesDocumento, int anoInicioProcesso, int mesInicioProcesso) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(configProperties.getFileTxt()));
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                checksLineIsValid(linha, anoDocumento, mesDocumento);
            }

            br.close();
            createSpreadsheetService.createSpreadsheet(anoInicioProcesso, mesInicioProcesso);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    private void checksLineIsValid(String linha, int anoDocumento, int mesDocumento) {
        String year = linha.substring(0, 4);
        try {
            int yearValid = Integer.parseInt(year);
            if (yearValid >= INIT_YEAR && yearValid <= LocalDateTime.now().getYear()) {
                extractToRestatement(linha, yearValid, anoDocumento, mesDocumento);
            }
        } catch (Exception e) {
        }

    }

    private void extractToRestatement(String linha, int yearValid, int anoDocumento, int mesDocumento) {
        int x = 0;
        String newLineWithoutYearsWithComma = linha.substring(5, (linha.length() - 5));
        String newLineWithoutYearsWithPoint = newLineWithoutYearsWithComma.replace(",", ".");
        String[] monthsString = newLineWithoutYearsWithPoint.split(" ");
        if (monthsString.length < 12) {
            monthsString = fillVector(monthsString);
        }
        Double[] monthsDouble = toDouble(monthsString);
        prepareToSave(monthsDouble, yearValid, anoDocumento, mesDocumento);

    }

    private String[] fillVector(String[] monthsString) {
        String[] monthsStringFilled = new String[12];
        for (int i = 0; i < 12; i++) {
            if (i < monthsString.length) {
                monthsStringFilled[i] = monthsString[i];
            } else {
                monthsStringFilled[i] = "";
            }
        }
        return monthsStringFilled;
    }

    private Double[] toDouble(String[] monthsString) {

        int length = monthsString.length;
        Double[] monthsDouble = new Double[12];

        for (int i = 0; i < 12; i++) {
            if (monthsString[i].isEmpty()) {
                monthsDouble[i] = 0.0;
            } else {
                monthsDouble[i] = Double.parseDouble(monthsString[i]);
            }

        }

        return monthsDouble;
    }

    private void prepareToSave(Double[] monthsDouble, int yearValid, int anoDocumento, int mesDocumento) {
        RestatementEntity restatementEntity = new RestatementEntity();
        RestatementId restatementId = createId(yearValid, anoDocumento, mesDocumento);
        if (yearValid == INIT_YEAR) {
            createRestament1964(monthsDouble, restatementEntity, restatementId);

        } else {
            createRestamentAnyYear(monthsDouble, restatementEntity, restatementId);
            repository.save(restatementEntity);
        }
    }

    private void createRestamentAnyYear(Double[] monthsDouble, RestatementEntity restatementEntity, RestatementId restatementId) {
        for (int i = 1; i <= 12; i++) {
            restatementId.setMes(i);
            restatementEntity.setRestatementId(restatementId);
            restatementEntity.setFactorMes(monthsDouble[i - 1]);
            repository.save(restatementEntity);
        }


    }

    private void createRestament1964(Double[] monthsDouble, RestatementEntity restatementEntity, RestatementId restatementId) {
        for (int i = 10; i <= 12; i++) {
            restatementId.setMes(i);
            restatementEntity.setRestatementId(restatementId);
            restatementEntity.setFactorMes(monthsDouble[i - 10]);
            repository.save(restatementEntity);
        }
    }

    private RestatementId createId(int yearValid, int anoDocumento, int mesDocumento) {
        try {
            RestatementId restatementId = new RestatementId();
            restatementId.setYearFactor(yearValid);
            restatementId.setMonthDoc(mesDocumento);
            restatementId.setYearDoc(anoDocumento);
            return restatementId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

}




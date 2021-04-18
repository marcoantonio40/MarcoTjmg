package com.project.marco.services.impl;

import com.project.marco.config.ConfigProperties;
import com.project.marco.model.RestatementEntity;
import com.project.marco.model.RestatementId;
import com.project.marco.repository.RestatementRepository;
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

    @Override
    public HttpStatus formatToPattern() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(configProperties.getFileTxt()));
            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                checksLineIsValid(linha);
            }

            br.close();
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    private void checksLineIsValid(String linha) {
        String year = linha.substring(0, 4);
        try {
            int yearValid = Integer.parseInt(year);
            if (yearValid >= INIT_YEAR && yearValid <= LocalDateTime.now().getYear()) {
                extractToRestatement(linha, yearValid);
            }
        } catch (Exception e) {
        }

    }

    private void extractToRestatement(String linha, int yearValid) {
        int x = 0;
        String newLineWithoutYearsWithComma = linha.substring(5, (linha.length() - 5));
        String newLineWithoutYearsWithPoint = newLineWithoutYearsWithComma.replace(",", ".");
        String[] monthsString = newLineWithoutYearsWithPoint.split(" ");
        if(monthsString.length<12){
            monthsString = fillVector(monthsString);
        }
        Double[] monthsDouble = toDouble(monthsString);
        prepareToSave(monthsDouble, yearValid);

    }

    private String[] fillVector(String[] monthsString) {
        String[] monthsStringFilled = new String[12];
        for(int i = 0; i < 12; i++){
            if(i < monthsString.length){
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
            if(monthsString[i].isEmpty()){
                monthsDouble[i] = 0.0;
            } else {
                monthsDouble[i] = Double.parseDouble(monthsString[i]);
            }

        }

        return monthsDouble;
    }

    private void prepareToSave(Double[] monthsDouble, int yearValid) {
        RestatementEntity restatementEntity = new RestatementEntity();
        RestatementId restatementId = createId(yearValid);
        if (yearValid == INIT_YEAR) {
            createRestament1964(monthsDouble, restatementEntity, restatementId);
            repository.save(restatementEntity);
        } else {
            createRestamentAnyYear(monthsDouble, restatementEntity, restatementId);
            repository.save(restatementEntity);
        }
    }

    private void createRestamentAnyYear(Double[] monthsDouble, RestatementEntity restatementEntity, RestatementId restatementId) {
        restatementEntity.setRestatementId(restatementId);
        restatementEntity.setJanuary(monthsDouble[0]);
        restatementEntity.setFebruary(monthsDouble[1] > 0 ? monthsDouble[1] : 0);
        restatementEntity.setMarch(monthsDouble[2] > 0 ? monthsDouble[2] : 0);
        restatementEntity.setApril(monthsDouble[3] > 0 ? monthsDouble[3] : 0);
        restatementEntity.setMay(monthsDouble[4] > 0 ? monthsDouble[4] : 0);
        restatementEntity.setJune(monthsDouble[5] > 0 ? monthsDouble[5] : 0);
        restatementEntity.setJuly(monthsDouble[6] > 0 ? monthsDouble[6] : 0);
        restatementEntity.setAugust(monthsDouble[7] > 0 ? monthsDouble[7] : 0);
        restatementEntity.setSeptember(monthsDouble[8] > 0 ? monthsDouble[8] : 0);
        restatementEntity.setOctober(monthsDouble[9] > 0 ? monthsDouble[9] : 0);
        restatementEntity.setNovember(monthsDouble[10] > 0 ? monthsDouble[10] : 0);
        restatementEntity.setDecember(monthsDouble[11] > 0 ? monthsDouble[11] : 0);


    }

    private void createRestament1964(Double[] monthsDouble, RestatementEntity restatementEntity, RestatementId restatementId) {
        restatementEntity.setRestatementId(restatementId);
        restatementEntity.setOctober(monthsDouble[0]);
        restatementEntity.setNovember(monthsDouble[1]);
        restatementEntity.setDecember(monthsDouble[2]);
    }

    private RestatementId createId(int yearValid) {
        try {
            RestatementId restatementId = new RestatementId();
            restatementId.setYearFactor(yearValid);
            restatementId.setMonthDoc(4);
            restatementId.setYearDoc(2021);
            return restatementId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

}




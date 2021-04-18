package com.project.marco.services.impl;

import com.project.marco.config.ConfigProperties;
import com.project.marco.model.SavingsIndexEntity;
import com.project.marco.model.SavingsIndexId;
import com.project.marco.repository.SavingsIndexRepository;
import com.project.marco.services.SavingsIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class SavingsIndexServiceImpl implements SavingsIndexService {

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private SavingsIndexRepository savingsIndexRepository;

    @Override
    public void savingsIndex() throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(configProperties.getFileIndex()));
            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                saveInDb(linha);
            }

        } catch (Exception e){
            throw new Exception("Exception message");
        }
    }

    private void saveInDb(String linha) throws Exception {
        String[] split = linha.split("/");
        SavingsIndexEntity savingsIndexEntity = new SavingsIndexEntity();
        SavingsIndexId savingsIndexId = new SavingsIndexId();
        savingsIndexId.setMes(split[0]);
        savingsIndexId.setAno(split[1]);
        savingsIndexEntity.setSavingsIndexId(savingsIndexId);
        savingsIndexEntity.setValue(Double.parseDouble(split[2]));
        savingsIndexRepository.save(savingsIndexEntity);
    }
}

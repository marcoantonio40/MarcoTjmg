package com.project.marco.services.impl;

import com.project.marco.config.ConfigProperties;
import com.project.marco.model.SavingsIndexEntity;
import com.project.marco.model.SavingsIndexId;
import com.project.marco.repository.SavingsIndexRepository;
import com.project.marco.services.SavingsIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;

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
            String linha;

            while ((linha = br.readLine()) != null) {
                saveInDb(linha);
            }

        } catch (Exception e){
            throw new Exception("Exception message");
        }
    }

    @Override
    public String getLastIndex() throws Exception {
        try{
            SavingsIndexId savingsIndexId = new SavingsIndexId();
            savingsIndexId.setAno(2000);
            savingsIndexId.setMes(10);
            Optional<SavingsIndexEntity> byId = savingsIndexRepository.findById(savingsIndexId);
        }catch (Exception e){
            savingsIndex();
        }

        SavingsIndexEntity savingsIndexEntityBase = getSavingsIndexEntityInDataBase();

        return "Ano: "+savingsIndexEntityBase.getSavingsIndexId().getAno()+"\n"
                +"Mês: "+savingsIndexEntityBase.getSavingsIndexId().getMes()+"\n"
                +"Valor: "+savingsIndexEntityBase.getValue();
    }

    private SavingsIndexEntity getSavingsIndexEntityInDataBase() throws Exception {
        try{
            SavingsIndexId savingsIndexId = new SavingsIndexId();
            savingsIndexId.setAno(2000);
            savingsIndexId.setMes(10);
            Optional<SavingsIndexEntity> byId = savingsIndexRepository.findById(savingsIndexId);
        }catch (Exception e){
            savingsIndex();
        }

        List<SavingsIndexEntity> lastIndex = savingsIndexRepository.getLastIndex();
        SavingsIndexEntity savingsIndexEntityBase = lastIndex.get(0);
        int mesBase = savingsIndexEntityBase.getSavingsIndexId().getMes();

        for (SavingsIndexEntity savingsIndex: lastIndex) {
            if(savingsIndex.getSavingsIndexId().getMes() >= mesBase){
                savingsIndexEntityBase = savingsIndex;
                mesBase = savingsIndex.getSavingsIndexId().getMes();
            }
        }
        return savingsIndexEntityBase;
    }

    @Override
    public String saveSavingsIndex(Integer ano, Integer mes, Float valor) throws Exception {
        SavingsIndexEntity lastIndex = getSavingsIndexEntityInDataBase();
        int ultimoAno = lastIndex.getSavingsIndexId().getAno();
        int ultimoMes = lastIndex.getSavingsIndexId().getMes();

        if(ultimoMes == 12){
            if(ano - ultimoAno == 1){
                if(mes == 1){
                    BufferedWriter br = new BufferedWriter(new FileWriter(configProperties.getFileIndex()));
                    int x=0;
                }
            }
        } else if(mes > 0 && mes < 12){
            if(ano == ultimoAno){
                if(mes - ultimoMes == 1){
                    BufferedWriter br = new BufferedWriter(new FileWriter(configProperties.getFileIndex()));
                    int x=0;
                }
            }
        }

        return "Valor para essa data já existe";

    }

    private void saveInDb(String linha) throws Exception {
        String[] split = linha.split("/");
        SavingsIndexEntity savingsIndexEntity = new SavingsIndexEntity();
        SavingsIndexId savingsIndexId = new SavingsIndexId();
        savingsIndexId.setMes(Integer.parseInt(split[0]));
        savingsIndexId.setAno(Integer.parseInt(split[1]));
        savingsIndexEntity.setSavingsIndexId(savingsIndexId);
        savingsIndexEntity.setValue(Double.parseDouble(split[2]));
        savingsIndexRepository.save(savingsIndexEntity);
    }


}

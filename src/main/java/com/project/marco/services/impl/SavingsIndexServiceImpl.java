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

            while (((linha = br.readLine()) != null)) {
                if (!linha.isEmpty()) {
                    saveInDb(linha);
                }

            }

        } catch (Exception e) {
            throw new Exception("Exception message");
        }
    }

    @Override
    public String getLastIndex() throws Exception {
        try {
            SavingsIndexId savingsIndexId = new SavingsIndexId();
            savingsIndexId.setAno(2000);
            savingsIndexId.setMes(10);
            Optional<SavingsIndexEntity> byId = savingsIndexRepository.findById(savingsIndexId);

            SavingsIndexEntity savingsIndexEntity = byId.get();
        } catch (Exception e) {
            savingsIndex();
        }

        SavingsIndexEntity savingsIndexEntityBase = getSavingsIndexEntityInDataBase();

        return "Ano: " + savingsIndexEntityBase.getSavingsIndexId().getAno() + "\n"
                + "Mês: " + savingsIndexEntityBase.getSavingsIndexId().getMes() + "\n"
                + "Valor: " + savingsIndexEntityBase.getValue();
    }

    private SavingsIndexEntity getSavingsIndexEntityInDataBase() throws Exception {
        try {
            SavingsIndexId savingsIndexId = new SavingsIndexId();
            savingsIndexId.setAno(2000);
            savingsIndexId.setMes(10);
            Optional<SavingsIndexEntity> byId = savingsIndexRepository.findById(savingsIndexId);

            SavingsIndexEntity savingsIndexEntity = byId.get();
        } catch (Exception e) {
            savingsIndex();
        }

        List<SavingsIndexEntity> lastIndex = savingsIndexRepository.getLastIndex();
        SavingsIndexEntity savingsIndexEntityBase = lastIndex.get(0);
        int mesBase = savingsIndexEntityBase.getSavingsIndexId().getMes();

        for (SavingsIndexEntity savingsIndex : lastIndex) {
            if (savingsIndex.getSavingsIndexId().getMes() >= mesBase) {
                savingsIndexEntityBase = savingsIndex;
                mesBase = savingsIndex.getSavingsIndexId().getMes();
            }
        }
        return savingsIndexEntityBase;
    }

    @Override
    public String saveSavingsIndex(Integer ano, Integer mes, Double valor) throws Exception {
        SavingsIndexEntity lastIndex = getSavingsIndexEntityInDataBase();
        int ultimoAno = lastIndex.getSavingsIndexId().getAno();
        int ultimoMes = lastIndex.getSavingsIndexId().getMes();
        BufferedWriter bw = new BufferedWriter(new FileWriter(configProperties.getFileIndex(), true));
        SavingsIndexId savingsIndexId = new SavingsIndexId();
        savingsIndexId.setMes(mes);
        savingsIndexId.setAno(ano);
        SavingsIndexEntity savingsIndexEntity = new SavingsIndexEntity();
        savingsIndexEntity.setSavingsIndexId(savingsIndexId);
        savingsIndexEntity.setValue(valor);

        try {
            if (ultimoMes == 12) {
                if (ano - ultimoAno == 1) {
                    if (mes == 1) {
                        bw.write("0" + mes + "/" + ano + "/" + valor);
                        bw.newLine();
                        bw.close();
                        savingsIndexRepository.save(savingsIndexEntity);
                        return "Registro salvo!";
                    }
                }
            } else if (mes > 0 && mes <= 12) {
                if (ano == ultimoAno) {
                    if (mes - ultimoMes == 1) {

                        if (mes < 10) {
                            bw.write("0" + mes + "/" + ano + "/" + valor);

                        } else {
                            bw.write(mes + "/" + ano + "/" + valor);
                        }
                        bw.newLine();
                        bw.close();
                        savingsIndexRepository.save(savingsIndexEntity);
                        return "Registro salvo!";
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


        return "Já existe valor para essa data ou não é o próximo mês!";

    }

    private void saveInDb(String linha) throws Exception {
        try {
            String[] split = linha.split("/");
            SavingsIndexEntity savingsIndexEntity = new SavingsIndexEntity();
            SavingsIndexId savingsIndexId = new SavingsIndexId();
            savingsIndexId.setMes(Integer.parseInt(split[0]));
            savingsIndexId.setAno(Integer.parseInt(split[1]));
            savingsIndexEntity.setSavingsIndexId(savingsIndexId);
            savingsIndexEntity.setValue(Double.parseDouble(split[2]));
            savingsIndexRepository.save(savingsIndexEntity);
        } catch (Exception e) {
            throw new Exception("Exception " + linha + " message{}");
        }

    }


}

package com.project.marco.services;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SavingsIndexService {
    void savingsIndex() throws Exception;

    String getLastIndex() throws FileNotFoundException, Exception;

    String saveSavingsIndex(Integer ano, Integer mes, Double valor) throws Exception;
}

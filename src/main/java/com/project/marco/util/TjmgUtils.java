package com.project.marco.util;

import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;

@Component
public class TjmgUtils {


    public String createNameFile(String fileXls) {

        Date data = new Date();
        final Instant dataName = data.toInstant();
        String dataNameCorrect = dataName.toString().replace(":", "_");

        if(!Paths.get(fileXls).toFile().isFile()){
            return fileXls;
        } else {
            String newNamwFile = fileXls.replace(".xls", dataNameCorrect + ".xls");
            return createNameFile(newNamwFile);
        }

    }
}

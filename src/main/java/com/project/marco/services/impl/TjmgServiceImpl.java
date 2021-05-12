package com.project.marco.services.impl;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.project.marco.config.ConfigProperties;
import com.project.marco.services.PatternService;
import com.project.marco.services.TjmgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class TjmgServiceImpl implements TjmgService {

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private PatternService patternService;


    @Override
    public HttpStatus updloadPdf(MultipartFile multipartFile, int anoDoc, int mesDoc) {
        try{
            multipartFile.transferTo(new File(configProperties.getFileDestino()+"/"+multipartFile.getOriginalFilename()));
            File file = new File(configProperties.getFileDestino()+"/"+multipartFile.getOriginalFilename());
            readerPdf(file.getAbsolutePath(), anoDoc, mesDoc );

            return HttpStatus.OK;
        } catch (Exception e){
            return HttpStatus.NOT_FOUND;
        }
    }


    public HttpStatus readerPdf(String fileName, int anoDoc, int mesDoc){
        PdfReader reader;
        try {
            reader = new PdfReader(fileName);
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(configProperties.getFileTxt()));
            int numberOfPages = reader.getNumberOfPages();
            int aux = 1;

            while (numberOfPages > aux){
                String textFromPage = PdfTextExtractor.getTextFromPage(reader, aux);
                bufferWriter.append(textFromPage);
                aux++;
            }

            bufferWriter.close();
            reader.close();
            patternService.formatToPattern(anoDoc, mesDoc);
            return HttpStatus.OK;
        } catch (IOException e) {
            return HttpStatus.NOT_FOUND;
        }
    }
}

package com.project.marco.services.impl;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.project.marco.config.ConfigProperties;
import com.project.marco.services.TjmgService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class TjmgServiceImpl implements TjmgService {

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public HttpStatus updloadPdf(MultipartFile file) throws MalformedURLException {
        URL url = new URL("https://www.tjmg.jus.br/lumis/portal/file/fileDownload.jsp?fileId=8A80BCE67818FF04017835AB2E890DB6");

        File file1 = new File(configProperties.getFilePdf());
        try{
            FileUtils.copyURLToFile(url, file1);
            return HttpStatus.OK;
        } catch (Exception e){
            return HttpStatus.NOT_FOUND;
        }
    }

    @Override
    public HttpStatus readerPdf(){
        PdfReader reader;
        try {
            reader = new PdfReader(configProperties.getFilePdf());
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

            return HttpStatus.OK;
        } catch (IOException e) {
            return HttpStatus.NOT_FOUND;
        }
    }



}

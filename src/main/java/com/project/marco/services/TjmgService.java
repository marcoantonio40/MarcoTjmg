package com.project.marco.services;

import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public interface TjmgService {

    HttpStatus downloadPdf() throws MalformedURLException;

    HttpStatus readerPdf() ;


}
